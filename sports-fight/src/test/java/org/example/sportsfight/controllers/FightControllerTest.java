package org.example.sportsfight.controllers;

import org.example.sportsfight.BaseIntegrationTest;
import org.example.sportsfight.controllers.fight.dto.FightDto;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.models.enums.FightStatus;
import org.example.sportsfight.repositories.FightRepository;
import org.example.sportsfight.repositories.PerformerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfight.utils.Models.FIGHT_REQUEST;
import static org.example.sportsfight.utils.Models.PERFORMER;
import static org.springframework.http.MediaType.APPLICATION_JSON;

class FightControllerTest extends BaseIntegrationTest {

    @Autowired
    private FightRepository fightRepository;

    @Autowired
    private PerformerRepository performerRepository;

    private Fight fight;
    private Performer performer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        performer = performerRepository.save(PERFORMER());
    }

    @AfterEach
    void tearDown() {
        fightRepository.deleteAll();
        performerRepository.deleteAll();
    }
    @Test
    void createFightShouldReturnCreated() {
        client.post()
                .uri("/fights")
                .contentType(APPLICATION_JSON)
                .bodyValue(FIGHT_REQUEST())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void findFightShouldReturnOk() {
        fight = fightRepository.save(Fight.builder().performer(performer).orderId(UUID.randomUUID()).status(FightStatus.PENDING).build());
        client.get()
                .uri("/fights/" + fight.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectAll(
                        exchange -> exchange.expectStatus().isOk(),
                        exchange -> exchange.expectBody(FightDto.class).value(dto -> {
                            assertThat(dto).isNotNull();
                        })
                );
    }


    @Test
    void updateFightStatusShouldReturnOk() {
        fight = fightRepository.save(Fight.builder().performer(performer).orderId(UUID.randomUUID()).status(FightStatus.PENDING).build());
        client.put()
                .uri(uriBuilder -> uriBuilder.path("/fights/111111/DONE")
                        .build(fight.getId()))
                .contentType(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
