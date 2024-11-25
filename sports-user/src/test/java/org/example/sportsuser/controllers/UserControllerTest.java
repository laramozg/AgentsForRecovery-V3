package org.example.sportsuser.controllers;

import org.example.sportsuser.BaseIntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.example.sportsuser.utils.Models.USER;

@Disabled
class UserControllerTest extends BaseIntegrationTest {

    @Test
    void findShouldReturnNotFound() {
        client.get()
                .uri("/users/" + USER.getId())
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void deleteShouldReturnNotFound() {
        client.delete()
                .uri("/users/" + USER.getId())
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    void blockShouldReturnNotFound() {
        client.patch()
                .uri("/users/" + USER.getId() + "/block")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void unblockShouldReturnNotFound() {
        client.delete()
                .uri("/users/" + USER.getId() + "/block")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void confirmShouldReturnNotFound() {
        client.patch()
                .uri("/users/" + USER.getId() + "/confirm")
                .exchange()
                .expectStatus().isNoContent();
    }
}