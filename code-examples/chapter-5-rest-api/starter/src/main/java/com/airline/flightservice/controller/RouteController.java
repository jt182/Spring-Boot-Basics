package com.airline.flightservice.controller;

import com.airline.flightservice.dto.RouteMapper;
import com.airline.flightservice.dto.RouteRequestDto;
import com.airline.flightservice.dto.RouteResponseDto;
import com.airline.flightservice.service.RouteService;

import java.util.List;

public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    public RouteController(RouteService routeService, RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    public List<RouteResponseDto> findAll() {
        return routeMapper.toResponseDtoList(routeService.findAll());
    }

    public RouteResponseDto findById(Long id) {
        return null;
    }

    public RouteResponseDto create(RouteRequestDto routeRequest) {
        return null;
    }

    public void delete(Long id) {

    }
}
