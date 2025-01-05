package org.example.sportsfight.controllers.fight.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Бой")
public record FightDto(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "ID исполнителя")
        UUID performerId,
        @Schema(description = "ID заказа")
        UUID orderId,
        @Schema(description = "Статус")
        String status) {
}
