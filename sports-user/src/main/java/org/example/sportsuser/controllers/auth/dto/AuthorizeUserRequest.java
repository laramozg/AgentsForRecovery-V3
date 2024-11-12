package org.example.sportsuser.controllers.auth.dto;

import jakarta.validation.constraints.Size;

public record AuthorizeUserRequest(
        @Size(min = 6, max = 30)
        String username,
        @Size(min = 6, max = 100)
        String password
) {
}
