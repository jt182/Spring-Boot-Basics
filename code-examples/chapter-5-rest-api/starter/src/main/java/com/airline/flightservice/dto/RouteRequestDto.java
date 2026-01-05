package com.airline.flightservice.dto;

public class RouteRequestDto {

    private String origin;
    private String destination;
    private Integer distanceKm;

    public RouteRequestDto() {
    }

    public RouteRequestDto(String origin, String destination, Integer distanceKm) {
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
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

    public Integer getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Integer distanceKm) {
        this.distanceKm = distanceKm;
    }
}
