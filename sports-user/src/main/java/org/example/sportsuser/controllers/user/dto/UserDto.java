package org.example.sportsuser.controllers.user.dto;


public record UserDto(String username,
                      String nick,
                      String telegram,
                      boolean confirmed,
                      boolean confirmed_username,
                      boolean blocked,
                      String role
                      ) {
}
