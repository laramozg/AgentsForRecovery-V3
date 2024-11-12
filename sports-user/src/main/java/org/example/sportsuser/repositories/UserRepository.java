package org.example.sportsuser.repositories;

import org.example.sportsuser.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<Boolean> existsByUsername(String username);

    Mono<User> findByUsername(String username);
}
