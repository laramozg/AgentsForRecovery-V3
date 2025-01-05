package org.example.sportsfight.controllers.performer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Создание исполнителя")
public record PerformerRequest (
        @Schema(description = "Серия и номер паспорта")
        @NotBlank String passportSeriesNumber,
        @Schema(description = "Вес")
        @NotNull Double weight,
        @Schema(description = "Рост")
        @NotNull Double height){
}
