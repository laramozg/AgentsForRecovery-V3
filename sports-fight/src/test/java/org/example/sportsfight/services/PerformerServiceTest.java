package org.example.sportsfight.services;

import org.example.sportsfight.models.Performer;
import org.example.sportsfight.repositories.PerformerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfight.utils.Models.PERFORMER;
import static org.example.sportsfight.utils.Models.USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerformerServiceTest {

    @InjectMocks
    private PerformerService performerService;

    @Mock
    private PerformerRepository performerRepository;

    private final Performer performer = PERFORMER();


    @Test
    void testCreate() {
        when(performerRepository.save(any(Performer.class))).thenReturn(performer);

        Mono<UUID> performerMono = performerService.create(performer);

        UUID savedId = performerMono.block();
        assertThat(savedId).isEqualTo(performer.getId());
    }

    @Test
    void testFind() {
        when(performerRepository.findPerformerById(performer.getId())).thenReturn(performer);

        Mono<Performer> performerMono = performerService.find(performer.getId());

        Performer foundPerformer = performerMono.block();
        assertThat(foundPerformer).isNotNull();
        assertThat(foundPerformer.getId()).isEqualTo(performer.getId());
    }


    @Test
    void testUpdate() {
        when(performerRepository.findPerformerById(performer.getId())).thenReturn(performer);
        when(performerRepository.save(any(Performer.class))).thenReturn(performer);

        Performer updatedPerformer = Performer.builder()
                .username(USERNAME)
                .passportSeriesNumber("111111")
                .height(190.4)
                .weight(90.0)
                .build();

        Mono<Performer> performerMono = performerService.update(performer.getId(), updatedPerformer);

        Performer resultPerformer = performerMono.block();
        assertThat(resultPerformer).isNotNull();
        assertThat(resultPerformer.getCompletedOrders()).isZero();
    }

    @Test
    void testSave() {
        when(performerRepository.save(any(Performer.class))).thenReturn(performer);

        Mono<Performer> performerMono = performerService.save(performer);

        Performer savedPerformer = performerMono.block();
        assertThat(savedPerformer).isNotNull();
        assertThat(savedPerformer.getId()).isEqualTo(performer.getId());
    }
}