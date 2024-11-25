package org.example.sportsfight.controllers;

import org.assertj.core.api.Assertions;
import org.example.sportsfight.BaseIntegrationTest;
import org.example.sportsfight.controllers.performer.dto.CreatePerformerResponse;
import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.repositories.PerformerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.example.sportsfight.utils.Models.PERFORMER;
import static org.example.sportsfight.utils.Models.PERFORMER_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class PerformerControllerTest extends BaseIntegrationTest {

    @Autowired
    private PerformerRepository performerRepository;

    private Performer performer;

    @AfterEach
    void tearDown() {
        performerRepository.deleteAll();
    }

    @Test
    void createPerformerShouldReturnCreated() {
        PerformerRequest performerRequest = new PerformerRequest("11111", 100.0, 199.0);
        client.post()
                .uri("/performers")
                .contentType(APPLICATION_JSON)
                .bodyValue(performerRequest)
                .exchange()
                .expectAll(
                        exchange -> exchange.expectStatus().isCreated(),
                        exchange -> exchange.expectBody(CreatePerformerResponse.class).value(res -> {
                            Assertions.assertThat(res.id()).isNotNull();
                        })
                );
    }

    @Test
    void findPerformerShouldReturnOk() {
        performer = performerRepository.save(PERFORMER());
        client.get()
                .uri("/performers/" + performer.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectAll(
                        exchange -> exchange.expectStatus().isOk(),
                        exchange -> exchange.expectBody(PerformerDto.class).value(dto -> {
                            Assertions.assertThat(dto).isNotNull();
                        })
                );
    }

    @Test
    void findPerformerByUserIdShouldReturnIsUnauthorized() {
        performer = performerRepository.save(PERFORMER());
        client.get()
                .uri("/performers")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void updatePerformerShouldReturnError() {
        performer = performerRepository.save(PERFORMER());
        client.put()
                .uri("/performers/" + performer.getId())
                .contentType(APPLICATION_JSON)
                .bodyValue(PERFORMER_REQUEST())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
