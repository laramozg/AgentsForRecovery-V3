package org.example.sportsuser.controllers.auth;

import org.example.sportsuser.mappers.AuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
//
//    private final AuthMapper authMapper;
//    private final AuthService authService;
//
//    public AuthController(AuthMapper authMapper, AuthService authService) {
//        this.authMapper = authMapper;
//        this.authService = authService;
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<RegisterUserResponse> register(@Valid @RequestBody Mono<RegisterUserRequest> request) {
//        return request.map(authMapper::convert)
//                .flatMap(user -> {
//                    LoggerUtils.trace(() -> "Register user with username - " + user.getUsername());
//                    return authService.register(user);
//                })
//                .map(RegisterUserResponse::new);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("isAuthenticated()")
//    public Mono<AuthorizationDetails> getAuthDetails() {
//        LoggerUtils.trace(() -> "Get authorization details for user");
//        return authService.getAuthDetails();
//    }
//
//    @PostMapping("/tokens")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<AuthorizeUserResponse> authorize(@Valid @RequestBody Mono<AuthorizeUserRequest> request) {
//        return request.map(authMapper::convert)
//                .flatMap(auth -> {
//                    LoggerUtils.trace(() -> "Authorize user with username - " + auth.getUsername());
//                    return authService.authorize(auth);
//                });
//    }
//
//    @PostMapping("/tokens/refresh")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<AuthorizeUserResponse> reAuthorize(@RequestParam String refresh) {
//        LoggerUtils.trace(() -> "Reauthorize user");
//        return authService.reAuthorize(refresh);
//    }

}