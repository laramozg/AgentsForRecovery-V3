package org.example.sportsuser.repositories;

import org.example.sportsuser.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<Boolean> existsByUsername(String username);

    Mono<User> findByUsername(String username);
}
