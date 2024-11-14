package org.example.sportsuser.utils;

import org.example.sportsuser.models.User;
import org.example.sportsuser.models.enums.Role;

import java.util.UUID;

public class Models {

    public static final String USER_PASSWORD = "12345";
    public static final String ENCODED_USER_PASSWORD = "$2a$10$.IEUyyxTZjIGYnDHOcFW3e8AD5QFAKWj7nu7NM1NfBs.wE6AtC83a";

    public static User USER = User.builder()
            .username("loginuser")
            .password(ENCODED_USER_PASSWORD)
            .nick("user1")
            .telegram("@telegram")
            .confirmed(true)
            .confirmedUsername(true)
            .blocked(false)
            .role(Role.SUPERVISOR).build();

    public static User UNAUTHORIZED_USER = User.builder()
            .username("loginuser2")
            .password("12345678")
            .nick("user")
            .telegram("@telegram")
            .confirmed(false)
            .confirmedUsername(false)
            .blocked(true)
            .role(Role.CUSTOMER).build();

}
