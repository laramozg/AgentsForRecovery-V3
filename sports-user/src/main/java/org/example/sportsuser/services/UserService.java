package org.example.sportsuser.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public Mono<Boolean> isExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Mono<User> find(UUID id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)));
    }

    public Mono<User> find(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)));
    }

    public Mono<User> create(User user) {
        logger.info("Creating user with username - " + user.getUsername());
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> updateRoles(UUID id, Role role) {
        return find(id).flatMap(user -> {
            User updatedUser = user.withRole(role);
            logger.info("User updating role - " + role + " with id - " + id);
            return userRepository.save(updatedUser);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> block(UUID id) {
        return find(id).flatMap(user -> {
            User updatedUser = user.withBlocked(true);
            logger.info("User blocking with id - " + id);
            return userRepository.save(updatedUser);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> unblock(UUID id) {
        return find(id).flatMap(user -> {
            User updatedUser = user.withBlocked(false);
            logger.info("User unblocking with id - " + id);
            return userRepository.save(updatedUser);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> confirm(UUID id) {
        return find(id).flatMap(user -> {
            User updatedUser = user.withConfirmed(true);
            logger.info("User confirmed with id - " + id);
            return userRepository.save(updatedUser);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(UUID id) {
        return find(id).flatMap(user -> {
            logger.info("Deleting user by id - " + id);
            return userRepository.delete(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(String username) {
        return find(username).flatMap(user -> {
            logger.info("Deleting user by username - " + username);
            return userRepository.delete(user);
        });
    }
}