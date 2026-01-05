package com.airline.flightservice.model;

public class Route {

    private Long id;

    private String origin;

    private String destination;

    public Route() {
    }

    public Route(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return origin + " -> " + destination;
    }
}
