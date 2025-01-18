package org.example.sportsorder.controllers.victim.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Жертва")
public record VictimDto(
        @Schema(description = "ID")
        UUID uuid,
        @Schema(description = "Имя")
        String firstname,
        @Schema(description = "Фамилия")
        String lastname,
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
