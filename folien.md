---
marp: true
theme: spring-boot-lecture
paginate: true
header: "Motivation & Setup"
footer: "Einführung in Spring Boot | Jan Tuttas | Januar 2026"
---

<!-- _class: lead -->

# Einführung in Spring Boot

## Jan Tuttas

---

# About me: Jan Tuttas

**Beruflich**
- Lead IT Consultant @ msg for automotive
- Softwarearchitekt & Technical Lead
- Diplom Informatik, TU Braunschweig

**Erfahrung**
- Spring Framework seit 2015
- Spring Boot seit 2018
- Spring Boot Trainer seit 2 Jahren

<figure class="right-figure">
<img src="images/jan.jpg" width="200">
<figcaption>
<a href="https://www.linkedin.com/in/jan-tuttas-b1a605244"><img class="icon" src="images/linkedin.svg"> LinkedIn</a><br>
<a href="https://github.com/jt182"><img class="icon" src="images/github.svg"> GitHub</a><br>
<a href="mailto:jan@tuttas.eu"><img class="icon" src="images/email.svg"> jan@tuttas.eu</a>
</figcaption>
</figure>

---

# Themen

<div class="grid-3col">

<div class="card">

#### Motivation & Setup

Warum Spring Boot?
Projekt-Initialisierung

</div>

<div class="card">

#### DI & Beans

IoC Container
Constructor Injection

</div>

<div class="card">

#### Configuration

application.yml
Profile & Logging

</div>

<div class="card">

#### Data Access & JPA

Entities & Repositories
Query Derivation

</div>

<div class="card">

#### REST APIs

@RestController
Error Handling

</div>

<div class="card">

#### Wrap-Up

Zusammenfassung
Ressourcen

</div>

</div>

---

# Das Projekt: Flight Service

<!--
Use-Case: "Wir bauen heute einen echten Backend-Service für eine fiktive Airline. Das ist kein Spielzeug-Beispiel - die Konzepte sind 1:1 übertragbar auf echte Projekte."
Scope: Route = Flugroute von A nach B, einfach genug für den Workshop, komplex genug für alle Konzepte.
Architektur-Vorschau: "Am Ende haben wir: Eine REST-API, die Routen aus einer Datenbank liest und schreibt. Alles mit Tests abgesichert."
-->

**Szenario:** Ein Backend-Service für eine Airline

<div class="grid-2col">

<div class="card">

#### Flight Service

- Flugrouten verwalten (Routes)
- REST-API für andere Systeme
- Persistierung in Datenbank

</div>

<div>

**Was entsteht:**
- `Route` Entity (Origin → Destination)
- Repository für Datenbankzugriff
- REST-API für CRUD-Operationen

</div>

</div>

---

# Spielregeln

<!--
Remote-Setting: "Da wir uns remote treffen, ein paar Spielregeln für einen reibungslosen Ablauf."
Erwartungen: "Wir machen echtes Live-Coding. Da passieren Fehler. Das ist gut - so lernt man am meisten!"
Link zu den Unterlagen: https://github.com/jt182/Spring-Boot-Basics
-->

**Während der Vorlesung:**
- Mikrofon bitte **stumm** schalten
- Kamera gerne **an** (für Interaktion)
- Fragen sind erwünscht → **Hand heben**

**Live-Coding:**
- Code wird live entwickelt
- Fehler gehören dazu
- Erst zuschauen, später selbst ausprobieren

<figure class="right-figure">
<img src="images/qrcode-github.svg" width="250">
<figcaption>Unterlagen</figcaption>
</figure>

---

<!-- _class: chapter -->

# Motivation & Setup

<!--
1. Warum Spring Boot?
2. Die 3 Säulen von Spring Boot
3. Praxis: Projekt-Setup mit start.spring.io
-->

## Warum Spring Boot? Was macht es besonders?

---

# Das Problem

<!--
Früher war Java-Entwicklung für Webanwendungen sehr aufwändig.
Viele Entwickler sind zu Node.js, Ruby on Rails abgewandert.
Spring Boot ist die Antwort darauf.
Frage: "Wer hat schon mal mit XML-Konfigurationen gearbeitet?"
-->

**Java Enterprise Development war lange Zeit...**

- Komplexe XML-Konfigurationen
- Viele Boilerplate-Code
- Abhängigkeitskonflikte ("Dependency Hell")
- Lange Setup-Zeiten für neue Projekte
- Externe Application Server notwendig

---

# Spring Framework vs. Spring Boot

<!--
Wichtig: Spring Boot ist KEIN Ersatz für Spring Framework!
Es ist eine Erweiterung, die das Framework einfacher nutzbar macht.
Das Spring Framework läuft unter der Haube weiter.
Analogie: "Auto mit Automatikgetriebe – Technik gleich, Bedienung einfacher."
-->

| Spring Framework | Spring Boot |
|------------------|-------------|
| Mächtig & flexibel | Opinionated & pragmatisch |
| Explizite Konfiguration | Convention over Configuration |
| Steile Lernkurve | Schneller Einstieg |
| Maximale Kontrolle | Sinnvolle Defaults |

---

# Spring Boot Versionen

| Version | Release | Spring Framework | Java | Support bis |
|---------|---------|------------------|------|-------------|
| **4.0** | **Nov 2025** | **7.0** | **17 - 25** | **Dez 2026** |
| 3.5 | Mai 2025 | 6.2 | 17 - 25 | Jun 2026 |
| 3.4 | Nov 2024 | 6.2 | 17 - 24 | ~~Dez 2025~~ |
| 3.3 | Mai 2024 | 6.1 | 17 - 23 | ~~Jun 2025~~ |
| 2.7 | Mai 2022 | 5.3 | 8 - 21 | ~~Nov 2023~~ |

<div class="footnote">
<ol><li><a href="https://spring.io/projects/spring-boot#support">Spring Boot Support Matrix</a></li></ol>
</div>

---

# Die Ziele von Spring Boot

<!--
Effektiv = Die richtigen Dinge tun (korrekte Konfiguration).
Effizient = Die Dinge richtig tun (weniger Aufwand).
Frage: "Wie lange dauert ein neues Web-Projekt mit Spring Boot?" → Unter 5 Minuten!
-->

<div class="grid-2col">

<div class="card">

#### Effektivität

Korrekte Konfiguration durch bewährte Defaults

→ "Convention over Configuration"

</div>

<div class="card">

#### Effizienz

Auto-Configuration reduziert manuellen Aufwand

→ Weniger Boilerplate, mehr Produktivität

</div>

</div>

---

# Die 3 Säulen von Spring Boot

<!--
Struktur: Diese drei Konzepte sind das Fundament von Spring Boot. Jede Säule löst ein konkretes Problem.
-->

<div class="grid-3col">

<div class="card">

#### Dependency Management

- Starter-POMs bündeln Dependencies
- Versionskonflikte vermieden
- Ein Import, alles dabei

</div>

<div class="card">

#### Auto-Configuration

- Automatische Bean-Konfiguration
- Classpath-basierte Erkennung
- Sinnvolle Defaults

</div>

<div class="card">

#### Run Anywhere

- Embedded Server (Tomcat)
- Fat JAR für einfaches Deployment
- Ideal für Container

</div>

</div>

---

# Säule 1: Dependency Management

<!--
Problem: Transitive Abhängigkeiten führen zu Konflikten.
Library A braucht Jackson 2.15, Library B braucht Jackson 2.14 – wer gewinnt?
Lösung: Spring Boot definiert eine Bill of Materials (BOM). Alle Versionen sind aufeinander abgestimmt und getestet. Man muss nur den Starter hinzufügen, keine Versionen angeben

-->

**Das Problem:** Welche Versionen passen zusammen?

```groovy
// Ohne Spring Boot: Versionskonflikte!
implementation 'org.springframework:spring-web:6.1.0'
implementation 'org.springframework:spring-core:6.0.5' // Konflikt!
implementation 'com.fasterxml.jackson:jackson-core:2.15.0'
```

**Die Lösung:** Starter Dependencies

```groovy
// Mit Spring Boot: Eine Zeile, alles kompatibel
implementation 'org.springframework.boot:spring-boot-starter-webmvc'
```

---

# Was steckt in einem Starter?

<!--
Praktisch zeigen: ./gradlew dependencies im Projekt ausführen.
"Ihr müsst euch nicht merken, was alles drinsteckt. Spring Boot hat das für euch kuratiert."
-->

**`spring-boot-starter-webmvc` enthält:**

- Spring MVC
- Jackson (JSON)
- Embedded Tomcat
- Validation
- ... und alle transitiven Abhängigkeiten

→ **Kuratierte, getestete Dependency-Sets**

---

# Säule 2: Auto-Configuration

<!--
Das "Magie"-Gefühl: Spring Boot schaut beim Start, was im Classpath liegt.
Basierend darauf werden automatisch Beans erstellt.
Beispiel: spring-boot-starter-webmvc → Tomcat wird gestartet, Jackson für JSON konfiguriert.
-->

**Das Prinzip:**

> "Wenn X im Classpath liegt, konfiguriere Y automatisch"

**Beispiele:**
- Tomcat im Classpath → Embedded Server wird gestartet
- H2 im Classpath → DataSource wird automatisch konfiguriert
- JPA im Classpath → EntityManager wird bereitgestellt

---

# Auto-Configuration in Aktion

<!--
Nur EINE Annotation und ZWEI Zeilen Code – trotzdem läuft ein vollständiger Webserver!
Frage: "Wo ist der Tomcat konfiguriert? Wo steht Port 8080?" → Nirgends! Das ist Auto-Configuration!
Hinweis auf später: Wie man Defaults überschreibt, sehen wir im Kapitel zu Properties.
-->

```java
// Das ist alles, was du brauchst:
@SpringBootApplication
public class FlightApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightApplication.class, args);
    }
}
```

**Spring Boot erkennt automatisch:**
- Web-Starter → Startet Tomcat auf Port 8080
- Konfiguriert Jackson für JSON
- Setzt sinnvolle Defaults

---

# Säule 3: Run Anywhere

<!--
Historischer Kontext: Früher WAR-Files auf Application Server deployen.
Problem: "Works on my machine" vs. Produktionsserver.
Fat JAR: Alles in einer Datei – Code + Dependencies + Server.
Perfekt für Docker: FROM openjdk:25 / COPY app.jar / CMD java -jar app.jar
-->

**Traditionell:**
```
App.war  →  Deploy auf  →  Tomcat/JBoss/WebSphere
```

**Mit Spring Boot:**
```
java -jar app.jar  →  Fertig!
```

- Embedded Server (Tomcat, Jetty, Undertow)
- "Fat JAR" enthält alles
- Ideal für Container (Docker, Kubernetes)

---

# Zusammenfassung

<div class="grid-3col">

<div class="card">

#### Dependency Management

- Starter-POMs bündeln Dependencies
- Versionskonflikte vermieden

</div>

<div class="card">

#### Auto-Configuration

- Automatische Bean-Konfiguration
- Classpath-basierte Erkennung

</div>

<div class="card">

#### Run Anywhere

- Embedded Server (Tomcat)
- Fat JAR für einfaches Deployment

</div>

</div>

---

# Live-Coding

## Projekt-Setup mit start.spring.io

Spring Initializr nutzen, Projekt importieren und erste Anwendung starten

---

<!-- _class: chapter -->

# Dependency Injection & Beans

<!--
1. Der Spring Container
2. Beans & Stereotypen
3. Dependency Injection
4. Praxis: Unser erster Service
-->

## Der Spring Container und seine Magie

---

<!-- header: "Dependency Injection & Beans" -->

# Das Problem: Tight Coupling

<!--
Frage: "Was ist das Problem mit diesem Code?"
Controller muss wissen, wie FlightService erstellt wird.
Was wenn FlightService selbst Dependencies hat? Wie testet man das?
Analogie: "Ihr müsstet jedes Mal den Motor selbst zusammenbauen, um Auto zu fahren."
-->

```java
public class FlightController {

    public List<FlightDto> getFlights() {
        // Direkte Instanziierung - Tight Coupling!
        var service = new FlightService();
        return service.findAll();
    }
}
```

**Probleme:**
- Controller ist abhängig von konkreter Implementierung
- Schwer testbar (kein Mocking möglich)
- Änderungen erfordern Code-Anpassungen

---

# Die Lösung: Inversion of Control

<!--
Konzept: "Inversion" = Umkehrung der Kontrolle.
Nicht die Klasse erstellt ihre Dependencies, sondern sie bekommt sie von außen.
Hollywood-Prinzip: "Don't call us, we'll call you."
Im Test kann ich jetzt einen Mock übergeben – viel einfacher!
-->

```java
public class FlightController {

    private final FlightService service;

    // Dependency wird von außen übergeben
    public FlightController(FlightService service) {
        this.service = service;
    }
}
```

**Vorteile:**
- Lose Kopplung
- Einfach testbar
- Flexibel austauschbar

---

# Der Spring Container

<!--
ASCII-Diagramm erklären:
- Container = "Fabrik" für Objekte
- Beans leben im Container
- Container verbindet sie miteinander

Begriffe erklären:
- IoC Container = Das Konzept/Prinzip ("Inversion of Control" – nicht ihr erstellt Objekte, der Container macht das)
- ApplicationContext = Die konkrete Implementierung in Spring (das ist der "echte" Container)
- BeanFactory = Basis-Interface, ApplicationContext erweitert es um Features wie Events, i18n, etc.

In der Praxis: Ihr arbeitet immer mit ApplicationContext, BeanFactory ist nur für tiefes Spring-Verständnis relevant.

LIFECYCLE:
1. Container startet
2. Beans werden instanziiert
3. Dependencies werden injiziert
4. Beans sind einsatzbereit
5. Bei Shutdown: Beans werden aufgeräumt
-->

```
┌───────────────────────────────────────────────────────────────┐
│                    Spring IoC Container                       │
│                                                               │
│  ┌───────────────┐   ┌─────────────┐   ┌────────────────┐     │
│  │ Route         │   │ Route       │   │ Route          │     │
│  │ Controller    │──>│ Service     │──>│ Repository     │     │
│  └───────────────┘   └─────────────┘   └────────────────┘     │
│                                                               │
│         Spring verbindet die Beans automatisch                │
└───────────────────────────────────────────────────────────────┘
```

Der Container:
- Erstellt Objekte (Beans) beim Start
- Verwaltet deren Lebenszyklus
- Verbindet Abhängigkeiten automatisch (Dependency Injection)

---

# Was ist ein Bean?

<!--
Ein Bean ist KEIN spezielles Java-Konstrukt – einfach ein Objekt, das Spring verwaltet.
Singleton: Es gibt nur EINE Instanz im Container. Egal wie oft injiziert, immer dasselbe Objekt.
Anders als klassisches Singleton-Pattern: Spring kümmert sich darum!
-->

Ein **Bean** ist ein Objekt, das vom Spring Container verwaltet wird.

**Eigenschaften:**
- Wird vom Container instanziiert
- Lebenszyklus wird vom Container gesteuert
- Standardmäßig **Singleton** (eine Instanz pro Container)
- Kann in andere Beans injiziert werden

---

# Beans definieren: @Component

<!--
Live zeigen: Klasse ohne Annotation → kein Bean. Mit @Component → Bean.
Häufiger Fehler: "NoSuchBeanDefinitionException" = Annotation vergessen!
-->

```java
@Component
public class FlightService {

    public List<String> findAll() {
        return List.of("LH123", "BA456", "AF789");
    }
}
```

`@Component` markiert eine Klasse als Bean.

Spring findet diese Klasse durch **Component Scanning**.

---

# Component Scan

<!--
Scan startet beim Package der Main-Klasse, alle Sub-Packages werden gescannt.
Klassen außerhalb werden NICHT gefunden!
Häufiger Fehler: Klasse in anderem Package → nicht gefunden.
Lösung: Richtige Package-Struktur oder @ComponentScan(basePackages = "...").
-->

```java
@SpringBootApplication  // Startet Component Scan
public class FlightApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightApplication.class, args);
    }
}
```

Spring scannt ab dem Package der Main-Klasse:

```
com.airline.flightservice/
├── FlightApplication.java     ← @SpringBootApplication
├── service/
│   └── FlightService.java     ← wird gefunden
└── controller/
    └── FlightController.java  ← wird gefunden
```

---

# Stereotypen

<!--
Technisch sind alle gleich! Aber sie dokumentieren die Absicht.
Service Layer Pattern: Controller → Service → Repository → Database.
@Service = Business-Logik, orchestriert Datenzugriffe.
@Repository hat Zusatznutzen: Exception Translation (DB-Exceptions → Spring DataAccessExceptions).
-->

| Annotation | Zweck |
|------------|-------|
| `@Component` | Generische Komponente |
| `@Service` | Business-Logik |
| `@Repository` | Datenzugriff |
| `@Controller` | Web MVC Controller |
| `@RestController` | REST API Controller |

Funktional identisch, aber semantisch aussagekräftig!

---

# Constructor Injection

<!--
Best Practice! Felder können final sein → Immutability.
Vollständigkeit: Objekt ist nach Konstruktor vollständig initialisiert.
Testbarkeit: Einfach mit new instanziieren, Mock-Objekte übergeben.
Fail-Fast: Fehler beim Startup, nicht zur Laufzeit.
-->

```java
@Service
public class FlightService {
    private final FlightRepository repository;

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }
}
```
**Vorteile:**
- **Immutability:** Felder können `final` sein
- **Vollständigkeit:** Alle Dependencies bei Erstellung vorhanden
- **Testbarkeit:** Einfaches Mocking im Unit Test

**Hinweis:** `@Autowired` ist bei nur einem Constructor optional (seit Spring 4.3)

---

# Setter Injection

```java
@Service
public class FlightService {
    private FlightRepository repository;
    private NotificationService notifications;

    @Autowired
    public void setRepository(FlightRepository repository) {
        this.repository = repository;
    }

    @Autowired(required = false)  // Optional!
    public void setNotifications(NotificationService notifications) {
        this.notifications = notifications;
    }
}
```

**Anwendungsfall:** Optionale Dependencies oder zirkuläre Abhängigkeiten

---

# Field Injection

<!--
Anti-Pattern! Man sieht die Dependencies nicht im Constructor.
Felder können nicht final sein, nur durch Reflection testbar.
Ausnahme: In Tests ist @Autowired auf Feldern OK, weil Tests sowieso mit Spring laufen.
-->

```java
@Service
public class FlightService {

    @Autowired
    private FlightRepository repository;
}
```

**Nachteile:**
- Felder können nicht `final` sein
- Versteckte Dependencies
- Schwerer zu testen (Reflection nötig)
- Kein Fail-Fast bei fehlenden Dependencies

---

# Dependency Injection - Überblick

<div class="grid-3col">
<div class="card">

#### Constructor Injection
- Empfohlene Variante
- Felder können `final` sein
- Alle Dependencies sofort sichtbar
- **Best Practice!**

</div>
<div class="card">

#### Setter Injection
- Optional Dependencies
- Nachträgliche Änderung möglich
- Für optionale Konfiguration

</div>
<div class="card">

#### Field Injection
- Weniger Code
- Aber: Versteckte Dependencies
- Schwerer testbar
- **Vermeiden!**

</div>
</div>

---

# Bean Scopes

<!--
Singleton ist fast immer der richtige Scope (Default).
Vorsicht bei Prototype: Wenn ein Singleton ein Prototype injiziert bekommt, wird Prototype nur EINMAL erstellt!
Lösung: ObjectFactory<T>, @Lookup oder Provider.
-->

| Scope | Beschreibung |
|-------|--------------|
| `singleton` | Eine Instanz pro Container (Default) |
| `prototype` | Neue Instanz bei jeder Injection |
| `request` | Eine Instanz pro HTTP Request |
| `session` | Eine Instanz pro HTTP Session |

```java
@Service
@Scope("prototype")  // Neue Instanz bei jeder Injection
public class FlightService { }
```

---

# Zusammenfassung

<div class="grid-3col">

<div class="card">

#### Spring Container

- Verwaltet Beans & Lebenszyklus
- Injiziert Dependencies automatisch

</div>

<div class="card">

#### Beans definieren

- `@Component`, `@Service`, `@Repository`
- Component Scan findet Klassen

</div>

<div class="card">

#### Best Practice

- Constructor Injection verwenden
- Felder `final` deklarieren

</div>

</div>

---

# Live-Coding

## Erster Service mit Integration Test

`RouteService` erstellen und mit `@SpringBootTest` testen

---

<!-- _class: chapter -->

# Configuration & Properties

<!--
1. Externalisierte Konfiguration
2. application.yml
3. @Value Injection
4. Praxis: Konfigurierbare Routes
-->

## Externalisierte Konfiguration für flexible Anwendungen

---

<!-- header: "Configuration & Properties" -->

# Das Problem: Hardcoded Values

<!--
Szenarien durchgehen: Was wenn Default-Flughafen sich ändert? Unterschiedliche Werte für Dev/Prod?
"Hardcoded Values sind ein Code Smell. Konfiguration gehört nicht in den Code!"
-->

```java
@Service
public class RouteService {

    public String getDefaultRoute() {
        return "Frankfurt -> Berlin";  // Hardcoded!
    }
}
```

**Probleme:**
- Änderungen erfordern Neu-Kompilierung
- Unterschiedliche Werte für Dev/Prod?
- Secrets im Code?

---

# Die Lösung: Externalisierte Konfiguration

<!--
12-Factor App Prinzip: Config gehört in die Umgebung, nicht in den Code.
Vorteile: Ein JAR für alle Umgebungen, Config zur Laufzeit änderbar, Secrets nie im Git.
-->

**Trennung von Code und Konfiguration**

```
Code (JAR)     +     Config (Properties)     =     Anwendung
```

**Vorteile:**
- Gleicher Code, unterschiedliche Umgebungen
- Keine Neu-Kompilierung bei Config-Änderungen
- Secrets außerhalb des Repositories

---

# application.yml

<!--
YAML-Vorteile: Hierarchische Struktur besser lesbar, weniger Redundanz.
Es gibt auch application.properties (Key=Value), aber YAML ist heute Standard.
Spring Boot lädt die Datei automatisch aus src/main/resources.
-->

```yaml
# src/main/resources/application.yml

# Server-Konfiguration
server:
  port: 8080

# Eigene Properties
route:
  default-origin: Frankfurt
  default-destination: Berlin

# Logging
logging:
  level:
    root: INFO
    com.airline: DEBUG
```

Spring Boot lädt diese Datei automatisch!

---

# Konfigurationsquellen

<!--
Hierarchie ist wichtig! Command Line überschreibt alles – praktisch für schnelle Tests.
Naming Convention: server.port → SERVER_PORT (Punkte zu Unterstrichen, uppercase).
Docker: docker run -e SERVER_PORT=9090 myapp
-->

**Hierarchie (höher = überschreibt niedriger):**

1. Command Line Arguments (`--server.port=9090`)
2. System Properties (`-Dserver.port=9090`)
3. Environment Variables (`SERVER_PORT=9090`)
4. application.yml / application.properties
5. Default Values

---

# @Value Injection

<!--
${} ist Spring Expression Language (SpEL). Property-Name muss exakt matchen.
Häufiger Fehler: @Value("route.default-origin") – fehlt ${}!
Was wenn Property fehlt? Exception beim Startup → Fail Fast!
-->

```java
@Service
public class RouteService {

    @Value("${route.default-origin}")
    private String defaultOrigin;

    @Value("${route.default-destination}")
    private String defaultDestination;

    public String getDefaultRoute() {
        return defaultOrigin + " -> " + defaultDestination;
    }
}
```

Spring ersetzt `${...}` durch den Wert aus der Config.

---

# @Value mit Default-Werten

<!--
Syntax: ${property:default} – Doppelpunkt trennt Property-Name und Default-Wert.
Wann Default-Werte? Optionale Konfiguration, sinnvolle Defaults für Entwicklung.
Nie für Pflicht-Konfiguration – Fail Fast ist besser!
-->

```java
@Service
public class RouteService {

    @Value("${route.default-origin:München}")
    private String defaultOrigin;

    @Value("${route.max-routes:10}")
    private int maxRoutes;
}
```

**Syntax:** `${property.name:defaultValue}`

Falls Property nicht gesetzt → Default wird verwendet.

---

# Constructor Injection mit @Value

<!--
Best Practice: Felder bleiben final → Immutable Service.
Testbar ohne Spring: new RouteService("Frankfurt", "Berlin")
-->

```java
@Service
public class RouteService {

    private final String defaultOrigin;
    private final String defaultDestination;

    public RouteService(
            @Value("${route.default-origin}") String defaultOrigin,
            @Value("${route.default-destination}") String defaultDestination) {
        this.defaultOrigin = defaultOrigin;
        this.defaultDestination = defaultDestination;
    }
}
```

Besser: Felder bleiben `final`!

---

# Profile: Technische Varianten

<!--
WICHTIG: Profile sind für technische Unterschiede – NICHT für Stages (dev/prod)!
Gute Use Cases: DB-Backends (H2, PostgreSQL), Storage Provider (AWS S3, Local).
Stage-Config gehört in die Infrastruktur (Kubernetes ConfigMaps, Secrets).
Das JAR bleibt identisch für alle Umgebungen.
-->

**Profile für technische Unterschiede**

```
src/main/resources/
├── application.yml          # Basis-Config
├── application-h2.yml       # In-Memory DB (Tests)
├── application-postgres.yml # PostgreSQL
```

**Aktivieren:**
```bash
java -jar app.jar --spring.profiles.active=postgres
```

Stage-Config (dev/prod) gehört in die Infrastruktur!

---

# Profile-Beispiel: Datenbank

**application-h2.yml:** (für Tests)
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
```

**application-postgres.yml:**
```yaml
spring:
  datasource:
    url: ${DATABASE_URL}  # Aus Umgebung!
    driver-class-name: org.postgresql.Driver
```

---

# Logging

<!--
Verwendet immer {} Placeholders statt String-Konkatenation mit +!
Grund: Bei + wird der String auch gebaut wenn Log-Level gar nicht aktiv ist → ineffizient.
Log-Level Hierarchie: TRACE < DEBUG < INFO < WARN < ERROR
-->

**Spring Boot verwendet SLF4J + Logback (Default)**

```java
private static final Logger log = LoggerFactory.getLogger(RouteService.class);

log.info("Finding route with id: {}", id);  // Placeholder statt +
```

**Log-Level in application.yml:**
```yaml
logging:
  level:
    root: WARN
    com.airline.flightservice: DEBUG
```

**Log-Level:** `TRACE` < `DEBUG` < `INFO` < `WARN` < `ERROR`

---

# Zusammenfassung

<div class="grid-3col">

<div class="card">

#### Externalisierte Config

- Trennung Code & Config
- application.yml als Quelle

</div>

<div class="card">

#### @Value Injection

- `${property.name}`
- `${property:default}`

</div>

<div class="card">

#### Profile & Logging

- Profile für Tech-Varianten
- SLF4J + Logback

</div>

</div>

---

# Live-Coding

## Konfigurierbare Routes

`RouteService` mit `@Value` und `application.yml` erweitern

---

<!-- _class: chapter -->

# Data Access & JPA

<!--
1. Das Problem: Boilerplate bei Datenbankzugriffen
2. Spring Data JPA
3. Entities & Repositories
4. Praxis: Route-Entity mit Repository
-->

## Vom Interface zur Datenbank mit Spring Data

---

<!-- header: "Data Access & JPA" -->

# Das Problem: JDBC Boilerplate

<!--
Frage: "Wer hat schon mal mit JDBC gearbeitet? Was war nervig?"
Connection holen, Statement erstellen, SQL schreiben, ResultSet durchiterieren, manuell mappen...
Und das für jede Query! findAll, findById, save, delete – immer wieder derselbe Aufwand.
-->

```java
public List<Route> findAll() {
    var routes = new ArrayList<Route>();
    try (var conn = dataSource.getConnection();
         var stmt = conn.createStatement();
         var rs = stmt.executeQuery("SELECT * FROM routes")) {

        while (rs.next()) {
            var route = new Route();
            route.setId(rs.getLong("id"));
            route.setOrigin(rs.getString("origin"));
            // ... und so weiter für jedes Feld
        }
    }
    return routes;
}
```

**Probleme:** SQL schreiben, manuelles Mapping, Ressourcen-Management

---

# JPA & Hibernate

<!--
JPA = Java Persistence API, ein Standard (Spezifikation). Definiert Annotations wie @Entity, @Id.
Hibernate = die populärste Implementierung dieses Standards (es gibt auch EclipseLink, OpenJPA).
ORM = Object-Relational Mapping: Java-Objekte werden automatisch auf DB-Zeilen gemappt.
Mit @Entity und @Id sagst du Hibernate: "Diese Klasse entspricht einer Tabelle".
Hibernate generiert das SQL, übernimmt das Mapping, verwaltet Connections.
-->

**JPA** = Standard — **Hibernate** = Implementierung

```java
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
}
```

- `@Entity` → Klasse wird zur Tabelle
- `@Id` → Feld wird zum Primärschlüssel
- Hibernate generiert SQL & übernimmt Mapping

---

# JPA in der Praxis

<!--
EntityManager ist die zentrale JPA-API: persist, find, createQuery, remove.
JPQL statt SQL – referenziert Klassen und Properties statt Tabellen und Spalten.
Problem: Dieser Code ist für JEDE Entity fast identisch! Route, Flight, Booking...
Das ist viel besser als JDBC, aber immer noch repetitiv. Hier setzt Spring Data an.
-->

```java
@Repository
public class RouteRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Route> findAll() {
        return em.createQuery("SELECT r FROM Route r", Route.class).getResultList();
    }

    public Route save(Route route) {
        em.persist(route);
        return route;
    }
}
```

Besser als JDBC – aber immer noch **repetitiver Boilerplate**!

---

# Spring Data JPA

<!--
Spring Data geht einen Schritt weiter: Du definierst nur ein Interface!
Spring scannt nach Interfaces die Repository erweitern, erstellt zur Laufzeit einen Proxy.
Der Proxy nutzt intern JPA/Hibernate – du schreibst keinen Implementierungscode.
Empfehlung: ListCrudRepository reicht für die meisten Fälle, gibt List statt Iterable zurück.
-->

**Repository-Pattern:** Interface statt Implementierung

```java
public interface RouteRepository extends ListCrudRepository<Route, Long> {
}
```

Spring generiert automatisch:
- `save(Route)` → INSERT oder UPDATE
- `findById(Long)` → SELECT WHERE id = ?
- `findAll()` → SELECT *
- `deleteById(Long)` → DELETE WHERE id = ?

---

# Die Schichten im Überblick

<!--
Wichtig zu verstehen: Spring Data ersetzt JPA/Hibernate nicht, es baut darauf auf!
Unter der Haube läuft weiterhin Hibernate, das SQL generiert und mit JDBC zur DB spricht.
Du arbeitest nur mit der obersten Schicht – aber gut zu wissen, was darunter passiert.
Bei Problemen (Performance, Queries) musst du manchmal tiefer schauen.
-->

```
┌─────────────────────────────────────┐
│         Dein Code                   │  ← Entity + Repository-Interface
├─────────────────────────────────────┤
│       Spring Data JPA               │  ← Generiert Implementierung
├─────────────────────────────────────┤
│        JPA / Hibernate              │  ← ORM: Mapping, SQL-Generierung
├─────────────────────────────────────┤
│            JDBC                     │  ← Verbindung zur Datenbank
└─────────────────────────────────────┘
```

Spring Data baut auf JPA/Hibernate auf – ersetzt es nicht!

---

# Query Derivation

<!--
Spring parst den Methodennamen und generiert daraus eine Query.
Validierung beim Startup (Fail Fast!) – Typo im Methodennamen = Exception sofort.
Demo: findByOrgin statt findByOrigin → Exception beim Start.
-->

**Queries durch Methodennamen:**

```java
public interface RouteRepository extends ListCrudRepository<Route, Long> {

    List<Route> findByOrigin(String origin);

    List<Route> findByOriginAndDestination(String origin, String destination);

    Optional<Route> findFirstByOriginOrderByIdDesc(String origin);

    boolean existsByOrigin(String origin);
}
```

Spring parst den Methodennamen und generiert SQL!

---

# Query Derivation: Keywords

| Keyword | Beispiel | SQL |
|---------|----------|-----|
| `findBy` | `findByOrigin` | `WHERE origin = ?` |
| `And` | `findByOriginAndDest` | `WHERE ... AND ...` |
| `Or` | `findByOriginOrDest` | `WHERE ... OR ...` |
| `OrderBy` | `findByOriginOrderById` | `ORDER BY id` |
| `Containing` | `findByOriginContaining` | `LIKE %?%` |
| `Between` | `findByIdBetween` | `BETWEEN ? AND ?` |

<div class="footnote">
<ol><li><a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">Spring Data JPA Query Methods Reference</a></li></ol>
</div>

---

# Custom Queries mit @Query

<!--
Wann nötig? Query Derivation zu lang/unlesbar, komplexe JOINs, DB-spezifische Features.
JPQL (Default) referenziert Entity & Property-Namen, ist portabel.
Native SQL referenziert Tabelle & Spalten-Namen, nicht portabel.
SQL-Injection: Beide Varianten sind SICHER! Parameter mit :name oder ?1 werden nie konkateniert.
-->

**Wenn Query Derivation nicht reicht:**

```java
public interface RouteRepository extends ListCrudRepository<Route, Long> {

    // JPQL (Default) - referenziert Entity & Properties
    @Query("SELECT r FROM Route r WHERE r.origin = :city")
    List<Route> findRoutesFromCity(@Param("city") String city);

    // Native SQL - referenziert Tabelle & Spalten
    @Query(value = "SELECT * FROM route WHERE origin LIKE ?1%",
           nativeQuery = true)
    List<Route> findByOriginPrefix(String prefix);
}
```

---

# JPQL vs Native SQL

| Aspekt | JPQL | Native SQL |
|--------|------|------------|
| Annotation | `@Query("...")` | `@Query(value="...", nativeQuery=true)` |
| Referenz | Entity (`Route`) | Tabelle (`route`) |
| Felder | Properties (`origin`) | Spalten (`origin`) |
| Portabel | Ja | Nein (DB-spezifisch) |

**Empfehlung:** JPQL bevorzugen, Native nur bei DB-Features

---

# Setup: Dependencies

<!--
runtimeOnly für H2: Nur zur Laufzeit nötig, nicht zum Kompilieren. Code arbeitet mit JPA-Interfaces.
Auto-Configuration Magie: H2 im Classpath → DataSource wird automatisch konfiguriert!
-->

**build.gradle:**

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'  // In-Memory DB für Entwicklung

    // Für Produktion (via Profile):
    // runtimeOnly 'org.postgresql:postgresql'
}
```

**Auto-Configuration:**
- H2 im Classpath → DataSource wird konfiguriert
- JPA im Classpath → EntityManager wird bereitgestellt

---

# H2 Console aktivieren

<!--
Nur für Entwicklung! In Produktion unbedingt deaktivieren (ist Default).
show-sql: true zeigt alle generierten SQL-Queries im Log – hilfreich zum Debuggen.
ddl-auto: create-drop = Tabellen beim Start erstellen, beim Stopp löschen.
-->

**application.yml:**

```yaml
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:flightdb
  jpa:
    show-sql: true
```

**Zugriff:** http://localhost:8080/h2-console

Praktisch für Debugging während der Entwicklung!

---

# @DataJpaTest: Sliced Testing

<!--
Sliced Testing: @DataJpaTest lädt nur JPA-Komponenten – Entities, Repositories, EntityManager.
Was wird NICHT geladen? Controller, Services, andere Beans.
Schnell (~1-2s) und automatischer Rollback nach jedem Test.
-->

```java
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
}
```

---

# @DataJpaTest vs @SpringBootTest

| @DataJpaTest | @SpringBootTest |
|--------------|-----------------|
| Lädt nur JPA-Komponenten | Lädt alles |
| Schnell (~1-2s) | Langsam (~5-10s) |
| Rollback nach jedem Test | Kein automatischer Rollback |
| Embedded DB (H2) | Konfigurierte DB |

**Regel:** Teste so spezifisch wie möglich!

→ Repository-Tests: `@DataJpaTest`
→ Integration über Schichten: `@SpringBootTest`

---

# Zusammenfassung

<div class="grid-3col">

<div class="card">

#### Spring Data JPA

- Interface definieren
- Spring implementiert
- Query Derivation

</div>

<div class="card">

#### Entity

- `@Entity`
- `@Id`
- `@GeneratedValue`

</div>

<div class="card">

#### Testing

- `@DataJpaTest`
- Schnelle Repository-Tests
- Automatischer Rollback

</div>

</div>

---

# Live-Coding

## Route-Entity mit Repository

`Route`-Entity und Repository erstellen, mit `@DataJpaTest` testen

---

<!-- _class: chapter -->

# REST APIs

<!--
1. REST-Grundlagen & HTTP (Verben, Status Codes)
2. @RestController & Mappings
3. DTOs für API-Entkopplung
4. Validierung mit Bean Validation
5. Exception Handling
6. Testing mit @WebMvcTest
-->

## HTTP-Schnittstellen mit Spring Web

---

<!-- header: "REST APIs" -->

# REST: **RE**presentational **S**tate **T**ransfer

<!--
Frage: "Wer hat schon mal eine REST-API benutzt? Z.B. App die Wetterdaten lädt?"
URI-Design: URL sollte die Ressource beschreiben, nicht die Aktion.
Also /routes/42 statt /getRouteById?id=42.
-->

**Prinzipien:**
- Ressourcen-orientiert (z.B. `/routes`, `/flights`)
- Zustandslos (kein Session-State am Server)
- Einheitliche Schnittstelle (HTTP-Verben)
- Standard-Formate (JSON, XML)

```
GET    /routes          → Alle Routes laden
GET    /routes/42       → Route mit ID 42 laden
POST   /routes          → Neue Route anlegen
PUT    /routes/42       → Route komplett ersetzen
DELETE /routes/42       → Route löschen
```

---

# HTTP-Verben & CRUD

<!--
Idempotent: Mehrfaches Ausführen hat gleichen Effekt wie einmaliges.
GET ist idempotent (10x laden = gleiches Ergebnis).
POST ist NICHT idempotent (10x posten = 10 neue Einträge!).
DELETE ist idempotent – auch wenn Ressource beim zweiten Mal schon weg ist.
-->

| HTTP-Verb | CRUD | Idempotent? | Request Body |
|-----------|------|-------------|--------------|
| `GET` | Read | Ja | Nein |
| `POST` | Create | Nein | Ja |
| `PUT` | Update (Replace) | Ja | Ja |
| `PATCH` | Update (Partial) | Nein | Ja |
| `DELETE` | Delete | Ja | Nein |

**Idempotent:** Mehrfache Ausführung = gleiches Ergebnis

---

# HTTP Status Codes

<!--
Status Codes sind Teil des HTTP-Protokolls – jede Response hat einen.
2xx = alles gut, 4xx = Client hat Fehler gemacht, 5xx = Server hat Fehler gemacht.
Diese Codes bestimmen, wie Clients die Response interpretieren.
-->

| Bereich | Bedeutung | Beispiele |
|---------|-----------|-----------|
| `2xx` | Erfolg | 200 OK, 201 Created, 204 No Content |
| `4xx` | Client-Fehler | 400 Bad Request, 404 Not Found |
| `5xx` | Server-Fehler | 500 Internal Server Error |

**Best Practices:**
- `200 OK` → Erfolgreicher GET, PUT
- `201 Created` → Erfolgreicher POST (+ Location Header)
- `204 No Content` → Erfolgreicher DELETE
- `404 Not Found` → Ressource nicht gefunden

---

# Setup: Dependencies

<!--
Analog zu spring-boot-starter-data-jpa für JPA.
spring-boot-starter-webmvc enthält alles für REST-APIs: Spring MVC, Jackson, Tomcat.
Auto-Configuration: Starter im Classpath → Tomcat wird gestartet, Jackson konfiguriert.
-->

**build.gradle:**

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webmvc'
}
```

**Auto-Configuration:**
- Spring MVC im Classpath → DispatcherServlet wird konfiguriert
- Jackson im Classpath → JSON-Serialisierung aktiviert
- Embedded Tomcat startet auf Port 8080

---

# @RestController

<!--
@RestController = @Controller + @ResponseBody.
@ResponseBody sorgt dafür, dass Return-Wert direkt als Response-Body geschrieben wird.
Constructor Injection für Repository – wie beim Service.
-->

```java
@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Route> findAll() {
        return repository.findAll();
    }
}
```

`@RestController` = `@Controller` + `@ResponseBody`

---

# Request Mappings

```java
@RestController
@RequestMapping("/routes")
public class RouteController {

    @GetMapping              // GET /routes
    public List<Route> findAll() { ... }

    @GetMapping("/{id}")     // GET /routes/42
    public Route findById(@PathVariable Long id) { ... }

    @PostMapping             // POST /routes
    public Route create(@RequestBody Route route) { ... }

    @DeleteMapping("/{id}")  // DELETE /routes/42
    public void delete(@PathVariable Long id) { ... }
}
```

---

# @PathVariable vs @RequestParam

<!--
PathVariable für IDs und eindeutige Identifikatoren: /routes/42
RequestParam für optionale Filter und Suchparameter: /routes?origin=Frankfurt
required = false macht Parameter optional, defaultValue setzt Default-Wert.
-->

```java
// Pfadvariable: Teil der URL
// GET /routes/42
@GetMapping("/{id}")
public Route findById(@PathVariable Long id) { ... }

// Query-Parameter: Nach dem ?
// GET /routes?origin=Frankfurt
@GetMapping
public List<Route> findByOrigin(
        @RequestParam(required = false) String origin) { ... }

// Kombination
// GET /routes/search?minDistance=100&maxDistance=500
@GetMapping("/search")
public List<Route> search(
        @RequestParam int minDistance,
        @RequestParam int maxDistance) { ... }
```

---

# @RequestBody

<!--
Jackson wird von Spring Boot automatisch konfiguriert – konvertiert JSON zu Java und zurück.
Content-Type: application/json muss gesetzt sein, sonst weiß Spring nicht wie zu parsen.
Häufiger Fehler: @RequestBody vergessen → route ist null!
-->

```java
@PostMapping
public RouteResponse create(@RequestBody RouteRequest request) {
```

**Request:**
```http
POST /routes
Content-Type: application/json

{
  "origin": "Frankfurt",
  "destination": "Berlin",
  "distanceKm": 545
}
```

Spring konvertiert JSON → Java-Objekt (via Jackson)

---

# DTOs: API von Datenbank entkoppeln

<!--
Wichtiges Konzept! Entity direkt im Controller ist ein Anti-Pattern.
Probleme: Mass Assignment (Client kann interne Felder setzen), Datenbankstruktur wird nach außen geleakt.
Lösung: DTOs als Zwischenschicht – volle Kontrolle über API-Kontrakt.
Für den Workshop zeigen wir die manuelle Konvertierung – in echten Projekten oft mit Libraries wie MapStruct.
-->

**Problem:** Entity direkt als @RequestBody / Response

- **Mass Assignment:** Client könnte `id` oder interne Felder setzen
- **Tight Coupling:** API-Änderung = Datenbank-Änderung
- **Security:** Interne Felder werden nach außen geleakt

**Lösung:** Data Transfer Objects (DTOs)

```
Client  ↔  DTO  ↔  Controller  ↔  Entity  ↔  Database
```

---

# DTO-Beispiel

<!--
Records sind ideal für DTOs: Immutable, kompakt, wenig Boilerplate.
Request DTO: Nur die Felder, die der Client setzen darf.
Response DTO: Nur die Felder, die der Client sehen darf.
-->

```java
// Request DTO - was der Client senden darf
public record RouteRequest(
        String origin,
        String destination,
        Integer distanceKm
) {}

// Response DTO - was der Client sehen darf
public record RouteResponse(
        Long id,
        String origin,
        String destination,
        Integer distanceKm
) {}
```

**Vorteile von Records:** Immutable, wenig Boilerplate, ideal für DTOs!

---

# ResponseEntity

<!--
Wann ResponseEntity? Wenn Status-Code wichtig ist (nicht nur 200), Header gesetzt werden müssen.
Für einfache GETs die immer erfolgreich sind reicht direkter Return.
Bei 201 Created sollte Location-Header mit URL der neuen Ressource gesetzt werden.
-->

```java
@GetMapping("/{id}")
public ResponseEntity<Route> findById(@PathVariable Long id) {
    return repository.findById(id)
            .map(ResponseEntity::ok)                    // 200 OK
            .orElse(ResponseEntity.notFound().build()); // 404 Not Found
}

@PostMapping
public ResponseEntity<Route> create(@RequestBody Route route) {
    var saved = repository.save(route);
    var location = URI.create("/routes/" + saved.getId());
    return ResponseEntity
            .created(location)  // 201 Created + Location Header
            .body(saved);
}
```

Volle Kontrolle über Status-Code und Header!

---

# Validierung: Das Problem

<!--
Bisher validieren wir nichts – der Client könnte leere Strings oder negative Werte senden.
Validierung gehört auf die DTOs, nicht auf die Entity (Separation of Concerns).
Bean Validation (Jakarta Validation) ist der Standard in Java.
-->

**Was passiert, wenn der Client ungültige Daten sendet?**

```json
{
  "origin": "",
  "destination": null,
  "distanceKm": -100
}
```

Ohne Validierung landet das in der Datenbank!

---

# Validierung mit Bean Validation

**DTOs mit Validierungs-Annotations erweitern:**

```java
public record RouteRequest(
        @NotBlank(message = "Origin darf nicht leer sein")
        String origin,
        @Min(value = 1, message = "Distanz muss mindestens 1 km sein")
        Integer distanceKm
) {}
```

**Im Controller aktivieren:**

```java
@PostMapping
public ResponseEntity<RouteResponse> create(@Valid @RequestBody RouteRequest request) {
```

`@Valid` löst die Validierung aus → bei Fehlern: 400 Bad Request

---

# Häufige Validierungs-Annotations

| Annotation | Prüft |
|------------|-------|
| `@NotNull` | Feld ist nicht null |
| `@NotBlank` | String ist nicht null/leer/whitespace |
| `@NotEmpty` | Collection/String ist nicht leer |
| `@Min(n)` / `@Max(n)` | Zahl ≥ n / ≤ n |
| `@Size(min, max)` | Länge von String/Collection |
| `@Email` | Gültiges E-Mail-Format |
| `@Pattern(regex)` | Matches Regex |

---

# Exception Handling

```java
// Eigene Exception definieren
public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(Long id) {
        super("Route not found: " + id);
    }
}

// Im Controller werfen
@GetMapping("/{id}")
public Route findById(@PathVariable Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new RouteNotFoundException(id));
}
```

Wie wird die Exception zum HTTP-Response?

---

# Globales Error Handling

<!--
Warum global? Einheitliches Fehlerformat für alle Endpoints, DRY – nicht in jedem Controller.
@RestControllerAdvice gilt für ALLE Controller in der Anwendung.
Typische Handler: RouteNotFoundException → 404, MethodArgumentNotValidException → 400.
-->

```java
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

`@RestControllerAdvice` = Globaler Exception Handler für alle Controller

---

# RFC 7807: Problem Details

<!--
RFC 7807 definiert Standard-Format für Fehler-Responses in HTTP APIs.
Spring Boot 3+ unterstützt das nativ mit der ProblemDetail-Klasse.
Vorteile: Clients können Fehler maschinenlesbar verarbeiten, kein proprietäres Format.
-->

**Standard-Format für Fehler-Responses:**

```json
{
  "type": "about:blank",
  "title": "Route Not Found",
  "status": 404,
  "detail": "Route not found: 42",
  "instance": "/routes/42"
}
```

- Einheitliches Format für alle Fehler
- Maschinenlesbar & menschenlesbar
- Spring Boot 3+ unterstützt `ProblemDetail` nativ

<div class="footnote">
<ol><li><a href="https://www.rfc-editor.org/rfc/rfc7807">RFC 7807: Problem Details for HTTP APIs</a></li></ol>
</div>

---

# @WebMvcTest: Sliced Testing

<!--
Wie @DataJpaTest nur JPA-Layer lädt, lädt @WebMvcTest nur Web-Layer.
Was wird geladen? Controller, @ControllerAdvice, Filter, WebMvcConfigurer.
Was wird NICHT geladen? Services, Repositories, andere Beans – müssen gemockt werden.
@MockitoBean (seit 3.4) ersetzt @MockBean – funktioniert gleich.
-->

```java
@WebMvcTest(RouteController.class)
class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RouteRepository repository;

    @Test
    void shouldReturnAllRoutes() throws Exception {
        when(repository.findAll()).thenReturn(List.of(...));

        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
```


---

# @WebMvcTest vs @SpringBootTest

| @WebMvcTest | @SpringBootTest |
|-------------|-----------------|
| Lädt nur Web-Layer | Lädt alles |
| MockMvc für HTTP-Tests | Echter Server möglich |
| Dependencies mocken | Echte Dependencies |
| Schnell (~2-3s) | Langsam (~5-10s) |

**Regel:**
→ Controller-Tests: `@WebMvcTest`
→ End-to-End-Tests: `@SpringBootTest`

---

# Zusammenfassung

<div class="grid-3col">

<div class="card">

#### REST & HTTP

- HTTP-Verben & Status Codes
- `@RestController`
- `@GetMapping`, `@PostMapping`

</div>

<div class="card">

#### DTOs & Validierung

- DTOs für API-Entkopplung
- `@Valid` + Bean Validation
- `@NotBlank`, `@Min`, ...

</div>

<div class="card">

#### Error Handling

- `@RestControllerAdvice`
- `ProblemDetail` (RFC 7807)
- `@WebMvcTest`

</div>

</div>

---

# Live-Coding

## Route-API

<!--
Demo: RouteController mit GET all, GET by id, POST, DELETE.
DTOs als Records für Request und Response.
Manuelle Konvertierung zwischen Entity und DTO.
Test mit @WebMvcTest und MockMvc – Repository wird gemockt.
-->

REST-Controller mit DTOs implementieren und mit `@WebMvcTest` testen

---

<!-- _class: chapter -->

# Wrap-Up

## Was haben wir gelernt? Wie geht es weiter?

---

<!-- header: "Wrap-Up" -->

# Was haben wir gebaut & gelernt?

<!--
Architektur-Überblick: Controller → Service → Repository → Database.
Spring Boot verbindet alles: Auto-Configuration, Dependency Injection, Testing-Support.
Testpyramide nochmal erwähnen: Unit Tests, Sliced Tests, Integration Tests.
-->

```
┌───────────────────────────────────────────────────────────────────────────┐
│                           Flight Service                                   │
├───────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐      ┌─────────────┐      ┌─────────────┐                │
│  │  Controller │  →   │   Service   │  →   │ Repository  │  →  Database   │
│  │  (REST API) │      │    (DI)     │      │   (JPA)     │                │
│  └─────────────┘      └─────────────┘      └─────────────┘                │
├───────────────────────────────────────────────────────────────────────────┤
│  @WebMvcTest            @SpringBootTest          @DataJpaTest             │
└───────────────────────────────────────────────────────────────────────────┘
```

**Kernkonzepte:** Dependency Injection, Auto-Configuration, Externalisierte Config, Sliced Testing

---

# Die Spring Boot Philosophie

<div class="grid-2col">

<div class="hero-card">

#### Convention over Configuration

- Sinnvolle Defaults
- Wenig Boilerplate
- Fokus auf Business-Logik

</div>

<div class="hero-card">

#### Opinionated Defaults

- Best Practices eingebaut
- Kuratierte Dependencies
- Bewährte Patterns

</div>

<div class="hero-card">

#### Production-Ready

- Embedded Server
- Health Checks & Metrics
- Einfaches Deployment

</div>

<div class="hero-card">

#### Test-First Mindset

- Testing-Support integriert
- Sliced Tests für Performance
- Mocking out-of-the-box

</div>

</div>

---

# Was fehlt für Produktion?

<!--
Ehrlich sein – das ist eine Einführung!
Jedes dieser Themen verdient einen eigenen Workshop.
Ihr habt jetzt das Fundament!
-->

| Thema | Warum wichtig? |
|-------|----------------|
| **Security** | Authentifizierung, Autorisierung |
| **Echte Datenbank** | PostgreSQL, MySQL statt H2 |
| **Docker** | Containerisierung für Deployment |
| **Monitoring** | Actuator, Metrics, Health Checks |
| **API-Dokumentation** | OpenAPI/Swagger |

---

# Ressourcen zum Weiterlernen

<!--
Übung vorschlagen: "Erweitert den Flight-Service! Fügt Flights hinzu, verknüpft mit Routes.
Implementiert Buchungen. Das ist der beste Weg zu lernen."
-->

<div class="grid-3col">

<div class="feature-box">

#### Dokumentation

- [spring.io/guides](https://spring.io/guides)
- [docs.spring.io](https://docs.spring.io)
- [Baeldung.com](https://baeldung.com)

<div class="use-case">→ Offizielle Tutorials & Referenz</div>

</div>

<div class="feature-box">

#### Bücher

- "Spring Boot in Action"
- "Spring in Action"
- Autor: Craig Walls

<div class="use-case">→ Tiefgehendes Wissen</div>

</div>

<div class="feature-box">

#### Praxis

- Flight-Service erweitern
- Neue Entities hinzufügen
- Eigene Projekte starten

<div class="use-case">→ Learning by Doing</div>

</div>

</div>

---

<!-- _class: lead -->

# Vielen Dank!

<!--
Zeit für Fragen lassen!
Positiv abschließen: "Viel Erfolg mit Spring Boot!"
-->
