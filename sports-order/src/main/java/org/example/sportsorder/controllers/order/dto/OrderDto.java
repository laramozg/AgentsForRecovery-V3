package org.example.sportsorder.controllers.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "Заказ")
public record OrderDto(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "ID пользователя")
        UUID userId,
        @Schema(description = "ID города")
        UUID cityId,
        @Schema(description = "ID жертвы")
        UUID victimId,
        @Schema(description = "Дедлайн")
        LocalDate deadline,
        @Schema(description = "Статус")
        String state,
        @Schema(description = "Список ущерба")
        List<MutilationDto> mutilations) {
}
