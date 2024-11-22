package org.example.sportsorder.controllers.city;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.city.dto.CityDto;
import org.example.sportsorder.controllers.city.dto.CityRequest;
import org.example.sportsorder.controllers.city.dto.CityResponse;
import org.example.sportsorder.mappers.CityMapper;
import org.example.sportsorder.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final CityService cityService;
    private final CityMapper cityMapper;
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'CUSTOMER')")
    public CityResponse create(@Valid @RequestBody CityRequest cityRequest) {
        logger.trace("Create city: {}", cityRequest);
        return new CityResponse(cityService.createCity(cityMapper.convertToEntity(cityRequest)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public CityDto update(@PathVariable UUID id,
                          @Valid @RequestBody CityRequest cityRequest) {
        logger.trace("Update city: {}", cityRequest);
        return cityMapper.convertToDto(cityService.updateCity(id, cityMapper.convertToEntity(cityRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void delete(@PathVariable UUID id) {
        logger.trace("Delete city: {}", id);
        cityService.deleteCity(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CityDto> findAllCities(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find all cities: {}", pageable);
        return cityService.findAllCities(pageable).map(cityMapper::convertToDto);
    }

}
