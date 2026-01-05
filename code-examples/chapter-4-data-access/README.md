# Chapter 4: Data Access & JPA

## Goal
Convert the `Route` class to a JPA entity and create a Spring Data repository.

## Tasks

### 1. Add Dependencies

**File:** `build.gradle`

Add Spring Data JPA and H2 database:

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webmvc'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

### 2. Configure Database

**File:** `application.yml`

Enable H2 console and configure JPA:

```yaml
server:
  port: 8080

spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:flightdb
  jpa:
    show-sql: true

logging:
  level:
    root: INFO
    com.airline: DEBUG
```

### 3. Convert Route to JPA Entity

**File:** `Route.java`

Add JPA annotations:

```java
package com.airline.flightservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;

    private String destination;

    public Route() {
    }

    public Route(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    // Getters and setters...
}
```

### 4. Create Spring Data Repository

**File:** `RouteRepository.java`

Convert to an interface extending `ListCrudRepository`:

```java
package com.airline.flightservice.repository;

import com.airline.flightservice.model.Route;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RouteRepository extends ListCrudRepository<Route, Long> {

    List<Route> findByOrigin(String origin);

    List<Route> findByOriginAndDestination(String origin, String destination);

    boolean existsByOriginAndDestination(String origin, String destination);
}
```

### 5. Write Repository Tests

**File:** `RouteRepositoryTest.java`

Use `@DataJpaTest` for fast, isolated tests:

```java
package com.airline.flightservice.repository;

import com.airline.flightservice.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepository repository;

    @Test
    void shouldSaveAndFindRoute() {
        var route = new Route("Frankfurt", "Berlin");

        var saved = repository.save(route);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void shouldFindRoutesByOrigin() {
        repository.save(new Route("Frankfurt", "Berlin"));
        repository.save(new Route("Frankfurt", "Munich"));
        repository.save(new Route("Hamburg", "Berlin"));

        var frankfurtRoutes = repository.findByOrigin("Frankfurt");

        assertThat(frankfurtRoutes).hasSize(2);
        assertThat(frankfurtRoutes)
                .extracting(Route::getOrigin)
                .containsOnly("Frankfurt");
    }

    @Test
    void shouldCheckIfRouteExists() {
        repository.save(new Route("Frankfurt", "Berlin"));

        var exists = repository.existsByOriginAndDestination("Frankfurt", "Berlin");
        var notExists = repository.existsByOriginAndDestination("Munich", "Hamburg");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
```

## Key Concepts

- **@Entity**: Marks a class as a JPA entity (mapped to a database table)
- **@Id**: Marks the primary key field
- **@GeneratedValue**: Auto-generates the ID value
- **ListCrudRepository**: Spring Data interface providing CRUD operations
- **Query Derivation**: Spring generates queries from method names (e.g., `findByOrigin`)
- **@DataJpaTest**: Sliced test that only loads JPA components (faster than @SpringBootTest)
- **var**: Use for local variables where the type is clear from the right-hand side

## Verify

Run the tests:
```bash
./gradlew test
```

Access H2 Console at http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:flightdb`
- Username: `sa`
- Password: (empty)

---

## Bonus: PostgreSQL with Docker

### 1. Add PostgreSQL Dependency

**File:** `build.gradle`

```groovy
dependencies {
    // ... existing dependencies
    runtimeOnly 'org.postgresql:postgresql'
}
```

### 2. Start PostgreSQL

**Option A: Docker Compose (recommended)**

```bash
docker compose up -d
```

**Option B: Docker run**

```bash
docker run -d --name postgres \
  -e POSTGRES_DB=flightdb \
  -e POSTGRES_USER=flight \
  -e POSTGRES_PASSWORD=secret \
  -p 5432:5432 \
  postgres:17
```

**Option C: Podman**

```bash
podman run -d --name postgres \
  -e POSTGRES_DB=flightdb \
  -e POSTGRES_USER=flight \
  -e POSTGRES_PASSWORD=secret \
  -p 5432:5432 \
  postgres:17
```

### 3. Create Profile Configuration

**File:** `application-postgres.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/flightdb
    username: flight
    password: secret
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop
```

### 4. Run with PostgreSQL Profile

```bash
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

Or as JAR:
```bash
java -jar build/libs/flightservice-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres
```

### 5. Verify Connection

Check the logs for:
```
HikariPool-1 - Starting...
HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@...
```

### Cleanup

With Docker Compose:
```bash
docker compose down
```

Or with docker run:
```bash
docker stop postgres && docker rm postgres
```

---

## Häufige Fragen

**"Warum brauche ich einen Default-Constructor?"**
> JPA muss Objekte per Reflection erstellen können. Dafür braucht es einen no-args Constructor. Kann `protected` sein.

**"Was ist der Unterschied zwischen CrudRepository und JpaRepository?"**
> `JpaRepository` erweitert `ListCrudRepository` und `PagingAndSortingRepository`. Für die meisten Fälle reicht `ListCrudRepository`.

**"Wie verhindere ich SQL-Injection?"**
> Spring Data ist standardmäßig sicher! Es verwendet PreparedStatements mit Parameter-Binding. Niemals Strings konkatenieren!

**"Was passiert mit meinen Daten bei H2?"**
> Bei `jdbc:h2:mem:` gehen sie beim App-Stop verloren. Für persistente H2-Daten: `jdbc:h2:file:./data/mydb`

**"Wann sollte ich native Queries verwenden?"**
> Nur wenn JPQL nicht ausreicht (DB-spezifische Features, Performance). Native Queries sind nicht portabel!

---

## Checkliste für die Demo

- [ ] Dependencies in build.gradle vorbereitet
- [ ] Entity-Klasse vorbereitet (zum Kopieren)
- [ ] Repository-Interface vorbereitet (zum Kopieren)
- [ ] Test-Klasse vorbereitet (zum Kopieren)
- [ ] application.yml mit H2-Config vorbereitet
- [ ] Terminal bereit für `./gradlew test`
- [ ] IDE zeigt SQL-Output im Log
- [ ] Docker/Podman läuft für PostgreSQL-Demo
