package org.example.sportsfight.controllers.fight.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FightRequest(
        @NotNull UUID performerId,
        @NotNull UUID orderId) {
}
