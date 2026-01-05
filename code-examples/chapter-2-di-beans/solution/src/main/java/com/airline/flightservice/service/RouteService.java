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
