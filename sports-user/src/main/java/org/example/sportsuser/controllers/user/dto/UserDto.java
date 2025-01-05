package org.example.sportsuser.controllers.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema
public record UserDto(
        @Schema(description = "Id пользователя")
        UUID id,
        @Schema(description = "Логин")
        String username,
        @Schema(description = "Ник")
        String nick,
        @Schema(description = "Телеграм")
        String telegram,
        @Schema(description = "Подтвержден")
        boolean confirmed,
        @Schema(description = "Подтверждена почта")
        boolean confirmed_username,
        @Schema(description = "Заблокирован")
        boolean blocked,
        @Schema(description = "Роль")
        String role
                      ) {
}
