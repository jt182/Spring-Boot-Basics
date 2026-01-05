package com.airline.flightservice.controller;

import com.airline.flightservice.dto.RouteMapper;
import com.airline.flightservice.dto.RouteRequestDto;
import com.airline.flightservice.dto.RouteResponseDto;
import com.airline.flightservice.exception.RouteNotFoundException;
import com.airline.flightservice.model.Route;
import com.airline.flightservice.service.RouteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RouteService routeService;

    @MockitoBean
    private RouteMapper routeMapper;

    @Test
    void shouldReturnAllRoutes() throws Exception {
        var route1 = new Route("Frankfurt", "Berlin");
        route1.setId(1L);
        var route2 = new Route("Munich", "Hamburg");
        route2.setId(2L);

        var dto1 = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);
        var dto2 = new RouteResponseDto(2L, "Munich", "Hamburg", null);

        when(routeService.findAll()).thenReturn(List.of(route1, route2));
        when(routeMapper.toResponseDtoList(List.of(route1, route2))).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].origin").value("Frankfurt"))
                .andExpect(jsonPath("$[1].origin").value("Munich"));
    }

    @Test
    void shouldReturnRouteById() throws Exception {
        var route = new Route("Frankfurt", "Berlin");
        route.setId(1L);
        var dto = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);

        when(routeService.findById(1L)).thenReturn(route);
        when(routeMapper.toResponseDto(route)).thenReturn(dto);

        mockMvc.perform(get("/routes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Frankfurt"))
                .andExpect(jsonPath("$.destination").value("Berlin"));
    }

    @Test
    void shouldReturn404WhenRouteNotFound() throws Exception {
        when(routeService.findById(999L)).thenThrow(new RouteNotFoundException(999L));

        mockMvc.perform(get("/routes/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Route Not Found"));
    }

    @Test
    void shouldCreateRoute() throws Exception {
        var route = new Route("Frankfurt", "Berlin");
        var saved = new Route("Frankfurt", "Berlin");
        saved.setId(1L);
        var responseDto = new RouteResponseDto(1L, "Frankfurt", "Berlin", null);

        when(routeMapper.toEntity(any(RouteRequestDto.class))).thenReturn(route);
        when(routeService.save(route)).thenReturn(saved);
        when(routeMapper.toResponseDto(saved)).thenReturn(responseDto);

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "origin": "Frankfurt",
                                    "destination": "Berlin"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/routes/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.origin").value("Frankfurt"));
    }

    @Test
    void shouldReturn400WhenValidationFails() throws Exception {
        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "origin": "",
                                    "destination": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteRoute() throws Exception {
        mockMvc.perform(delete("/routes/1"))
                .andExpect(status().isNoContent());

        verify(routeService).deleteById(1L);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentRoute() throws Exception {
        doThrow(new RouteNotFoundException(999L)).when(routeService).deleteById(999L);

        mockMvc.perform(delete("/routes/999"))
                .andExpect(status().isNotFound());
    }
}
