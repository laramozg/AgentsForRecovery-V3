package org.example.sportsuser.controllers.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Пользовательские данные")
public record AuthorizeUserResponse(
        @Schema(description = "Id пользователя")
        UUID id,
        @Schema(description = "Логин пользователя")
        String username,
        @Schema(description = "Access токен")
        String access,
        @Schema(description = "Refresh токен")
        String refresh
) {
}
