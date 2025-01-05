package org.example.sportsorder.controllers.city.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Город")
public record CityDto(
        @Schema(description = "ID города")
        UUID id,
        @Schema(description = "Название")
        String name,
        @Schema(description = "Регион")
        String region) {
}
