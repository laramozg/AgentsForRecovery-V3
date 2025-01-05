package org.example.sportsorder.controllers.victim.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record VictimResponse(
        @Schema(description = "ID")
        UUID id) {
}
