package org.example.sportsorder.controllers.order.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record OrderDeadlineResponse(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "Дедлайн")
        LocalDate deadline
) {
}
