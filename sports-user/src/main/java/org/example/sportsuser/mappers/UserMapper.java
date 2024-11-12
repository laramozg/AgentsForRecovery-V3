package org.example.sportsuser.mappers;

import org.example.sportsuser.controllers.user.dto.UserDto;
import org.example.sportsuser.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto convert(User user) {
        return new UserDto(
                user.getUsername(),
                user.getTelegram(),
                user.getNick(),
                user.isConfirmed(),
                user.isConfirmedUsername(),
                user.isBlocked(),
                user.getRole().name()
        );
    }
}
