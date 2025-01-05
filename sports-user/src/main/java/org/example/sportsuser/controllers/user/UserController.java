package org.example.sportsuser.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.sportsuser.controllers.user.dto.UserDto;
import org.example.sportsuser.mappers.UserMapper;
import org.example.sportsuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(name = "User controller", description = "Контроллер для работы с пользователями")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @Operation(summary = "Поиск пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> find(@PathVariable UUID id) {
        return userService.find(id).map(userMapper::convert);
    }

    @Operation(summary = "Проверка существования пользователя по логину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь существует", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> exists(@RequestParam String username) {
        return userService.isExists(username);
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

    @Operation(summary = "Блокировка пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Заблокирован", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> block(@PathVariable UUID id) {
        return userService.block(id).then();
    }

    @Operation(summary = "Разблокировка пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Разблокирован", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> unblock(@PathVariable UUID id) {
        return userService.unblock(id).then();
    }

    @Operation(summary = "Подтверждение пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Подтвержден", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/{id}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> confirm(@PathVariable UUID id) {
        return userService.confirm(id).then();
    }

}
