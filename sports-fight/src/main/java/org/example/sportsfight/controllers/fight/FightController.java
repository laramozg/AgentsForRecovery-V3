package org.example.sportsfight.controllers.fight;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsfight.controllers.fight.dto.FightDto;
import org.example.sportsfight.controllers.fight.dto.FightRequest;
import org.example.sportsfight.controllers.fight.dto.FightResponse;
import org.example.sportsfight.mappers.FightMapper;
import org.example.sportsfight.models.enums.FightStatus;
import org.example.sportsfight.services.FightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fights")
public class FightController {
    private final Logger logger = LoggerFactory.getLogger(FightController.class);
    private final FightService fightService;
    private final FightMapper fightMapper;
    public static final int DEFAULT_PAGE_SIZE = 50;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('EXECUTOR')")
    public Mono<FightResponse> createFight(@Valid @RequestBody Mono<FightRequest> request) {
        logger.info("Create fight request: {}", request);
        return request
                .flatMap(fightMapper::convertToEntity)
                .flatMap(fightService::create)
                .map(FightResponse::new);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Page<FightDto>> findFightsByPerformerId(
            @PathVariable UUID id,
            @Schema(hidden = true)@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.info("Find all fight by performer id: {}", id);
        return fightService.findPage(pageable, id).map(page -> page.map(fightMapper::convertToDto));
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<FightDto> updateFightStatus(@PathVariable UUID id,
                                            @RequestParam FightStatus newStatus) {
        logger.info("Update fight status: {}", newStatus);
        return fightService.updateStatus(id,newStatus).map(fightMapper::convertToDto);

    }
}
