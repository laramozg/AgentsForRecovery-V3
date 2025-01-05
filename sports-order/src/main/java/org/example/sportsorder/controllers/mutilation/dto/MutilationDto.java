package org.example.sportsorder.controllers.mutilation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Ущерб")
public record MutilationDto(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "Тип")
        String type,
        @Schema(description = "Стоимость")
        Long price) {
}
