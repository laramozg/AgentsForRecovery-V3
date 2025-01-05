package org.example.sportsorder.controllers.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record OrderResponse(
        @Schema(description = "ID")
        UUID id) {
}
