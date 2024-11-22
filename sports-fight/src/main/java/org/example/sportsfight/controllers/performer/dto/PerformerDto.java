package org.example.sportsfight.controllers.performer.dto;

import java.util.UUID;

public record PerformerDto(UUID id,
                           String userId,
                           String passportSeriesNumber,
                           Double weight,
                           Double height,
                           Double rating,
                           Integer completedOrders
                           ) {
}
