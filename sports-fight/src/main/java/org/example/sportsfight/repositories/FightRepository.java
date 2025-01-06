package org.example.sportsfight.repositories;

import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.enums.FightStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FightRepository extends JpaRepository<Fight, UUID> {
    Fight findFightById(UUID id);

    Page<Fight> findByPerformer_Id(UUID performerId, Pageable pageable);

    List<Fight> findByStatus(FightStatus status);

    long countByPerformerIdAndStatus(UUID performerId, FightStatus status);

    long countByPerformerId(UUID performerId);

}
