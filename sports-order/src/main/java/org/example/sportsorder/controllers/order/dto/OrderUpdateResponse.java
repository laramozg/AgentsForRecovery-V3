package org.example.sportsorder.controllers.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record OrderUpdateResponse(
        @Schema(description = "ID")
        UUID id,
        @Schema(description = "Статус")
        String status) {
}
