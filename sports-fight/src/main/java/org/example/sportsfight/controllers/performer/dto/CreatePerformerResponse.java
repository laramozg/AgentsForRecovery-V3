package org.example.sportsfight.controllers.performer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record CreatePerformerResponse(
        @Schema(description = "ID")
        UUID id) {
}
