package org.example.sportsuser.sevices;

import org.example.sportsuser.exceptions.InternalException;
import org.example.sportsuser.models.User;
import org.example.sportsuser.repositories.UserRepository;
import org.example.sportsuser.services.UserService;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.example.sportsuser.utils.Models.UNAUTHORIZED_USER;
import static org.example.sportsuser.utils.Models.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserService userService = new UserService(userRepository);

    @Test
    void isExistsShouldReturnTrue() {
        when(userRepository.existsByUsername(any())).thenReturn(Mono.just(true));

        StepVerifier.create(userService.isExists(USER.getUsername()))
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    void findByUsernameShouldExecuteSuccessfully() {
        when(userRepository.findByUsername(any())).thenReturn(Mono.just(USER));

        StepVerifier.create(userService.find(USER.getUsername()))
                .expectNext(USER)
                .expectComplete()
                .verify();
    }

    @Test
    void findByUsernameShouldThrowUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(Mono.empty());

        StepVerifier.create(userService.find(UNAUTHORIZED_USER.getUsername()))
                .expectError(InternalException.class)
                .verify();
    }

    @Test
    void blockShouldExecuteSuccessfully() {
        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Mono.just(USER));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setBlocked(true);
            return Mono.just(savedUser);
        });

        StepVerifier.create(userService.block(USER.getId()))
                .expectNextMatches(User::isBlocked)
                .verifyComplete();
    }

    @Test
    void unblockShouldExecuteSuccessfully() {
        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Mono.just(USER));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setBlocked(false);
            return Mono.just(savedUser);
        });
        StepVerifier.create(userService.unblock(USER.getId()))
                .expectNextMatches(u -> !u.isBlocked())
                .verifyComplete();
    }

    @Test
    void confirmShouldExecuteSuccessfully() {
        when(userRepository.findByUsername(USER.getUsername())).thenReturn(Mono.just(USER));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setConfirmed(true);
            return Mono.just(savedUser);
        });
        StepVerifier.create(userService.confirm(USER.getId()))
                .expectNextMatches(User::isConfirmed)
                .verifyComplete();
    }


    @Test
    void deleteByUsernameShouldExecuteSuccessfully() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Mono.just(USER));
        when(userRepository.delete(any(User.class))).thenReturn(Mono.empty());

        StepVerifier.create(userService.delete(USER.getUsername()))
                .verifyComplete();
    }
}
