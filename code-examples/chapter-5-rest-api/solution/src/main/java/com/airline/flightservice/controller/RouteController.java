package com.airline.flightservice.controller;

import com.airline.flightservice.dto.RouteMapper;
import com.airline.flightservice.dto.RouteRequestDto;
import com.airline.flightservice.dto.RouteResponseDto;
import com.airline.flightservice.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteMapper routeMapper;

    public RouteController(RouteService routeService, RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @GetMapping
    public List<RouteResponseDto> findAll(@RequestParam(required = false) String origin) {
        if (origin != null) {
            return routeMapper.toResponseDtoList(routeService.findByOrigin(origin));
        }
        return routeMapper.toResponseDtoList(routeService.findAll());
    }

    @GetMapping("/{id}")
    public RouteResponseDto findById(@PathVariable Long id) {
        return routeMapper.toResponseDto(routeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RouteResponseDto> create(@Valid @RequestBody RouteRequestDto request) {
        var route = routeMapper.toEntity(request);
        var saved = routeService.save(route);
        var location = URI.create("/routes/" + saved.getId());
        return ResponseEntity.created(location).body(routeMapper.toResponseDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
