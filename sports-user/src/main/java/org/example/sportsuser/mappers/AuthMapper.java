package org.example.sportsuser.mappers;

import org.example.sportsuser.controllers.auth.dto.AuthorizeUserRequest;
import org.example.sportsuser.controllers.auth.dto.RegisterUserRequest;
import org.example.sportsuser.models.User;
import org.example.sportsuser.models.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User convert(RegisterUserRequest request) {
        return User.builder()
                .username(request.username())
                .password(request.password())
                .nick(request.nick())
                .telegram(request.telegram())
                .role(Role.valueOf(request.role()))
                .build();
    }

    public User convert(AuthorizeUserRequest request) {
        return User.builder()
                .username(request.username())
                .password(request.password())
                .build();
    }
}