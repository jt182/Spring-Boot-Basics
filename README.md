# Spring Boot Basics

## Übersicht

| Kapitel | Themen | Zeit |
|---------|--------|------|
| Motivation & Setup | Warum Spring Boot, 3 Säulen, Projekt-Setup | ~25 Min |
| DI & Beans | IoC Container, Constructor Injection, Scopes | ~30 Min |
| Configuration | application.yml, @Value, Profile, Logging | ~25 Min |
| Data Access & JPA | Entity, Repository, Query Derivation, @DataJpaTest | ~40 Min |
| REST APIs | @RestController, Error Handling, @WebMvcTest | ~45 Min |
| Wrap-Up | Zusammenfassung, Ausblick, Ressourcen | ~10 Min |

---

## Projektstruktur

```
Spring Boot Vorlesung/
├── code-examples/                  # Code-Beispiele für Live-Coding
│   ├── chapter-2-di-beans/
│   ├── chapter-3-configuration/
│   ├── chapter-4-data-access/
│   └── chapter-5-rest-api/
├── images/                         # In der Präsentation genutzte Bilder
│── folien.md                       # Präsentation
├── README.md                       # Diese Datei
└── theme.css                       # Custom Marp Theme
```

## Code-Beispiele

Die `code-examples/` Verzeichnisse enthalten vorbereiteten Code für die Live-Coding Sessions:

| Verzeichnis | Kapitel | Inhalt |
|-------------|---------|--------|
| `chapter-2-di-beans` | DI & Beans | RouteService mit @Service |
| `chapter-3-configuration` | Configuration | application.yml, @Value |
| `chapter-4-data-access` | Data Access & JPA | Route Entity, Repository |
| `chapter-5-rest-api` | REST APIs | RouteController, Exception Handling |

---

## 1. Motivation & Setup

**Lernziel:** Verstehen, warum Spring Boot existiert und wie man ein Projekt aufsetzt.

### Themen:
* **Warum Spring Boot?**
    * Unterschied: Spring Framework (mächtig aber komplex) vs. Spring Boot (Opinionated, Convention over Configuration)
    * Ziele: Effektivität (korrekte Config) und Effizienz (Auto-Config)

* **Die 3 Säulen:**
    1. **Dependency Management:** Starter-Dependencies lösen die "Dependency Hell"
    2. **Auto-Configuration:** Automatische Erstellung von Beans basierend auf dem Classpath
    3. **Run Anywhere:** Embedded Server (Tomcat) im Fat JAR

* **Live-Coding:**
    * Nutzung von `start.spring.io` (Gradle als Build-Tool)
    * Blick in die `build.gradle`: `spring-boot-starter-webmvc` und `spring-boot-starter-test`
    * Erste Anwendung starten

## 2. Dependency Injection & Beans

**Lernziel:** Verstehen, wie der Spring Container funktioniert (DI/IoC).

### Themen:
* **Das Problem:** Tight Coupling durch direkte Instanziierung
* **Die Lösung:** Inversion of Control (IoC)
* **Der Container:** Verwaltet den Lebenszyklus von Beans
* **Spring Beans:** Objekte, die vom Container instanziiert und verwaltet werden

* **Bean-Definition:**
    * `@Component` und Component Scan
    * Stereotypen: `@Service`, `@Repository`, `@Controller`, `@RestController`

* **Injection-Varianten:**
    * **Constructor Injection** (Best Practice): Felder können `final` sein, testbar
    * **Setter Injection:** Für optionale Dependencies
    * **Field Injection:** Vermeiden (versteckte Dependencies)
    * `@Autowired` ist bei nur einem Constructor optional (seit Spring 4.3)

* **Bean Scopes:** Singleton (Default), Prototype, Request, Session

* **Live-Coding: RouteService mit Integration Test**
    * Erstellen der Service-Klasse mit `@Service`
    * **@SpringBootTest:** Fährt den vollen ApplicationContext hoch
    * Test mit AssertJ schreiben

## 3. Configuration & Properties

**Lernziel:** Externalisierte Konfiguration verstehen und anwenden.

### Themen:
* **Externalisierung:** Trennung von Code und Konfiguration
* **application.yml:** Hierarchische Struktur, YAML-Syntax
* **Konfigurationsquellen:** CLI > System Props > Env Vars > Config File
* **@Value Injection:**
    * `${property.name}` Syntax
    * Default-Werte: `${property:default}`
    * Constructor Injection mit @Value

* **Profile:**
    * Profile für technische Varianten (h2, postgres), nicht für Stages!
    * Stage-Config (dev/prod) gehört in die Infrastruktur
    * `--spring.profiles.active=postgres`

* **Logging:**
    * SLF4J + Logback (Default in Spring Boot)
    * Placeholder `{}` statt String-Konkatenation
    * Log-Level in application.yml konfigurierbar

* **Live-Coding: Konfigurierbare Routes**
    * `RouteService` mit `@Value` und `application.yml` erweitern
    * Test mit `@TestPropertySource`

## 4. Data Access & JPA

**Lernziel:** Persistenzschicht mit Spring Data JPA bauen.

### Themen:
* **Problem:** Boilerplate-Code bei JDBC/DB-Zugriffen
* **Lösung:** Spring Data Repositories
* **Repository-Hierarchie:**
    * `Repository` → `CrudRepository` → `ListCrudRepository` → `JpaRepository`

* **Entity definieren:**
    * `@Entity`, `@Id`, `@GeneratedValue`
    * Default Constructor für JPA

* **Repository definieren:**
    * Interface extends `ListCrudRepository<T, ID>`
    * Spring generiert die Implementierung

* **Query Derivation:**
    * Queries durch Methodennamen: `findByOrigin`, `findByOriginAndDestination`
    * Keywords: And, Or, OrderBy, Containing, Between

* **Custom Queries:**
    * `@Query` mit JPQL (Default) oder Native SQL (`nativeQuery = true`)
    * Parameter-Binding mit `:name` oder `?1` (SQL-Injection sicher!)

* **Setup:**
    * H2 In-Memory DB für Entwicklung
    * H2 Console für Debugging

* **@DataJpaTest:**
    * Sliced Test: Lädt nur JPA-Komponenten
    * Schnell, automatischer Rollback

* **Live-Coding: Route-Entity mit Repository**
    * Entity und Repository erstellen
    * Test mit `@DataJpaTest`

## 5. REST APIs

**Lernziel:** REST-API exponieren und gezielt testen.

### Themen:
* **REST-Grundlagen & HTTP:**
    * Ressourcen-orientiert, zustandslos, einheitliche Schnittstelle
    * HTTP-Verben: GET, POST, PUT, DELETE
    * HTTP Status Codes: 2xx (Erfolg), 4xx (Client-Fehler), 5xx (Server-Fehler)

* **@RestController & Mappings:**
    * `@RequestMapping` für Base-Path
    * `@GetMapping`, `@PostMapping`, `@DeleteMapping`
    * `@PathVariable`, `@RequestParam`, `@RequestBody`

* **DTOs (Data Transfer Objects):**
    * Entkopplung von API und Datenbank-Entity
    * Records als DTOs (immutable, wenig Boilerplate)
    * ResponseEntity für Status-Codes und Header

* **Validierung:**
    * Bean Validation: `@NotBlank`, `@Min`, `@Size`, etc.
    * `@Valid` im Controller aktiviert Validierung
    * Validierungsfehler → 400 Bad Request

* **Error Handling:**
    * Eigene Exceptions definieren
    * `@RestControllerAdvice` für globales Exception Handling
    * **RFC 7807 ProblemDetail:** Standard-Format für Fehler-Responses

* **@WebMvcTest:**
    * Sliced Test: Lädt nur Web-Layer
    * MockMvc für HTTP-Tests
    * `@MockitoBean` für Dependencies

* **Live-Coding: Route-API**
    * REST-Controller mit DTOs implementieren
    * Validierung hinzufügen
    * Test mit `@WebMvcTest`

## 6. Wrap-Up

**Lernziel:** Gelerntes zusammenfassen und Ausblick geben.

### Themen:
* **Zusammenfassung:**
    * Architektur: Controller → Service → Repository → Database
    * Spring Boot Philosophie: Convention over Configuration, Opinionated Defaults

* **Was haben wir gebaut?**
    * Flight Service mit REST-API und H2-Datenbank
    * Tests auf allen Ebenen: Unit, Sliced, Integration

* **Was fehlt für Produktion?**
    * Security (Spring Security)
    * Echte Datenbank (PostgreSQL/MySQL)
    * Docker für Deployment
    * Monitoring (Actuator)
    * API-Dokumentation (OpenAPI/Swagger)

* **Ressourcen:**
    * spring.io/guides
    * docs.spring.io
    * Baeldung.com
    * Craig Walls' Bücher
