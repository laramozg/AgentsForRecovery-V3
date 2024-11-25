package org.example.sportsorder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.sportsorder.BaseIntegrationTest;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.models.City;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.Victim;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.repositories.CityRepository;
import org.example.sportsorder.repositories.MutilationRepository;
import org.example.sportsorder.repositories.OrderRepository;
import org.example.sportsorder.repositories.VictimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.example.sportsorder.utils.Models.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseIntegrationTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MutilationRepository mutationRepository;

    @Autowired
    private VictimRepository victimRepository;

    private Order order;
    private City city;
    private Victim victim;
    private Mutilation mutilation;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        city = cityRepository.save(CITY);
        mutilation = mutationRepository.save(MUTILATION);
        victim = victimRepository.save(VICTIM);
        order = orderRepository.save(Order.builder()
                .userId(UUID.randomUUID())
                .deadline(LocalDate.now())
                .status(OrderStatus.WAITING)
                .city(city)
                .victim(victim).build());
    }

    @Test
    void createOrderShouldReturnCreated() throws Exception {
        OrderRequest orderRequest = new OrderRequest(city.getId(),victim.getId(),
                LocalDate.now(), List.of(mutilation.getId()));

        mockMvc.perform(post("/orders")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    void deleteOrderShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/orders/" + order.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateOrderStatusShouldReturnUpdatedOrder() throws Exception {
        mockMvc.perform(post("/orders/" + order.getId() + "/" + "DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }
}