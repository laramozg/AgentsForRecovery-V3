package org.example.sportsorder.controllers.victim;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.victim.dto.VictimDto;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.controllers.victim.dto.VictimResponse;
import org.example.sportsorder.mappers.VictimMapper;
import org.example.sportsorder.services.VictimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/victims")
public class VictimController {
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final VictimService victimService;
    private final VictimMapper victimMapper;
    private static final Logger logger = LoggerFactory.getLogger(VictimController.class);

    @Operation(summary = "Создание жертвы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Жертва создана", content = @Content(schema = @Schema(implementation = VictimResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public VictimResponse createVictim(
            @Valid @RequestBody VictimRequest request) {
        logger.trace("Create Victim request: {}", request);
        return new VictimResponse(victimService.createVictim(victimMapper.convertToEntity(request)));
    }

    @Operation(summary = "Поиск жертвы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Жертва найдена", content = @Content(schema = @Schema(implementation = VictimDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VictimDto findVictim(@PathVariable UUID id) {
        logger.trace("Get Victim by id request: {}", id);
        return victimMapper.convertToDto(victimService.find(id));
    }

    @Operation(summary = "Поиск жертв")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Жертвы найдены", content = @Content(schema = @Schema(implementation = VictimDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<VictimDto> findAllVictims(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Get all Victims request: {}", pageable);
        return victimService.findAllVictims(pageable).map(victimMapper::convertToDto);
    }

    @Operation(summary = "Удаление жертвы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Жертва удалена", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVictim(@PathVariable UUID id) {
        logger.trace("Delete Victim request: {}", id);
        victimService.deleteVictim(id);
    }
}
