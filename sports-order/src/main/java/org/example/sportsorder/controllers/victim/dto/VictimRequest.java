package org.example.sportsorder.controllers.victim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Создание жертвы")
public record VictimRequest(
        @Schema(description = "Имя")
        @NotBlank String firstname,
        @Schema(description = "Фамилия")
        @NotBlank String lastname,
        @Schema(description = "Место работы")
        String workplace,
        @Schema(description = "Должность")
        String position,
        @Schema(description = "Место жительства")
        String residence,
        @Schema(description = "Телефон")
        String phone,
        @Schema(description = "Описание")
        String description) {
}
