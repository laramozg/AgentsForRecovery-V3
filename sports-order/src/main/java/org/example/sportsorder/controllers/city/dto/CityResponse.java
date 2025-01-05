package org.example.sportsorder.controllers.city.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record CityResponse(
        @Schema(description = "ID города")
        UUID id) {
}
