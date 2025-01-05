package org.example.sportsorder.controllers.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "Создание заказ")
public record OrderRequest(
        @Schema(description = "ID города")
        @NotNull UUID cityId,
        @Schema(description = "ID жертвы")
        @NotNull UUID victimId,
        @Schema(description = "Дедлайн")
        @NotNull LocalDate deadline,
        @Schema(description = "Список ущерба")
        List<UUID> mutilationIds) {
}
