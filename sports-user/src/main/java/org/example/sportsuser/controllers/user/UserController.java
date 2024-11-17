package org.example.sportsuser.controllers.user;

import lombok.RequiredArgsConstructor;
import org.example.sportsuser.controllers.user.dto.UserDto;
import org.example.sportsuser.mappers.UserMapper;
import org.example.sportsuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> find(@PathVariable UUID id) {
        return userService.find(id).map(userMapper::convert);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> exists(@RequestParam String username) {
        return userService.isExists(username);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }


    @PatchMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> block(@PathVariable UUID id) {
        return userService.block(id).then();
    }

    @DeleteMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> unblock(@PathVariable UUID id) {
        return userService.unblock(id).then();
    }

    @PatchMapping("/{id}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> confirm(@PathVariable UUID id) {
        return userService.confirm(id).then();
    }

}
