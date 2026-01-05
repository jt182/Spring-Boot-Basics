package com.airline.flightservice.exception;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(Long id) {
        super("Route not found: " + id);
    }
}
