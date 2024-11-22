package org.example.sportsfight.configurations.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String USER_ROLES = "UserRoles";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String userId = exchange.getRequest().getHeaders().getFirst(USER_ID);
        String username = exchange.getRequest().getHeaders().getFirst(USERNAME);
        String userRoles = exchange.getRequest().getHeaders().getFirst(USER_ROLES);

        if (userId != null && username != null && userRoles != null) {
            List<String> roles = null;
            try {
                roles = objectMapper.readValue(userRoles, new TypeReference<List<String>>() {
                });
            } catch (JsonProcessingException e) {
                logger.error("Failed to parse roles", e);
            }

            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, username, authorities);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authenticationToken));
        } else {
            return chain.filter(exchange);
        }
    }
}