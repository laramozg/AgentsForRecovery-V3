package org.example.sportsfile.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.sportsfile.utils.Models.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextUtils {
    public static void mockSecurityContext() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = createTestAuthentication();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private static Authentication createTestAuthentication() {
        UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);
        when(authentication.getPrincipal()).thenReturn(USER_ID);
        when(authentication.getCredentials()).thenReturn(USERNAME);
        when(authentication.getAuthorities()).thenReturn(
                Stream.of(ROLE_SUPERVISOR, ROLE_CUSTOMER, ROLE_EXECUTOR)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        return authentication;
    }
}
