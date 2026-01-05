package com.airline.flightservice.repository;

import com.airline.flightservice.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepository repository;

    @Test
    void shouldSaveAndFindRoute() {
        var route = new Route("Frankfurt", "Berlin");

        var saved = repository.save(route);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void shouldFindRoutesByOrigin() {
        repository.save(new Route("Frankfurt", "Berlin"));
        repository.save(new Route("Frankfurt", "Munich"));
        repository.save(new Route("Hamburg", "Berlin"));

        var frankfurtRoutes = repository.findByOrigin("Frankfurt");

        assertThat(frankfurtRoutes).hasSize(2);
        assertThat(frankfurtRoutes)
                .extracting(Route::getOrigin)
                .containsOnly("Frankfurt");
    }

    @Test
    void shouldCheckIfRouteExists() {
        repository.save(new Route("Frankfurt", "Berlin"));

        var exists = repository.existsByOriginAndDestination("Frankfurt", "Berlin");
        var notExists = repository.existsByOriginAndDestination("Munich", "Hamburg");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    void shouldDeleteRoute() {
        var route = repository.save(new Route("Frankfurt", "Berlin"));

        repository.deleteById(route.getId());

        assertThat(repository.findAll()).isEmpty();
    }
}
