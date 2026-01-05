# Chapter 3: Configuration & Properties

## Goal
Replace hardcoded values with externalized configuration using `@Value` and `application.yml`.

## Tasks

### 1. Add Configuration Properties

**File:** `application.yml`

Add custom properties for the default route:

```yaml
server:
  port: 8080

route:
  default-origin: Frankfurt
  default-destination: Berlin

logging:
  level:
    root: INFO
    com.airline: DEBUG
```

### 2. Inject Configuration with @Value

**File:** `RouteService.java`

Use constructor injection with `@Value` to inject the configuration values:

```java
package com.airline.flightservice.service;

import com.airline.flightservice.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteService.class);

    private final String defaultOrigin;
    private final String defaultDestination;
    private final List<Route> routes;

    public RouteService(
            @Value("${route.default-origin}") String defaultOrigin,
            @Value("${route.default-destination}") String defaultDestination) {
        this.defaultOrigin = defaultOrigin;
        this.defaultDestination = defaultDestination;
        this.routes = List.of(
                new Route(1L, defaultOrigin, defaultDestination),
                new Route(2L, "Munich", "Hamburg"),
                new Route(3L, "Cologne", "Dresden")
        );
        log.info("RouteService initialized with default route: {} -> {}",
                defaultOrigin, defaultDestination);
    }

    public List<Route> findAll() {
        log.info("Finding all routes");
        return routes;
    }

    public Optional<Route> findById(Long id) {
        log.debug("Finding route with id: {}", id);
        return routes.stream()
                .filter(route -> route.getId().equals(id))
                .findFirst();
    }

    public String getDefaultRoute() {
        return defaultOrigin + " -> " + defaultDestination;
    }
}
```

### 3. Update Test

**File:** `RouteServiceTest.java`

Verify the configuration is correctly loaded:

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
    void shouldUseConfiguredDefaultRoute() {
        var defaultRoute = routeService.getDefaultRoute();

        assertThat(defaultRoute).isEqualTo("Frankfurt -> Berlin");
    }

    @Test
    void shouldIncludeDefaultRouteInAllRoutes() {
        var routes = routeService.findAll();

        assertThat(routes)
                .extracting(Route::toString)
                .contains("Frankfurt -> Berlin");
    }
}
```

## Key Concepts

- **@Value("${...}")**: Injects a value from configuration
- **@Value("${...}:default")**: Provides a default value if property is missing
- **application.yml**: Main configuration file (YAML format)
- **Constructor Injection**: Allows fields to be `final` and ensures immutability
- **var**: Local variable type inference - use when the type is obvious from context

## Verify

Run the tests:
```bash
./gradlew test
```

You can also override the configuration at runtime:
```bash
java -jar app.jar --route.default-origin=Munich
```

---

## Häufige Fragen

**"application.yml oder application.properties?"**
> YAML ist heute Standard bei Spring Boot. Bessere Lesbarkeit bei verschachtelten Strukturen.

**"Wo speichere ich Secrets?"**
> NICHT in application.yml! Optionen: Environment Variables, Spring Cloud Config, Vault, Kubernetes Secrets.

**"Warum keine dev/prod Profile mehr?"**
> In Cloud-Native-Setups ist das ein Anti-Pattern. Das JAR soll für alle Stages identisch sein. Stage-Config kommt aus der Infrastruktur.

**"Kann ich Properties zur Laufzeit ändern?"**
> Standardmäßig nein. Mit Spring Cloud Config + @RefreshScope geht es.

**"Was ist @ConfigurationProperties?"**
> Typsichere Konfiguration für komplexe Strukturen. Statt vieler @Value-Annotationen eine Klasse mit allen Properties.

---

## Checkliste für die Demo

- [ ] application.yml mit Beispiel-Properties vorbereitet
- [ ] Erweiterter RouteService vorbereitet (zum Kopieren)
- [ ] Erweiterter Test vorbereitet (zum Kopieren)
- [ ] Terminal bereit für `./gradlew test`
- [ ] @TestPropertySource Beispiel vorbereitet
