package com.airline.flightservice.service;

import com.airline.flightservice.model.Route;
import com.airline.flightservice.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteService.class);

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
        log.info("RouteService initialized with RouteRepository");
    }

    public List<Route> findAll() {
        log.info("Finding all routes");
        return routeRepository.findAll();
    }

    public Optional<Route> findById(Long id) {
        log.debug("Finding route with id: {}", id);
        return routeRepository.findById(id);
    }

    public List<Route> findByOrigin(String origin) {
        log.debug("Finding routes by origin: {}", origin);
        return routeRepository.findByOrigin(origin);
    }

    public Route save(Route route) {
        log.info("Saving route: {}", route);
        return routeRepository.save(route);
    }

    public void deleteById(Long id) {
        log.info("Deleting route with id: {}", id);
        routeRepository.deleteById(id);
    }

    public boolean existsByOriginAndDestination(String origin, String destination) {
        return routeRepository.existsByOriginAndDestination(origin, destination);
    }
}
