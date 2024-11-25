package org.example.sportsfight.services;

import org.example.sportsfight.exceptions.ErrorCode;
import org.example.sportsfight.exceptions.InternalException;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.models.enums.FightStatus;
import org.example.sportsfight.repositories.FightRepository;
import org.example.sportsfight.services.clients.SportsOrderClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.example.sportsfight.utils.Models.FIGHT;
import static org.example.sportsfight.utils.Models.PERFORMER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FightServiceTest {
    private FightService fightService;

    @Mock
    private FightRepository fightRepository;

    @Mock
    private PerformerService performerService;

    @Mock
    private SportsOrderClient sportsOrderClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fightService = new FightService(fightRepository, performerService, sportsOrderClient);
    }

    @Test
    void create_shouldExecuteSuccessfully() {
        Fight fight = FIGHT();

        when(fightRepository.save(any(Fight.class))).thenReturn(fight);

        StepVerifier.create(fightService.create(fight))
                .expectNext(fight.getId())
                .expectComplete()
                .verify();
        verify(fightRepository, times(1)).save(any(Fight.class));
        verify(sportsOrderClient, times(1)).updateStatusOrder(eq(fight.getOrderId()), eq("PERFORMANCE"));

    }

    @Test
    void find_shouldReturnFight() {
        Fight fight = FIGHT();

        when(fightRepository.findFightById(any(UUID.class))).thenReturn(fight);

        StepVerifier.create(fightService.find(fight.getId()))
                .expectNext(fight)
                .expectComplete()
                .verify();
        verify(fightRepository, times(1)).findFightById(any(UUID.class));
    }

    @Test
    void find_shouldThrowExceptionWhenFightNotFound() {
        when(fightRepository.findFightById(any(UUID.class))).thenReturn(null);

        StepVerifier.create(fightService.find(UUID.randomUUID()))
                .expectErrorMatches(throwable -> throwable instanceof InternalException &&
                        ((InternalException) throwable).getErrorCode() == ErrorCode.FIGHT_NOT_FOUND)
                .verify();
        verify(fightRepository, times(1)).findFightById(any(UUID.class));
    }

    @Test
    void findPage_shouldReturnPageOfFights() {
        UUID performerId = UUID.randomUUID();
        List<Fight> fights = List.of(new Fight(), new Fight());
        Page<Fight> fightPage = new PageImpl<>(fights);

        when(fightRepository.findByPerformer_Id(any(UUID.class), any(Pageable.class))).thenReturn(fightPage);

        StepVerifier.create(fightService.findPage(Pageable.ofSize(10), performerId))
                .expectNext(fightPage)
                .expectComplete()
                .verify();
        verify(fightRepository, times(1)).findByPerformer_Id(any(UUID.class), any(Pageable.class));

    }

    @Test
    void updateStatus_shouldExecuteSuccessfully() {

        Fight fight = FIGHT();
        fight.setStatus(FightStatus.VICTORY);

        when(fightRepository.findFightById(any(UUID.class))).thenReturn(FIGHT());
        when(performerService.find(any(UUID.class))).thenReturn(Mono.just(PERFORMER()));
        when(fightRepository.save(any(Fight.class))).thenReturn(fight);
        when(performerService.save(any(Performer.class))).thenReturn(Mono.just(PERFORMER()));

        StepVerifier.create(fightService.updateStatus(FIGHT().getId(), FightStatus.VICTORY))
                .expectNextMatches(f -> f.getStatus() == FightStatus.VICTORY)
                .expectComplete()
                .verify();

        verify(fightRepository, times(1)).findFightById(any(UUID.class));
        verify(performerService, times(1)).find(any(UUID.class));
        verify(fightRepository, times(1)).save(any(Fight.class));
        verify(performerService, times(1)).save(any(Performer.class));

    }
}
