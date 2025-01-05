package org.example.sportsuser.controllers.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Регистрация пользователя")
public record RegisterUserRequest(
        @Schema(description = "Логин/Почта")
        @Size(min = 6, max = 30)
        String username,
        @Schema(description = "Пароль")
        @Size(min = 6, max = 100)
        String password,
        @Schema(description = "Ник")
        @NotBlank String nick,
        @Schema(description = "Телеграм")
        @NotBlank String telegram,
        @Schema(description = "Роль")
        @NotBlank String role
) {
}
