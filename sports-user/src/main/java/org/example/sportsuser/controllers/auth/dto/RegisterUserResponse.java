package org.example.sportsuser.controllers.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record RegisterUserResponse(
        @Schema(description = "Id нового пользователя")
        UUID id) {
}
