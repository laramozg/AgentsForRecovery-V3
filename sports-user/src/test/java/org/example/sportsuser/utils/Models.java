package org.example.sportsuser.utils;

import org.example.sportsuser.models.User;
import org.example.sportsuser.models.enums.Role;

import java.util.UUID;

public class Models {

    public static final String USER_PASSWORD = "password112";
    public static final String ENCODED_USER_PASSWORD = "$2a$10$fsRVtIIhXIStL94e3UJA7uAonxOIb.Pd2a4dr9Uq0tw0YKqZubHIm";

    public static User USER = User.builder()
            .id(UUID.fromString("37790e1b-2d12-4e3e-b222-522e81e90205"))
            .username("john.doe@mail.ru")
            .password(ENCODED_USER_PASSWORD)
            .nick("user1")
            .telegram("@telegram")
            .confirmed(true)
            .confirmedUsername(true)
            .blocked(false)
            .role(Role.SUPERVISOR).build();

    public static User UNAUTHORIZED_USER = User.builder()
            .id(UUID.randomUUID())
            .username("johan.do@mail.ru")
            .password("12345678")
            .nick("user")
            .telegram("@telegram")
            .confirmed(false)
            .confirmedUsername(false)
            .blocked(true)
            .role(Role.CUSTOMER).build();

}
