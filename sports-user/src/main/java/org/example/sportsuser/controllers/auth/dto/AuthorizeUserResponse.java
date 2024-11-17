package org.example.sportsuser.controllers.auth.dto;

import java.util.UUID;

public record AuthorizeUserResponse(
        UUID id,
        String username,
        String access,
        String refresh
) {
}
