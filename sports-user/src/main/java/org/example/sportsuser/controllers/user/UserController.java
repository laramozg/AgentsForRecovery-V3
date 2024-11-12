package org.example.sportsuser.controllers.user;

import lombok.RequiredArgsConstructor;
import org.example.sportsuser.controllers.user.dto.UserDto;
import org.example.sportsuser.mappers.UserMapper;
import org.example.sportsuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> find(@PathVariable String username) {
        return userService.find(username).map(userMapper::convert);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> exists(@RequestParam String username) {
        return userService.isExists(username);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String username) {
        return userService.delete(username);
    }


    @PatchMapping("/{username}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> block(@PathVariable String username) {
        return userService.block(username).then();
    }

    @DeleteMapping("/{username}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> unblock(@PathVariable String username) {
        return userService.unblock(username).then();
    }

    @PatchMapping("/{username}/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public Mono<Void> confirm(@PathVariable String username) {
        return userService.confirm(username).then();
    }

}
