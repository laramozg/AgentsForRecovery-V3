package org.example.sportsorder.util;

import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityContext {

    private SecurityContext() {
    }

    public static UUID getAuthorizedUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
        return getId(authentication);
    }

    public static String getAuthorizedUserUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
        return getUsername(authentication);
    }

    private static UUID getId(Authentication authentication) {
        try {
            return UUID.fromString((String) authentication.getPrincipal());
        } catch (ClassCastException | IllegalArgumentException e) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
    }

    private static String getUsername(Authentication authentication) {
        try {
            return (String) authentication.getCredentials();
        } catch (ClassCastException e) {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}