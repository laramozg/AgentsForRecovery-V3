package org.example.sportsuser.controllers.auth.dto;

import org.example.sportsuser.models.enums.Role;

public record AuthorizationDetails(String username,
                                   Role role) {
}
