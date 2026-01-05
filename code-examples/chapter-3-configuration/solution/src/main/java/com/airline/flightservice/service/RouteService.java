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
