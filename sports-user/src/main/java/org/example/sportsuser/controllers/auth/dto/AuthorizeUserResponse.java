package org.example.sportsuser.controllers.auth.dto;

public record AuthorizeUserResponse(
        String username,
        String access,
        String refresh
) {
}
