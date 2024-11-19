package org.example.sportsorder.services;

import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.repositories.MutilationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.sportsorder.utils.Models.MUTILATION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MutilationServiceTest {
    @Mock
    private MutilationRepository mutilationRepository;

    @InjectMocks
    private MutilationService mutilationService;

    private final Mutilation testMutilation = MUTILATION;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldFindMutilationById() {
        when(mutilationRepository.findById(testMutilation.getId())).thenReturn(Optional.of(testMutilation));

        Mutilation foundMutilation = mutilationService.find(testMutilation.getId());

        assertNotNull(foundMutilation);
        assertEquals(testMutilation.getId(), foundMutilation.getId());
    }

    @Test
    void shouldThrowWhenMutilationNotFound() {
        UUID id = UUID.randomUUID();
        when(mutilationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InternalException.class, () -> mutilationService.find(id));
    }

    @Test
    void shouldCreateMutilation() {
        when(mutilationRepository.save(any(Mutilation.class))).thenReturn(testMutilation);

        UUID createdId = mutilationService.createMutilation(testMutilation);

        assertEquals(testMutilation.getId(), createdId);
    }

    @Test
    void shouldFindAllMutilationsByIds() {
        List<UUID> ids = List.of(testMutilation.getId());
        when(mutilationRepository.findAllByIdIn(ids)).thenReturn(List.of(testMutilation));

        List<Mutilation> mutilations = mutilationService.findAllMutilationsById(ids);

        assertEquals(1, mutilations.size());
        assertEquals(testMutilation.getId(), mutilations.get(0).getId());
    }
}
