package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Victim;
import org.example.sportsorder.repositories.VictimRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VictimService {
    private final VictimRepository victimRepository;
    private static final Logger logger = LoggerFactory.getLogger(VictimService.class);

    public Victim find(UUID id) {
        logger.info("Find Victim with id {}", id);
        return victimRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.VICTIM_NOT_FOUND));
    }

    public UUID createVictim(Victim victim) {
        logger.info("Create Victim with id {}", victim.getId());
        return victimRepository.save(victim).getId();
    }

    public void deleteVictim(UUID id) {
        logger.info("Delete Victim with id {}", id);
        victimRepository.deleteById(id);
    }

    public Page<Victim> findAllVictims(Pageable pageable) {
        logger.info("Find all Victims");
        return victimRepository.findAll(pageable);
    }
}
