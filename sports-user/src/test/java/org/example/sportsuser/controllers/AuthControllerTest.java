package org.example.sportsuser.controllers;

import org.example.sportsuser.BaseIntegrationTest;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserRequest;
import org.example.sportsuser.controllers.auth.dto.RegisterUserRequest;
import org.example.sportsuser.controllers.auth.dto.RegisterUserResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.example.sportsuser.utils.Models.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class AuthControllerTest extends BaseIntegrationTest {


    @Test
    void registerShouldReturnCreated() {
        client.post()
                .uri("/auth")
                .contentType(APPLICATION_JSON)
                .bodyValue(new RegisterUserRequest(
                        UNAUTHORIZED_USER.getUsername(),
                        UNAUTHORIZED_USER.getPassword(),
                        UNAUTHORIZED_USER.getNick(),
                        UNAUTHORIZED_USER.getTelegram(),
                        UNAUTHORIZED_USER.getRole().name()
                ))
                .exchange()
                .expectAll(
                        result -> result.expectStatus().isCreated(),
                        result -> result.expectBody(RegisterUserResponse.class)
                );
    }

    @Test
    void authorizeShouldReturnCreated() {
        client.post()
                .uri("/auth/tokens")
                .contentType(APPLICATION_JSON)
                .bodyValue(new AuthorizeUserRequest(
                        USER.getUsername(),
                        USER_PASSWORD
                ))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void reAuthorizeShouldReturnBadRequest() {
        client.post()
                .uri("/auth/tokens/refresh?refresh=refresh")
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}