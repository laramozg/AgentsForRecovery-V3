package org.example.sportsuser.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsuser.models.User;
import org.example.sportsuser.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<Boolean> isExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Mono<User> find(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new InterruptedException("User with username '" + username + "' not found")));
    }

    public Mono<User> create(User user) {
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> block(String username) {
        return find(username).flatMap(user -> {
            user.setBlocked(true);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> unblock(String username) {
        return find(username).flatMap(user -> {
            user.setBlocked(false);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> confirm(String username) {
        return find(username).flatMap(user -> {
            user.setConfirmed(true);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(String username) {
        return find(username).flatMap(userRepository::delete);
    }
}