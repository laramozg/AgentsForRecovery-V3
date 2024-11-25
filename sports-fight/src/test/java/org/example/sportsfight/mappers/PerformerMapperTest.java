package org.example.sportsfight.mappers;

import org.example.sportsfight.controllers.performer.dto.PerformerDto;
import org.example.sportsfight.models.Performer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfight.utils.Models.PERFORMER;

@ExtendWith(MockitoExtension.class)
class PerformerMapperTest {
    private final PerformerMapper performerMapper = new PerformerMapper();


    @Test
    void testConvertToDto() {
        Performer performer = PERFORMER();

        PerformerDto performerDto = performerMapper.convertToDto(performer);

        assertThat(performerDto).isNotNull();
        assertThat(performerDto.id()).isEqualTo(performer.getId());
        assertThat(performerDto.userId()).isEqualTo(performer.getUsername());
        assertThat(performerDto.passportSeriesNumber()).isEqualTo(performer.getPassportSeriesNumber());
    }


}
