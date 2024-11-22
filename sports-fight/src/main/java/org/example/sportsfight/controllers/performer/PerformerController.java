package org.example.sportsfight.controllers.performer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsfight.controllers.performer.dto.CreatePerformerResponse;
import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.mappers.PerformerMapper;
import org.example.sportsfight.services.PerformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(PerformerController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('EXECUTOR')")
    public Mono<CreatePerformerResponse> createPerformer(@Valid @RequestBody Mono<PerformerRequest> request) {
        logger.info("Create performer request: {}", request);
        return request
                .flatMap(performerMapper::convertToEntity)
                .flatMap(performerService::create)
                .map(CreatePerformerResponse::new);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PerformerDto> findPerformer(@PathVariable UUID id) {
        logger.info("Find performer by id: {}", id);
        return performerService.find(id).map(performerMapper::convertToDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<PerformerDto> findPerformerByUserId() {
        logger.info("Find performer by User");
        return performerService.findByUsername().map(performerMapper::convertToDto);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('EXECUTOR')")
    public Mono<PerformerDto> updatePerformer(
            @PathVariable UUID id,
            @Valid @RequestBody Mono<PerformerRequest> request) {
        logger.info("Update performer request: {}", request);
        return request
                .flatMap(performerMapper::convertToEntity)
                .flatMap(performer -> performerService.update(id, performer))
                .map(performerMapper::convertToDto);
    }
}
