package org.example.sportsfight.util;

import org.example.sportsfight.exceptions.ErrorCode;
import org.example.sportsfight.exceptions.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class ReactiveSecurityContext {
    private ReactiveSecurityContext() {}
    private static final Logger logger = LoggerFactory.getLogger(ReactiveSecurityContext.class);

    public static Mono<String> getUsername() {
        return Mono.defer(() -> {
            logger.info("getUsername() called");
            return ReactiveSecurityContextHolder.getContext()
                    .map(SecurityContext::getAuthentication)
                    .switchIfEmpty(Mono.error(new InternalException(HttpStatus.UNAUTHORIZED, ErrorCode.FORBIDDEN)))
                    .filter(Authentication::isAuthenticated)
                    .switchIfEmpty(Mono.error(new InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN)))
                    .map(auth -> (String) auth.getCredentials());
        });
    }


}

