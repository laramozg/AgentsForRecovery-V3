package org.example.sportsfight.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsfight.exceptions.ErrorCode;
import org.example.sportsfight.exceptions.InternalException;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.repositories.PerformerRepository;
import org.example.sportsfight.util.ReactiveSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PerformerService {
    private final PerformerRepository performerRepository;
    private final Logger logger = LoggerFactory.getLogger(PerformerService.class);

    public Mono<UUID> create(Performer performer) {
        logger.info("Creating performer");
        return Mono.fromCallable(() -> {
            performer.setRating(0.0);
            performer.setCompletedOrders(0);
            Performer savedPerformer = performerRepository.save(performer);
            return savedPerformer.getId();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Performer> find(UUID id) {
        logger.info("Finding performer by id {}", id);
        return Mono.fromCallable(() -> performerRepository.findPerformerById(id))
                .switchIfEmpty(
                        Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.PERFORMER_NOT_FOUND))
                )
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Performer> findByUsername() {
        logger.info("Finding performer by Username");
        return Mono.fromCallable(() ->
                        performerRepository.findPerformerByUsername(ReactiveSecurityContext.getUsername().block()))
                .switchIfEmpty(
                        Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.PERFORMER_NOT_FOUND))
                )
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Performer> update(UUID id, Performer performer) {
        logger.info("Updating performer");
        return find(id)
                .map(existingPerformer -> {
                    performer.setId(existingPerformer.getId());
                    return performer;
                })
                .flatMap(updatedPerformer ->
                        Mono.fromCallable(() -> performerRepository.save(updatedPerformer))
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }

    public Mono<Performer> save(Performer performer) {
        logger.info("Saving performer with id {}", performer.getId());
        return Mono.fromCallable(() -> performerRepository.save(performer))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
