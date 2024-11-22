package org.example.sportsuser.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sportsuser.exceptions.ErrorCode;
import org.example.sportsuser.exceptions.InternalException;
import org.example.sportsuser.models.User;
import org.example.sportsuser.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public Mono<Boolean> isExists(String username) {
        logger.info("Checking if user exists with username {}", username);
        return userRepository.existsByUsername(username);
    }

    public Mono<User> find(UUID id) {
        logger.info("Find user by id {}", id);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)));
    }


    public Mono<User> find(String username) {
        logger.info("Find user by username {}", username);
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new InternalException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND)));
    }


    public Mono<User> create(User user) {
        logger.info("Create user {}", user);
        return userRepository.save(user);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> block(UUID id) {
        logger.info("Block user with id {}", id);
        return find(id).flatMap(user -> {
            user.setBlocked(true);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> unblock(UUID id) {
        logger.info("Unblock user with id {}", id);
        return find(id).flatMap(user -> {
            user.setBlocked(false);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<User> confirm(UUID id) {
        logger.info("Confirm user with id {}", id);
        return find(id).flatMap(user -> {
            user.setConfirmed(true);
            return userRepository.save(user);
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(String username) {
        logger.info("Delete user with name {}", username);
        return find(username).flatMap(userRepository::delete);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(UUID id) {
        logger.info("Delete user with id {}", id);
        return find(id).flatMap(userRepository::delete);
    }
}