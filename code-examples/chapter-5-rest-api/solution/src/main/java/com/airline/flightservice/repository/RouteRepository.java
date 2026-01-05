package com.airline.flightservice.repository;

import com.airline.flightservice.model.Route;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RouteRepository extends ListCrudRepository<Route, Long> {

    List<Route> findByOrigin(String origin);
}
