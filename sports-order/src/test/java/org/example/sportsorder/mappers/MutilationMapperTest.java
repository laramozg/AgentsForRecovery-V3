package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.mutilation.dto.MutilationRequest;
import org.example.sportsorder.models.Mutilation;
import org.junit.jupiter.api.Test;

import static org.example.sportsorder.utils.Models.MUTILATION;
import static org.example.sportsorder.utils.Models.MUTILATIONREQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MutilationMapperTest {

    private final MutilationMapper mutilationMapper = new MutilationMapper();

    @Test
    void testConvertToEntity() {
        MutilationRequest request = MUTILATIONREQUEST;
        Mutilation mutilation = mutilationMapper.convertToEntity(request);

        assertEquals(request.type(), mutilation.getType());
        assertEquals(request.price(), mutilation.getPrice());
    }

    @Test
    void testConvertToDto() {
        Mutilation mutilation = MUTILATION;
        MutilationDto dto = mutilationMapper.convertToDto(mutilation);

        assertEquals(mutilation.getType(), dto.type());
        assertEquals(mutilation.getPrice(), dto.price());
    }
}
