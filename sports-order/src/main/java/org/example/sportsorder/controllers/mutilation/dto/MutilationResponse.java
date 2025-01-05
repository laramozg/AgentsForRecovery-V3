package org.example.sportsorder.controllers.mutilation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record MutilationResponse(
        @Schema(description = "ID")
        UUID id) {
}
