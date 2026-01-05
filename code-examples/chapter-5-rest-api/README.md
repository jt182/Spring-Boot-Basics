# Chapter 5: REST APIs

## Goal
Create a REST API for Route management with proper HTTP status codes, validation, exception handling, and DTOs.

## Tasks

### 1. Create DTOs (Data Transfer Objects)

DTOs separate the API contract from the internal domain model.

**File:** `dto/RouteRequestDto.java`

```java
package com.airline.flightservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RouteRequestDto {

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @Min(value = 1, message = "Distance must be at least 1 km")
    private Integer distanceKm;

    public RouteRequestDto() {
    }

    public RouteRequestDto(String origin, String destination, Integer distanceKm) {
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
    }

    // Getters and setters...
}
```

**File:** `dto/RouteResponseDto.java`

```java
package com.airline.flightservice.dto;

public class RouteResponseDto {

    private Long id;
    private String origin;
    private String destination;
    private Integer distanceKm;

    public RouteResponseDto() {
    }

    public RouteResponseDto(Long id, String origin, String destination, Integer distanceKm) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
    }

    // Getters and setters...
}
```

### 2. Create Manual Mapper

**File:** `dto/RouteMapper.java`

```java
package com.airline.flightservice.dto;

import com.airline.flightservice.model.Route;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteMapper {

    public RouteResponseDto toResponseDto(Route route) {
        RouteResponseDto dto = new RouteResponseDto();
        dto.setId(route.getId());
        dto.setOrigin(route.getOrigin());
        dto.setDestination(route.getDestination());
        dto.setDistanceKm(route.getDistanceKm());
        return dto;
    }

    public List<RouteResponseDto> toResponseDtoList(List<Route> routes) {
        return routes.stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Route toEntity(RouteRequestDto dto) {
        Route route = new Route();
        route.setOrigin(dto.getOrigin());
        route.setDestination(dto.getDestination());
        route.setDistanceKm(dto.getDistanceKm());
        return route;
    }

    public void updateEntity(Route route, RouteRequestDto dto) {
        route.setOrigin(dto.getOrigin());
        route.setDestination(dto.getDestination());
        route.setDistanceKm(dto.getDistanceKm());
    }
}
```

### 3. Create REST Controller

**File:** `controller/RouteController.java`

```java
package com.airline.flightservice.controller;

import com.airline.flightservice.dto.RouteMapper;
import com.airline.flightservice.dto.RouteRequestDto;
import com.airline.flightservice.dto.RouteResponseDto;
import com.airline.flightservice.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    public RouteController(RouteService routeService, RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @GetMapping
    public List<RouteResponseDto> findAll(@RequestParam(required = false) String origin) {
        if (origin != null) {
            return routeMapper.toResponseDtoList(routeService.findByOrigin(origin));
        }
        return routeMapper.toResponseDtoList(routeService.findAll());
    }

    @GetMapping("/{id}")
    public RouteResponseDto findById(@PathVariable Long id) {
        return routeMapper.toResponseDto(routeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RouteResponseDto> create(@Valid @RequestBody RouteRequestDto request) {
        var route = routeMapper.toEntity(request);
        var saved = routeService.save(route);
        var location = URI.create("/routes/" + saved.getId());
        return ResponseEntity.created(location).body(routeMapper.toResponseDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 4. Create Global Exception Handler

**File:** `exception/GlobalExceptionHandler.java`

```java
package com.airline.flightservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(RouteNotFoundException ex) {
        var problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Route Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
```

### 5. Write Controller Tests

**File:** `RouteControllerTest.java`

```java
package com.airline.flightservice.controller;

import com.airline.flightservice.dto.RouteMapper;
import com.airline.flightservice.dto.RouteRequestDto;
import com.airline.flightservice.dto.RouteResponseDto;
import com.airline.flightservice.exception.RouteNotFoundException;
import com.airline.flightservice.model.Route;
import com.airline.flightservice.service.RouteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RouteService routeService;

    @MockitoBean
    private RouteMapper routeMapper;

    @Test
    void shouldReturnAllRoutes() throws Exception {
        var route1 = new Route("Frankfurt", "Berlin");
        route1.setId(1L);

        var dto1 = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);

        when(routeService.findAll()).thenReturn(List.of(route1));
        when(routeMapper.toResponseDtoList(List.of(route1))).thenReturn(List.of(dto1));

        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].origin").value("Frankfurt"));
    }

    @Test
    void shouldReturnRouteById() throws Exception {
        var route = new Route("Frankfurt", "Berlin");
        route.setId(1L);
        var dto = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);

        when(routeService.findById(1L)).thenReturn(route);
        when(routeMapper.toResponseDto(route)).thenReturn(dto);

        mockMvc.perform(get("/routes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Frankfurt"));
    }

    @Test
    void shouldReturn404WhenRouteNotFound() throws Exception {
        when(routeService.findById(999L)).thenThrow(new RouteNotFoundException(999L));

        mockMvc.perform(get("/routes/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Route Not Found"));
    }

    @Test
    void shouldCreateRoute() throws Exception {
        var route = new Route("Frankfurt", "Berlin");
        var saved = new Route("Frankfurt", "Berlin");
        saved.setId(1L);
        var responseDto = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);

        when(routeMapper.toEntity(any(RouteRequestDto.class))).thenReturn(route);
        when(routeService.save(route)).thenReturn(saved);
        when(routeMapper.toResponseDto(saved)).thenReturn(responseDto);

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "origin": "Frankfurt",
                                    "destination": "Berlin"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/routes/1"));
    }

    @Test
    void shouldReturn400WhenValidationFails() throws Exception {
        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "origin": "",
                                    "destination": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteRoute() throws Exception {
        mockMvc.perform(delete("/routes/1"))
                .andExpect(status().isNoContent());

        verify(routeService).deleteById(1L);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentRoute() throws Exception {
        doThrow(new RouteNotFoundException(999L)).when(routeService).deleteById(999L);

        mockMvc.perform(delete("/routes/999"))
                .andExpect(status().isNotFound());
    }
}
```

## Key Concepts

### REST Annotations
- **@RestController**: Combines @Controller and @ResponseBody
- **@RequestMapping**: Base path for all endpoints
- **@GetMapping / @PostMapping / @DeleteMapping**: HTTP method mappings
- **@PathVariable**: Extracts value from URL path (`/routes/{id}`)
- **@RequestParam**: Extracts query parameters (`/routes?origin=Frankfurt`)
- **@RequestBody**: Deserializes JSON body to Java object
- **@Valid**: Triggers validation on the request body

### DTOs and Mapping
- **DTO (Data Transfer Object)**: Separates API contract from domain model
- **RequestDto**: For incoming data (without id, with validation)
- **ResponseDto**: For outgoing data (with id)
- **Manual Mapper**: Simple, explicit conversion between Entity and DTO

### Response Handling
- **ResponseEntity**: Full control over HTTP response (status, headers, body)
- **@RestControllerAdvice**: Global exception handling for all controllers
- **ProblemDetail**: RFC 7807 standard format for error responses

### Testing
- **@WebMvcTest**: Sliced test that only loads web layer
- **@MockitoBean**: Creates a mock bean in the Spring context
- **Text Blocks (""")**: Multi-line strings for JSON

## HTTP Status Codes

| Status | When to use |
|--------|-------------|
| 200 OK | Successful GET, PUT |
| 201 Created | Successful POST (+ Location header) |
| 204 No Content | Successful DELETE |
| 400 Bad Request | Validation errors |
| 404 Not Found | Resource not found |

## Verify

Run the tests:
```bash
./gradlew test
```

Test the API manually:
```bash
# Get all routes
curl http://localhost:8080/routes

# Create a route
curl -X POST http://localhost:8080/routes \
  -H "Content-Type: application/json" \
  -d '{"origin": "Frankfurt", "destination": "Berlin", "distanceKm": 550}'

# Get route by ID
curl http://localhost:8080/routes/1

# Delete route
curl -X DELETE http://localhost:8080/routes/1
```

---

## Häufige Fragen

**"Warum DTOs statt direkt die Entity?"**
> DTOs entkoppeln die API von der Datenbank-Struktur. Man kann Felder umbenennen, ausblenden oder hinzufügen, ohne die Entity zu ändern. Validation-Annotations gehören ins RequestDto, nicht in die Entity.

**"Warum manuelles Mapping statt MapStruct?"**
> Manuelles Mapping ist explizit und leicht verständlich. Für größere Projekte kann MapStruct sinnvoll sein, aber für Schulungszwecke ist manuelles Mapping transparenter.

**"Warum @RestController statt @Controller?"**
> @RestController setzt automatisch @ResponseBody auf alle Methoden. Bei @Controller würde Spring nach einer View suchen.

**"Wie teste ich PUT und PATCH?"**
> Genauso wie POST, nur mit `put()` oder `patch()` statt `post()`.

**"Warum brauche ich Content-Type: application/json?"**
> Spring muss wissen, wie der Request-Body zu parsen ist. Ohne Header würde er Form-Daten erwarten.

**"Was ist der Unterschied zwischen @MockBean und @MockitoBean?"**
> @MockitoBean ist die neue Variante ab Spring Boot 3.4. @MockBean ist deprecated.

**"Wie mache ich Pagination?"**
> Mit Spring Data's `Pageable`: `public Page<RouteResponseDto> findAll(Pageable pageable)`
> Dann: `GET /routes?page=0&size=10&sort=origin,asc`

**"Wie dokumentiere ich meine API?"**
> Mit OpenAPI/Swagger. Einfach `springdoc-openapi-starter-webmvc-ui` hinzufügen.

---

## Checkliste für die Solution

- [x] DTOs erstellen (`RouteRequestDto`, `RouteResponseDto`)
- [x] RouteMapper mit manuellem Mapping erstellen
- [x] RouteController mit allen CRUD-Endpoints implementieren
- [x] GlobalExceptionHandler für RouteNotFoundException
- [x] Controller-Tests mit MockMvc, @MockitoBean für Service und Mapper
