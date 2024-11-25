package org.example.sportsfight.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.example.sportsfight.utils.Models.*;

public class AuthenticationUtils {
    public static UsernamePasswordAuthenticationToken createAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                String.valueOf(USER_ID),
                USERNAME,
                USER_ROLES
        );
    }
}
