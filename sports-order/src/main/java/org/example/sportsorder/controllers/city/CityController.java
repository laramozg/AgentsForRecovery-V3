package org.example.sportsorder.controllers.city;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Создание города")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Город создан", content = @Content(schema = @Schema(implementation = CityResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'CUSTOMER')")
    public CityResponse create(@Valid @RequestBody CityRequest cityRequest) {
        logger.trace("Create city: {}", cityRequest);
        return new CityResponse(cityService.createCity(cityMapper.convertToEntity(cityRequest)));
    }

    @Operation(summary = "Обновление города")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Город создан", content = @Content(schema = @Schema(implementation = CityDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public CityDto update(@PathVariable UUID id,
                          @Valid @RequestBody CityRequest cityRequest) {
        logger.trace("Update city: {}", cityRequest);
        return cityMapper.convertToDto(cityService.updateCity(id, cityMapper.convertToEntity(cityRequest)));
    }

    @Operation(summary = "Удаление города")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Город удален", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void delete(@PathVariable UUID id) {
        logger.trace("Delete city: {}", id);
        cityService.deleteCity(id);
    }

    @Operation(summary = "Поиск городов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Города найдены", content = @Content(schema = @Schema(implementation = CityDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CityDto> findAllCities(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find all cities: {}", pageable);
        return cityService.findAllCities(pageable).map(cityMapper::convertToDto);
    }

}
