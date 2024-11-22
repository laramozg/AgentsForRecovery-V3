package org.example.sportsuser.controllers.auth.dto;

import org.example.sportsuser.models.enums.Role;

import java.util.List;
import java.util.UUID;

public record AuthorizationDetails(UUID id,
                                   String username,
                                   List<Role> roles) {
}
