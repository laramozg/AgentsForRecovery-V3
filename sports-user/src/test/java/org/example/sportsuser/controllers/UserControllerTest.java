package org.example.sportsuser.controllers;

import org.example.sportsuser.BaseIntegrationTest;
import org.example.sportsuser.controllers.user.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsuser.utils.Models.USER;


class UserControllerTest extends BaseIntegrationTest {

    @Test
    void findShouldReturnOk() {
        client.get()
                .uri("/users/" + USER.getId())
                .exchange()
                .expectAll(
                        result -> result.expectStatus().isOk(),
                        result -> result.expectBody(UserDto.class).value(user -> {
                            assertThat(user.username()).isEqualTo(USER.getUsername());
                        })
                );
    }

    @Test
    void deleteShouldReturnNoContent() {
        client.delete()
                .uri("/users/" + USER.getId())
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    void blockShouldReturnNoContent() {
        client.patch()
                .uri("/users/" + USER.getId() + "/block")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void unblockShouldReturnNoContent() {
        client.delete()
                .uri("/users/" + USER.getId() + "/block")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void confirmShouldReturnNoContent() {
        client.patch()
                .uri("/users/" + USER.getId() + "/confirm")
                .exchange()
                .expectStatus().isNoContent();
    }
}