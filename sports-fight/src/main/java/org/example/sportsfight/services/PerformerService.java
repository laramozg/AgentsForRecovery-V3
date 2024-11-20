package org.example.sportsfight.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsfight.exceptions.ErrorCode;
import org.example.sportsfight.exceptions.InternalException;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.repositories.PerformerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PerformerService {
    private final PerformerRepository performerRepository;

//    public Mono<Performer> find(UUID id){
//        return Mono.fromCallable(() -> performerRepository.findById(id))
//                .switchIfEmpty(Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.PERFORMER_NOT_FOUND)));
//
//
//    }
}
