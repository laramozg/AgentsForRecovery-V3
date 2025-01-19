package org.example.sportsfight.controllers.performer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Performer controller", description = "Контроллер для работы с личными данныеми исполнителя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/performers")
public class PerformerController {
    private final PerformerService performerService;
    private final PerformerMapper performerMapper;
    private final Logger logger = LoggerFactory.getLogger(PerformerController.class);

    @Operation(summary = "Создание исполнителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Исполнитель создан", content = @Content(schema = @Schema(implementation = CreatePerformerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
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

    @Operation(summary = "Поиск исполнителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Исполнитель найден", content = @Content(schema = @Schema(implementation = PerformerDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PerformerDto> findPerformer(@PathVariable UUID id) {
        logger.info("Find performer by id: {}", id);
        return performerService.find(id).map(performerMapper::convertToDto);
    }

    @Operation(summary = "Поиск исполнителя по пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Исполнитель найден", content = @Content(schema = @Schema(implementation = PerformerDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<PerformerDto> findPerformerByUserId() {
        logger.info("Find performer by User");
        return performerService.findByUsername().map(performerMapper::convertToDto);
    }

    @Operation(summary = "Обновление исполнителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Исполнитель обновлен", content = @Content(schema = @Schema(implementation = PerformerDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
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
