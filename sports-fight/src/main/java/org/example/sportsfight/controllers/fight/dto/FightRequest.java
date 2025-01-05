package org.example.sportsfight.controllers.fight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Создание боя")
public record FightRequest(
        @Schema(description = "ID исполнителя")
        @NotNull UUID performerId,
        @Schema(description = "ID заказа")
        @NotNull UUID orderId) {
}
