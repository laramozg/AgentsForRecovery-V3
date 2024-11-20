package org.example.sportsfight.mappers;

import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.models.Performer;
import org.springframework.stereotype.Component;

@Component
public class PerformerMapper {
    public Performer convertToEntity(PerformerRequest performerRequest) {
        return Performer.builder()
                .passportSeriesNumber(performerRequest.passportSeriesNumber())
                .weight(performerRequest.weight())
                .height(performerRequest.height()).build();
    }

    public PerformerDto convertToDto(Performer performer) {
        return new PerformerDto(
                performer.getId(),
                performer.getUserId(),
                performer.getPassportSeriesNumber(),
                performer.getWeight(),
                performer.getHeight(),
                performer.getRating(),
                performer.getCompletedOrders());
    }
}
