package org.example.sportsuser.controllers.auth;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsuser.controllers.auth.dto.*;
import org.example.sportsuser.mappers.AuthMapper;
import org.example.sportsuser.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Auth controller", description = "Контроллер для работы с авторизацией и аутентификацией")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthMapper authMapper;
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан", content = @Content(schema = @Schema(implementation = RegisterUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegisterUserResponse> register(@Valid @RequestBody Mono<RegisterUserRequest> request) {
        return request.map(authMapper::convert)
                .flatMap(authService::register)
                .map(RegisterUserResponse::new);
    }

    @Hidden
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Mono<AuthorizationDetails> getAuthDetails() {
        return authService.getAuthDetails();
    }


    @Operation(summary = "Получение токенов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Токены созданы", content = @Content(schema = @Schema(implementation = AuthorizeUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/tokens")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizeUserResponse> authorize(@Valid @RequestBody Mono<AuthorizeUserRequest> request) {
        return request.map(authMapper::convert)
                .flatMap(authService::authorize);
    }

    @Operation(summary = "Обновление токенов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Токены созданы", content = @Content(schema = @Schema(implementation = AuthorizeUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/tokens/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizeUserResponse> reAuthorize(@RequestParam String refresh)  {
        return authService.reAuthorize(refresh);
    }

}