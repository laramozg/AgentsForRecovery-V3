package org.example.sportsorder.services;

import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.City;
import org.example.sportsorder.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.example.sportsorder.utils.Models.CITY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;
    private final City city = CITY;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldFindCityById() {
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        City foundCity = cityService.find(city.getId());

        assertNotNull(foundCity);
        assertEquals(city.getId(), foundCity.getId());
    }

    @Test
    void shouldThrowWhenCityNotFound() {
        UUID id = UUID.randomUUID();
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InternalException.class, () -> cityService.find(id));
    }

    @Test
    void shouldCreateCity() {
        when(cityRepository.save(any(City.class))).thenReturn(city);

        UUID createdId = cityService.createCity(city);

        assertEquals(city.getId(), createdId);
    }

    @Test
    void shouldDeleteCity() {
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        cityService.deleteCity(city.getId());
    }
}
