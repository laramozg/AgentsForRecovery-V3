package org.example.sportsorder.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.sportsorder.utils.Models.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextMockUtil {
    public static void mockSecurityContext() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = createTestAuthentication();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private static Authentication createTestAuthentication() {
        UsernamePasswordAuthenticationToken auth = mock(UsernamePasswordAuthenticationToken.class);
        when(auth.getPrincipal()).thenReturn(USER_ID.toString());
        when(auth.getCredentials()).thenReturn(USERNAME);
        when(auth.getAuthorities()).thenReturn(
                Stream.of(ROLE_SUPERVISOR, ROLE_WORKER, ROLE_CUSTOMER)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        return auth;
    }
}
