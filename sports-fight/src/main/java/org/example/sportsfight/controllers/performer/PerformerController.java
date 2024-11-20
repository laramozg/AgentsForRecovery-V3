package org.example.sportsfight.controllers.performer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsfight.controllers.performer.dto.CreatePerformerResponse;
import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.mappers.PerformerMapper;
import org.example.sportsfight.services.PerformerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/performers")
public class PerformerController {
    private final PerformerService performerService;
    private final PerformerMapper performerMapper;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyAuthority('EXECUTOR')")
//    public Mono<CreatePerformerResponse> createExecutor(@Valid @RequestBody Mono<PerformerRequest> request) {
//        return request.map(performerMapper::convertToEntity)
//                .flatMap(performerService::create)
//                .map(CreatePerformerResponse::new);
//    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<PerformerDto> findExecutor(@PathVariable UUID id) {
//        return performerService.find(id).map(performerMapper::convertToDto);
//    }
//
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyAuthority('EXECUTOR')")
//    public Mono<CreatePerformerResponse> updateExecutor(
//            @Valid @RequestBody Mono<PerformerRequest> request) {
//        return request.map(performerMapper::convertToEntity)
//                .flatMap(performerService::update)
//                .map(CreatePerformerResponse::new);
//    }
}
