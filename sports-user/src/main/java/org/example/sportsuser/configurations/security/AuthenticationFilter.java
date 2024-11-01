package org.example.sportsuser.configurations.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements WebFilter {

    private final AuthenticationProvider authenticationProvider;

    public AuthenticationFilter(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken = bearerToken.substring(BEARER_PREFIX_LENGTH);
        } else if (bearerToken != null) {
            return Mono.error(new RuntimeException("Invalid bearer token"));
                    //InternalException(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_DOES_NOT_EXIST));
        }

        if (bearerToken != null) {
            if (authenticationProvider.isValid(bearerToken)) {
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(
                                authenticationProvider.getAuthentication(bearerToken)
                        ));
            } else {
                return Mono.error(new RuntimeException("Invalid bearer token"));
                        //InternalException(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_EXPIRED));
            }
        } else {
            return chain.filter(exchange);
        }
    }

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();
}