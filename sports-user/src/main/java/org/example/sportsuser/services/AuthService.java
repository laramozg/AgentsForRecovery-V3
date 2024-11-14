package org.example.sportsuser.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsuser.configurations.security.AuthenticationProvider;
import org.example.sportsuser.controllers.auth.dto.AuthorizationDetails;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserResponse;
import org.example.sportsuser.exceptions.ErrorCode;
import org.example.sportsuser.exceptions.InternalException;
import org.example.sportsuser.models.User;
import org.example.sportsuser.models.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationProvider authProvider;


    @Transactional
    public Mono<String> register(User user) {
        return userService.isExists(user.getUsername()).flatMap(result -> {
            if (result) {
                return Mono.error(new InternalException(HttpStatus.BAD_REQUEST, ErrorCode.USER_ALREADY_EXIST));
            }
            user.setPassword(encoder.encode(user.getPassword()));
            return userService.create(user).mapNotNull(User::getUsername);
        });
    }

    public Mono<AuthorizationDetails> getAuthDetails() {
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

    public Mono<AuthorizeUserResponse> authorize(User authUser) {
        return userService.find(authUser.getUsername()).flatMap(user -> {
            String encodedAuthUserPassword = encoder.encode(authUser.getPassword());
            if (!encoder.matches(user.getPassword(), encodedAuthUserPassword)) {
                return Mono.error(
                        new InternalException(HttpStatus.UNAUTHORIZED, ErrorCode.USER_PASSWORD_INCORRECT));
            }
            return createAuthorizeUserResponse(user);
        });
    }

    public Mono<AuthorizeUserResponse> reAuthorize(String refreshToken){
        if (!authProvider.isValid(refreshToken)) {
            throw new InternalException(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_EXPIRED);
        }
        String username = authProvider.getUsernameFromToken(refreshToken);
        return userService.find(username).flatMap(this::createAuthorizeUserResponse);
    }

    private Mono<AuthorizeUserResponse> createAuthorizeUserResponse(User user) {
        return Mono.just(new AuthorizeUserResponse(
                user.getUsername(),
                authProvider.createAccessToken(user.getUsername(), user.getRole()),
                authProvider.createRefreshToken(user.getUsername())
        ));
    }
}
