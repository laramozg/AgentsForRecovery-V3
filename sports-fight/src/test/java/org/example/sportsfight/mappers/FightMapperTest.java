package org.example.sportsfight.mappers;

import org.example.sportsfight.controllers.fight.dto.FightDto;
import org.example.sportsfight.controllers.fight.dto.FightRequest;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.services.PerformerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfight.utils.Models.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FightMapperTest {

    @InjectMocks
    private FightMapper fightMapper;

    @Mock
    private PerformerService performerService;

    private final FightRequest fightRequest = FIGHT_REQUEST();
    private final Performer performer = PERFORMER();

    @Test
    void testConvertToEntity() {
        when(performerService.find(fightRequest.performerId()))
                .thenReturn(Mono.just(performer));

        Mono<Fight> fightMono = fightMapper.convertToEntity(fightRequest);
        Fight fight = fightMono.block();

        assertThat(fight).isNotNull();
        assertThat(fight.getPerformer()).isEqualTo(performer);
        assertThat(fight.getOrderId()).isEqualTo(fightRequest.orderId());
    }

    @Test
    void testConvertToDto() {
        Fight fight = FIGHT();
        FightDto fightDto = fightMapper.convertToDto(fight);

        assertThat(fightDto).isNotNull();
        assertThat(fightDto.id()).isEqualTo(fight.getId());
        assertThat(fightDto.performerId()).isEqualTo(fight.getPerformer().getId());
        assertThat(fightDto.orderId()).isEqualTo(fight.getOrderId());
        assertThat(fightDto.status()).isEqualTo(fight.getStatus().name());
    }
}