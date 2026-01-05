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
    void shouldUseConfiguredDefaultRoute() {
        var defaultRoute = routeService.getDefaultRoute();

        assertThat(defaultRoute).isEqualTo("Frankfurt -> Berlin");
    }

    @Test
    void shouldIncludeDefaultRouteInAllRoutes() {
        var routes = routeService.findAll();

        assertThat(routes)
                .extracting(Route::toString)
                .contains("Frankfurt -> Berlin");
    }

    @Test
    void shouldReturnAllRoutes() {
        var routes = routeService.findAll();

        assertThat(routes).hasSize(3);
    }
}
