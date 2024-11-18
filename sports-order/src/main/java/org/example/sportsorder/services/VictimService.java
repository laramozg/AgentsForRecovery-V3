package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Victim;
import org.example.sportsorder.repositories.VictimRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VictimService {
    private final VictimRepository victimRepository;

    public Victim find(UUID id) {
        return victimRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.VICTIM_NOT_FOUND));
    }

    public UUID createVictim(Victim victim) {
        return victimRepository.save(victim).getId();
    }

    public void deleteVictim(UUID id) {
        find(id);
        victimRepository.deleteById(id);
    }

    public Page<Victim> findAllVictims(Pageable pageable) {
        return victimRepository.findAll(pageable);
    }
}
