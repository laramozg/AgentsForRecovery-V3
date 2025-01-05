package org.example.sportsuser.controllers.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Авторизация пользователя")
public record AuthorizeUserRequest(
        @Schema(description = "Логин")
        @Size(min = 6, max = 30)
        String username,
        @Schema(description = "Пароль")
        @Size(min = 6, max = 100)
        String password
) {
}
