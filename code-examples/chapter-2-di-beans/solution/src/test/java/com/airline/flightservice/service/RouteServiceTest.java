package com.airline.flightservice.service;

import com.airline.flightservice.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Test
    void shouldReturnAllRoutes() {
        var routes = routeService.findAll();

        assertThat(routes).isNotNull();
        assertThat(routes).isNotEmpty();
        assertThat(routes).hasSize(3);
    }

    @Test
    void shouldContainFrankfurtBerlinRoute() {
        var routes = routeService.findAll();

        assertThat(routes)
                .extracting(Route::toString)
                .contains("Frankfurt -> Berlin");
    }

    @Test
    void shouldFindRouteById() {
        var route = routeService.findById(1L);

        assertThat(route).isPresent();
        assertThat(route.get().getOrigin()).isEqualTo("Frankfurt");
        assertThat(route.get().getDestination()).isEqualTo("Berlin");
    }

    @Test
    void shouldReturnEmptyForUnknownId() {
        var route = routeService.findById(999L);

        assertThat(route).isEmpty();
    }
}
