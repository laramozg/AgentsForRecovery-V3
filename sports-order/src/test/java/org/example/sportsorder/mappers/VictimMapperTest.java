package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.victim.dto.VictimDto;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.models.Victim;
import org.junit.jupiter.api.Test;

import static org.example.sportsorder.utils.Models.VICTIM;
import static org.example.sportsorder.utils.Models.VICTIMREQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VictimMapperTest {
    private final VictimMapper victimMapper = new VictimMapper();

    @Test
    void testConvertToEntity() {
        VictimRequest request = VICTIMREQUEST;
        Victim victim = victimMapper.convertToEntity(request);

        assertEquals(request.firstName(), victim.getFirstName());
        assertEquals(request.lastName(), victim.getLastName());
        assertEquals(request.workplace(), victim.getWorkplace());
    }

    @Test
    void testConvertToDto() {
        Victim victim = VICTIM;

        VictimDto dto = victimMapper.convertToDto(victim);

        assertEquals(victim.getFirstName(), dto.firstName());
        assertEquals(victim.getLastName(), dto.lastName());
    }
}
