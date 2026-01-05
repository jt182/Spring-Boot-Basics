package com.airline.flightservice.exception;

/**
 * Exception thrown when a route is not found.
 */
public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(Long id) {
        super("Route not found: " + id);
    }
}
