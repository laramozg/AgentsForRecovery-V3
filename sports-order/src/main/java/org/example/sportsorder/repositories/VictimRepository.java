package org.example.sportsorder.repositories;

import org.example.sportsorder.models.Victim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VictimRepository extends JpaRepository<Victim, UUID> {
}
