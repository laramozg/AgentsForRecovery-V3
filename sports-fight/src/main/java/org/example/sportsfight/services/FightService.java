package org.example.sportsfight.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.exceptions.ErrorCode;
import org.example.sportsfight.exceptions.InternalException;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.models.enums.FightStatus;
import org.example.sportsfight.repositories.FightRepository;
import org.example.sportsfight.services.clients.SportsOrderClient;
import org.example.sportsfight.services.clients.dto.OrderDeadlineDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FightService {
    private final Logger logger = LoggerFactory.getLogger(FightService.class);
    private final FightRepository fightRepository;
    private final PerformerService performerService;
    private final SportsOrderClient sportsOrderClient;

    @Transactional
    public Mono<UUID> create(Fight fight) {
        logger.info("Creating fight");
        return Mono.fromCallable(() -> {
            fight.setStatus(FightStatus.PENDING);
            sportsOrderClient.updateStatusOrder(fight.getOrderId(), "PERFORMANCE");
            Fight savedFight = fightRepository.save(fight);
            return savedFight.getId();
        }).subscribeOn(Schedulers.boundedElastic());
    }


    public Mono<Fight> find(UUID id) {
        logger.info("Finding fight by id {}", id);
        return Mono.fromCallable(() -> fightRepository.findFightById(id))
                .switchIfEmpty(
                        Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.FIGHT_NOT_FOUND))
                )
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Page<Fight>> findPage(Pageable pageable, UUID id) {
        logger.info("Finding all fights by page № - {}", pageable.getPageNumber());
        return Mono.fromCallable(() -> fightRepository.findByPerformer_Id(id, pageable))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Transactional
    public Mono<Fight> updateStatus(UUID id, FightStatus newStatus) {
        logger.info("Updating status of fight {}", id);
        return find(id)
                .flatMap(fight -> performerService.find(fight.getPerformer().getId())
                        .flatMap(performer -> {
                            fight.setStatus(newStatus);
                            Mono<Fight> fightSaveMono = Mono.fromCallable(() -> fightRepository.save(fight))
                                    .subscribeOn(Schedulers.boundedElastic());
                            Mono<Void> updateOrderMono;
                            if (newStatus == FightStatus.VICTORY) {
                                performer.setCompletedOrders(performer.getCompletedOrders() + 1);
                                updateOrderMono =
                                        Mono.fromCallable(() -> sportsOrderClient.updateStatusOrder(fight.getId(),
                                                        "DONE"))
                                        .subscribeOn(Schedulers.boundedElastic())
                                        .then();
                            } else {
                                updateOrderMono =
                                        Mono.fromCallable(() -> sportsOrderClient.updateStatusOrder(fight.getId(),
                                                        "WAITING"))
                                        .subscribeOn(Schedulers.boundedElastic())
                                        .then();
                            }
                            updatePerformerRating(performer);
                            Mono<Performer> performerSaveMono = performerService.save(performer);
                            return fightSaveMono
                                    .then(updateOrderMono)
                                    .then(performerSaveMono)
                                    .thenReturn(fight);
                        }))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private void updatePerformerRating(Performer performer) {
        long victoriesCount = fightRepository.countByPerformerIdAndStatus(performer.getId(), FightStatus.VICTORY);
        long totalFightsCount = fightRepository.countByPerformerId(performer.getId());

        if (totalFightsCount > 0) {
            double rating = (double) victoriesCount / totalFightsCount * 10;
            performer.setRating(rating);
        } else {
            performer.setRating(0.0);
        }
    }

    public List<Pair<OrderDeadlineDto, Performer>> calculateDeadline() {
        List<Pair<OrderDeadlineDto, Performer>> pairs = new ArrayList<>();
        fightRepository.findByStatus(FightStatus.PENDING).forEach(fight -> {
            OrderDeadlineDto order = sportsOrderClient.getOrderById(fight.getOrderId());
            if (ChronoUnit.DAYS.between(LocalDate.now(), order.deadline()) < 4) {
                Performer performer = performerService.find(fight.getPerformer().getId()).block();
                if (performer != null) {
                    pairs.add(Pair.of(order, performer));
                }
            }
        });
        return pairs;
    }
}
