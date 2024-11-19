package org.example.sportsorder.services;

import org.example.sportsorder.models.Victim;
import org.example.sportsorder.repositories.VictimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.example.sportsorder.utils.Models.VICTIM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VictimServiceTest {

    @Mock
    private VictimRepository victimRepository;

    @InjectMocks
    private VictimService victimService;

    private final Victim testVictim = VICTIM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void shouldFindVictimById() {
        when(victimRepository.findById(testVictim.getId())).thenReturn(Optional.of(testVictim));

        Victim foundVictim = victimService.find(testVictim.getId());

        assertNotNull(foundVictim);
        assertEquals(testVictim.getId(), foundVictim.getId());
    }

    @Test
    void shouldCreateVictim() {
        when(victimRepository.save(any(Victim.class))).thenReturn(testVictim);

        UUID createdId = victimService.createVictim(testVictim);

        assertEquals(testVictim.getId(), createdId);
    }

    @Test
    void shouldDeleteVictim() {
        when(victimRepository.findById(testVictim.getId())).thenReturn(Optional.of(testVictim));

        victimService.deleteVictim(testVictim.getId());
    }
}
