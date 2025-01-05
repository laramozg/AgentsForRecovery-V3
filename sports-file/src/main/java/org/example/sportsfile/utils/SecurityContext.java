package org.example.sportsfile.utils;

import org.example.sportsfile.exceptions.ErrorCode;
import org.example.sportsfile.exceptions.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityContext {
    private SecurityContext() {
    }

    public static UUID getAuthorizedUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
        return UUID.fromString(authentication.getPrincipal().toString());
    }

    public static String getAuthorizedUserUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication.getCredentials() == null) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
        return authentication.getCredentials().toString();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
