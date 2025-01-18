package org.example.sportsorder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sportsorder.BaseIntegrationTest;
import org.example.sportsorder.controllers.city.dto.CityRequest;
import org.example.sportsorder.models.City;
import org.example.sportsorder.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.example.sportsorder.utils.Models.CITY;
import static org.example.sportsorder.utils.Models.CITYREQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CityControllerTest extends BaseIntegrationTest {
    @Autowired
    private CityRepository cityRepository;

    private City city;

    @Test
    void createCityShouldReturnCreated() throws Exception {
        CityRequest cityRequest = CITYREQUEST;

        mockMvc.perform(post("/cities")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cityRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateCityShouldReturnUpdated() throws Exception {
        city = cityRepository.save(CITY);
        CityRequest updateRequest = CITYREQUEST;

        mockMvc.perform(put("/cities/" + city.getId())
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(CITY.getName()));
    }

    @Test
    void deleteCityShouldReturnNoContent() throws Exception {
        city = cityRepository.save(CITY);
        mockMvc.perform(delete("/cities/" + city.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findAllCitiesShouldReturnPagedResults() throws Exception {
        city = cityRepository.save(CITY);
        mockMvc.perform(get("/cities?page=0&size=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(CITY.getName()));
    }
}
