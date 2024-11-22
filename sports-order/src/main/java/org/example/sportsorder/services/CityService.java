package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.City;
import org.example.sportsorder.repositories.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityService {
    private final CityRepository cityRepository;
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    public City find(UUID id) {
        logger.info("Find city by id: {}", id);
        return cityRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.CITY_NOT_FOUND));
    }

    public UUID createCity(City city) {
        logger.info("Create city: {}", city);
        return cityRepository.save(city).getId();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public City updateCity(UUID id, City city) {
        logger.info("Update city: {}", city);
        City cityOld = find(id);
        city.setId(cityOld.getId());
        return cityRepository.save(city);
    }

    public void deleteCity(UUID id) {
        logger.info("Delete city: {}", id);
        cityRepository.deleteById(id);
    }

    public Page<City> findAllCities(Pageable pageable) {
        logger.info("Find all cities");
        return cityRepository.findAll(pageable);
    }
}
