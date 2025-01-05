package org.example.sportsorder.controllers.mutilation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Создание ущерба")
public record MutilationRequest(
        @Schema(description = "Тип")
        @NotBlank String type,
        @Schema(description = "Стоимость")
        @NotNull Long price) {
}
