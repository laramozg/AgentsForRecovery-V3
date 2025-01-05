package org.example.sportsorder.controllers.city.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Создание города")
public record CityRequest(
        @Schema(description = "Название")
        @NotBlank String name,
        @Schema(description = "Регион")
        @NotBlank String region) {
}
