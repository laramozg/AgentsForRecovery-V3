package org.example.sportsfight.controllers.fight.dto;

import java.util.UUID;

public record FightDto(UUID id,
                       UUID performerId,
                       UUID orderId,
                       String status) {
}
