package org.example.sportsorder.util;

import lombok.extern.slf4j.Slf4j;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@Slf4j
public class SecurityContext {
    private SecurityContext() {}

    public static UUID getAuthorizedUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            return getId(authentication);
        } else {
            throw new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);
        }
    }

    private static UUID getId(Authentication authentication) {
        return UUID.fromString(authentication.getPrincipal().toString());
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}