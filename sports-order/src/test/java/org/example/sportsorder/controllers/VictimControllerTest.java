package org.example.sportsorder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sportsorder.BaseIntegrationTest;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.models.Victim;
import org.example.sportsorder.repositories.VictimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.sportsorder.utils.Models.VICTIM;
import static org.example.sportsorder.utils.Models.VICTIMREQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VictimControllerTest extends BaseIntegrationTest {
    @Autowired
    private VictimRepository victimRepository;

    private Victim victim;

    @BeforeEach
    void setUp() {
        victim = victimRepository.save(VICTIM);
    }

    @Test
    void createCityShouldReturnCreated() throws Exception {
        VictimRequest victimRequest = VICTIMREQUEST;

        mockMvc.perform(post("/victims")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(victimRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void findCityByIdShouldReturnResult() throws Exception {
        mockMvc.perform(get("/victims/" + victim.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(victim.getFirstName()));
    }

    @Test
    void deleteCityShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/victims/" + victim.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAllCitiesShouldReturnPagedResults() throws Exception {

        mockMvc.perform(get("/victims?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value(victim.getFirstName()));
    }
}
