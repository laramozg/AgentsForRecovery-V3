package org.example.sportsuser.sevices;

import org.example.sportsuser.configurations.security.AuthenticationProvider;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserResponse;
import org.example.sportsuser.exceptions.ErrorCode;
import org.example.sportsuser.exceptions.InternalException;
import org.example.sportsuser.services.AuthService;
import org.example.sportsuser.services.UserService;
import org.example.sportsuser.utils.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.example.sportsuser.utils.Models.UNAUTHORIZED_USER;
import static org.example.sportsuser.utils.Models.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    private final UserService userService = mock(UserService.class);
    private final PasswordEncoder encoder = mock(PasswordEncoder.class);
    private final AuthenticationProvider authProvider = mock(AuthenticationProvider.class);

    private final AuthService authService = new AuthService(userService, encoder, authProvider);

    @BeforeEach
    void setUp() {
        SecurityContext.mockSecurityContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void registerShouldExecuteSuccessfully() {
        when(userService.isExists(UNAUTHORIZED_USER.getUsername())).thenReturn(Mono.just(false));
        when(encoder.encode(any())).thenReturn("passwordEncoded");
        when(userService.create(any())).thenReturn(Mono.just(UNAUTHORIZED_USER));

        StepVerifier.create(authService.register(UNAUTHORIZED_USER))
                .expectNext(UNAUTHORIZED_USER.getUsername())
                .expectComplete()
                .verify();
    }

    @Test
    void registerShouldThrowUserAlreadyExist() {
        when(userService.isExists(UNAUTHORIZED_USER.getUsername())).thenReturn(Mono.just(true));

        StepVerifier.create(authService.register(UNAUTHORIZED_USER))
                .expectError(InternalException.class)
                .verify();
    }

    @Test
    void authorizeShouldExecuteSuccessfully() {
        when(userService.find(USER.getUsername())).thenReturn(Mono.just(USER));
        when(encoder.matches(any(), any())).thenReturn(true);
        when(authProvider.createAccessToken(any(), any())).thenReturn("access");
        when(authProvider.createRefreshToken(any())).thenReturn("refresh");

        StepVerifier.create(authService.authorize(USER))
                .expectNext(new AuthorizeUserResponse(
                        USER.getUsername(),
                        "access",
                        "refresh"
                ))
                .expectComplete()
                .verify();
    }

    @Test
    void authorizeShouldThrowUserPasswordIncorrect() {
        when(userService.find(any(String.class))).thenReturn(Mono.just(USER));
        when(encoder.encode(any())).thenReturn("12345");
        when(encoder.matches(any(), any())).thenReturn(false);

        StepVerifier.create(authService.authorize(UNAUTHORIZED_USER))
                .expectError(InternalException.class)
                .verify();
    }

    @Test
    void reAuthorizeShouldExecuteSuccessfully() {
        when(authProvider.isValid(any())).thenReturn(true);
        when(authProvider.getUsernameFromToken(any())).thenReturn(UNAUTHORIZED_USER.getUsername());
        when(userService.find(UNAUTHORIZED_USER.getUsername())).thenReturn(Mono.just(UNAUTHORIZED_USER));
        when(authProvider.createAccessToken(any(), any())).thenReturn("access");
        when(authProvider.createRefreshToken(any())).thenReturn("refresh");

        StepVerifier.create(authService.reAuthorize("refresh"))
                .expectNext(new AuthorizeUserResponse(
                        UNAUTHORIZED_USER.getUsername(),
                        "access",
                        "refresh"
                ))
                .expectComplete()
                .verify();
    }

    @Test
    void reAuthorizeShouldThrowTokenExpired() {
        when(authProvider.isValid(any())).thenReturn(false);

        InternalException ex = catchThrowableOfType(
                () -> authService.reAuthorize("refresh"),
                InternalException.class
        );

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.TOKEN_EXPIRED);
    }
}
