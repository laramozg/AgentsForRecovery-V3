package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.repositories.MutilationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MutilationService {
    private final MutilationRepository mutilationRepository;
    private static final Logger logger = LoggerFactory.getLogger(MutilationService.class);

    public Mutilation find(UUID id) {
        logger.info("Find Mutilation with id {}", id);
        return mutilationRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.MUTILATION_NOT_FOUND));
    }

    public UUID createMutilation(Mutilation mutilation) {
        logger.info("Create Mutilation with id {}", mutilation.getId());
        return mutilationRepository.save(mutilation).getId();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mutilation updateMutilation(UUID id, Mutilation mutilation) {
        logger.info("Update Mutilation with id {}", id);
        Mutilation mutilationOld = find(id);
        mutilation.setId(mutilationOld.getId());
        return mutilationRepository.save(mutilation);
    }

    public void deleteMutilation(UUID id) {
        logger.info("Delete Mutilation with id {}", id);
        mutilationRepository.deleteById(id);
    }

    public Page<Mutilation> findAllMutilations(Pageable pageable) {
        logger.info("Find all mutilations");
        return mutilationRepository.findAll(pageable);
    }

    public List<Mutilation> findAllMutilationsById(List<UUID> mutilationIds) {
        logger.info("Find all mutilations by id {}", mutilationIds);
        List<Mutilation> mutilations = mutilationRepository.findAllByIdIn(mutilationIds);
        if (mutilations.size() != mutilationIds.size()) {
            throw new InternalException(HttpStatus.NOT_FOUND, ErrorCode.MUTILATION_NOT_FOUND);
        }
        return mutilations;
    }

}
