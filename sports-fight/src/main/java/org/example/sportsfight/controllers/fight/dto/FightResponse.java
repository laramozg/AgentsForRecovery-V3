package org.example.sportsfight.controllers.fight.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record FightResponse(
        @Schema(description = "ID")
        UUID id) {
}
