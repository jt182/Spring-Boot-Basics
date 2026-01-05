package com.airline.flightservice.service;

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
        String defaultRoute = routeService.getDefaultRoute();

        assertThat(defaultRoute).isNotNull();
    }
}
