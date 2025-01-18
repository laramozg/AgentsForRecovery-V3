package org.example.sportsfight.mappers;

import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.util.ReactiveSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PerformerMapper {
    private final Logger logger = LoggerFactory.getLogger(PerformerMapper.class);


    public Mono<Performer> convertToEntity(PerformerRequest performerRequest) {
        logger.info("PerformerMapper::convertToEntity");
        return ReactiveSecurityContext.getUsername()
                .map(username -> Performer.builder()
                        .username(username)
                        .email(performerRequest.email())
                        .passportSeriesNumber(performerRequest.passportSeriesNumber())
                        .weight(performerRequest.weight())
                        .height(performerRequest.height())
                        .build());
    }

    public PerformerDto convertToDto(Performer performer) {
        return new PerformerDto(
                performer.getId(),
                performer.getUsername(),
                performer.getPassportSeriesNumber(),
                performer.getWeight(),
                performer.getHeight(),
                performer.getRating(),
                performer.getCompletedOrders());
    }



}
