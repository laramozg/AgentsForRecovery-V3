package org.example.sportsfight.configurations.security;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements WebFilter {

    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String USER_ROLE = "UserRole";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst(USER_ID);
        String username = exchange.getRequest().getHeaders().getFirst(USERNAME);
        String userRole = exchange.getRequest().getHeaders().getFirst(USER_ROLE);

        if (userId != null && username != null && userRole != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, username, List.of(authority));
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticationToken));
        } else {
            return chain.filter(exchange);
        }
    }
}