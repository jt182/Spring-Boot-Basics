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
