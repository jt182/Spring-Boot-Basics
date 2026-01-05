# Chapter 2: Dependency Injection & Beans

## Goal
Create a Spring-managed `RouteService` bean and test it with `@SpringBootTest`.

## Tasks

### 1. Make RouteService a Spring Bean

**File:** `RouteService.java`

Add the `@Service` annotation to register the class as a Spring bean:

```java
package com.airline.flightservice.service;

import com.airline.flightservice.model.Route;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final List<Route> routes = List.of(
            new Route(1L, "Frankfurt", "Berlin"),
            new Route(2L, "Munich", "Hamburg"),
            new Route(3L, "Cologne", "Dresden")
    );

    public List<Route> findAll() {
        return routes;
    }

    public Optional<Route> findById(Long id) {
        return routes.stream()
                .filter(route -> route.getId().equals(id))
                .findFirst();
    }
}
```

### 2. Write Integration Test

**File:** `RouteServiceTest.java`

Use `@SpringBootTest` to load the Spring context and `@Autowired` to inject the service:

```java
package com.airline.flightservice.service;

import com.airline.flightservice.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Test
    void shouldReturnAllRoutes() {
        var routes = routeService.findAll();

        assertThat(routes).isNotNull();
        assertThat(routes).isNotEmpty();
        assertThat(routes).hasSize(3);
    }

    @Test
    void shouldFindRouteById() {
        var route = routeService.findById(1L);

        assertThat(route).isPresent();
        assertThat(route.get().getOrigin()).isEqualTo("Frankfurt");
        assertThat(route.get().getDestination()).isEqualTo("Berlin");
    }

    @Test
    void shouldReturnEmptyForUnknownId() {
        var route = routeService.findById(999L);

        assertThat(route).isEmpty();
    }
}
```

## Key Concepts

- **@Service**: Marks a class as a Spring bean (specialized @Component for business logic)
- **@SpringBootTest**: Loads the full Spring context for integration testing
- **@Autowired**: Injects a Spring-managed bean
- **Constructor Injection**: Preferred way to inject dependencies (fields can be `final`)
- **var**: Local variable type inference (Java 10+) - reduces boilerplate

## Verify

Run the tests:
```bash
./gradlew test
```

---

## Häufige Fragen

**"Warum nicht einfach `new` verwenden?"**
> Mit `new` verliert ihr alle Spring-Features: AOP (Transaktionen, Security), Lifecycle-Management, Konfiguration über Properties, etc.

**"Was passiert bei zirkulären Dependencies?"**
> Spring erkennt das und wirft beim Startup eine Exception. Das ist gut - zirkuläre Dependencies sind ein Code Smell!

**"Kann ich auch Interfaces statt Klassen injizieren?"**
> Ja, und das ist sogar besser! Dann könnt ihr die Implementierung leicht austauschen (z.B. für Tests).

**"Was ist der Unterschied zwischen @Component und @Bean?"**
> `@Component` annotiert eine Klasse direkt. `@Bean` annotiert eine Methode in einer `@Configuration`-Klasse, die das Bean erstellt.

**"Ist @SpringBootTest nicht langsam?"**
> Ja, relativ! Der ganze Container wird hochgefahren. Für Unit Tests nutzt man Mocks. Für Schichten-Tests gibt es `@DataJpaTest`, `@WebMvcTest` etc.

**"Warum @Autowired im Test, aber nicht im produktiven Code?"**
> Im produktiven Code nutzen wir Constructor Injection. In Tests geht Field Injection, weil der Test sowieso vom Spring-Runner gestartet wird.

---

## Checkliste für die Demo

- [ ] Service-Klasse vorbereitet (zum Kopieren)
- [ ] Test-Klasse vorbereitet (zum Kopieren)
- [ ] Terminal bereit für `./gradlew test`
- [ ] IDE zeigt Package-Struktur deutlich
- [ ] IDE kann Tests ausführen (grüner Play-Button)
