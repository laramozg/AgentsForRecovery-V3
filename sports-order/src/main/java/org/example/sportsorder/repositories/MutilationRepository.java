package org.example.sportsorder.repositories;

import org.example.sportsorder.models.Mutilation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MutilationRepository extends JpaRepository<Mutilation, UUID> {
}
