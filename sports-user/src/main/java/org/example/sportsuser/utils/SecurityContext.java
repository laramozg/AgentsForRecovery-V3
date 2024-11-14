package org.example.sportsuser.utils;

import org.example.sportsuser.controllers.auth.dto.AuthorizationDetails;
import org.example.sportsuser.models.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SecurityContext {
    public static Mono<AuthorizationDetails> getAuthorizationDetails() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication authentication = context.getAuthentication();
                    String username = (String) authentication.getPrincipal();
                    String role = authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .findFirst()
                            .orElse("");
                    return new AuthorizationDetails(username, Role.valueOf(role));
                });
    }



}
