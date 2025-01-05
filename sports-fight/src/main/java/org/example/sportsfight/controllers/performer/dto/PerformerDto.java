package org.example.sportsfight.controllers.performer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Исполнитель")
public record PerformerDto(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "ID пользователя")
        String userId,
        @Schema(description = "Серия и номер паспорта")
        String passportSeriesNumber,
        @Schema(description = "Вес")
        Double weight,
        @Schema(description = "Рост")
        Double height,
        @Schema(description = "Рейтинг")
        Double rating,
        @Schema(description = "Кол-во выполненных заказов")
        Integer completedOrders
        ) {
}
