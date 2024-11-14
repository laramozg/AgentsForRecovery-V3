package org.example.sportsuser.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.util.Collections;

import static org.example.sportsuser.utils.Models.USER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContext {

    public static void mockSecurityContext() {
        org.springframework.security.core.context.SecurityContext securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        Authentication authentication = createMockAuthentication();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        TestSecurityContextHolder.setContext(securityContext);
    }

    public static Authentication createMockAuthentication() {
        UsernamePasswordAuthenticationToken auth = mock(UsernamePasswordAuthenticationToken.class);
        when(auth.getPrincipal()).thenReturn(USER.getUsername());
        when(auth.getAuthorities()).thenReturn(Collections.singletonList(new SimpleGrantedAuthority(USER.getRole().name())));
        return auth;
    }

    public static Authentication createAuthentication() {
        return new UsernamePasswordAuthenticationToken(
                USER.getUsername(),
                Collections.singletonList(new SimpleGrantedAuthority(USER.getRole().name()))
        );
    }
}
