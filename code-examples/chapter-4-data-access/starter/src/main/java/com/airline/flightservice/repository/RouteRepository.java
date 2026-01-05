package com.airline.flightservice.repository;

import com.airline.flightservice.model.Route;

import java.util.List;

public class RouteRepository {

    public List<Route> findAll() {
        return List.of();
    }

    public Route save(Route route) {
        return route;
    }
}
