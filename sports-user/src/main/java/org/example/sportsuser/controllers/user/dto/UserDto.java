package org.example.sportsuser.controllers.user.dto;

import java.util.UUID;

public record UserDto(UUID id,
                      String username,
                      String nick,
                      String telegram,
                      boolean confirmed,
                      boolean confirmed_username,
                      boolean blocked,
                      String role
                      ) {
}
