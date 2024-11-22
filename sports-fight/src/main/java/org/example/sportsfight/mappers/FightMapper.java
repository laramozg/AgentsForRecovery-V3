package org.example.sportsfight.mappers;

import lombok.RequiredArgsConstructor;
import org.example.sportsfight.controllers.fight.dto.FightDto;
import org.example.sportsfight.controllers.fight.dto.FightRequest;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.services.PerformerService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class FightMapper {
    private final PerformerService performerService;

    public Mono<Fight> convertToEntity(FightRequest fightRequest) {
        return performerService.find(fightRequest.performerId())
                .map(performer -> Fight.builder()
                        .performer(performer)
                        .orderId(fightRequest.orderId())
                        .build());
    }

    public FightDto convertToDto(Fight fight) {
        return new FightDto(
                fight.getId(),
                fight.getPerformer().getId(),
                fight.getOrderId(),
                fight.getStatus().name());
    }
}
