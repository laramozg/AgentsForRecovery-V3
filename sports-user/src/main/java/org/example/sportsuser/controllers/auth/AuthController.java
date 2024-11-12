package org.example.sportsuser.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserRequest;
import org.example.sportsuser.controllers.auth.dto.AuthorizeUserResponse;
import org.example.sportsuser.controllers.auth.dto.RegisterUserRequest;
import org.example.sportsuser.controllers.auth.dto.RegisterUserResponse;
import org.example.sportsuser.mappers.AuthMapper;
import org.example.sportsuser.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthMapper authMapper;
    private final AuthService authService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<RegisterUserResponse> register(@Valid @RequestBody Mono<RegisterUserRequest> request) {
        return request.map(authMapper::convert)
                .flatMap(authService::register)
                .map(RegisterUserResponse::new);
    }


    @PostMapping("/tokens")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizeUserResponse> authorize(@Valid @RequestBody Mono<AuthorizeUserRequest> request) {
        return request.map(authMapper::convert)
                .flatMap(authService::authorize);
    }

    @PostMapping("/tokens/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorizeUserResponse> reAuthorize(@RequestParam String refresh) throws InterruptedException {
        return authService.reAuthorize(refresh);
    }

}