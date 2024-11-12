package org.example.sportsuser.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsuser.configurations.security.AuthenticationProvider;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserResponse;
import org.example.sportsuser.controllers.auth.dto.RegisterUserRequest;
import org.example.sportsuser.models.User;
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
                return Mono.error(new InterruptedException("User already exists"));
            }
            user.setPassword(encoder.encode(user.getPassword()));
            return userService.create(user)
                    .map(User::getUsername);
        });
    }

    public Mono<AuthorizeUserResponse> authorize(User authUser) {
        return userService.find(authUser.getUsername()).flatMap(user -> {
            String encodedAuthUserPassword = encoder.encode(user.getPassword());
            if (!encoder.matches(user.getPassword(), encodedAuthUserPassword)) {
                return Mono.error(
                        new InterruptedException("Неверный логин или пароль")
                );
            }
            return createAuthorizeUserResponse(user);
        });
    }

    public Mono<AuthorizeUserResponse> reAuthorize(String refreshToken) throws InterruptedException {
        if (!authProvider.isValid(refreshToken)) {
            throw new InterruptedException("Токен не валидный");
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
