package org.example.sportsorder.repositories;

import org.example.sportsorder.models.Mutilation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MutilationRepository extends JpaRepository<Mutilation, UUID> {
    List<Mutilation> findAllByIdIn(List<UUID> mutilationIds);

}
