package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.mutilation.dto.MutilationRequest;
import org.example.sportsorder.models.Mutilation;
import org.springframework.stereotype.Component;

@Component
public class MutilationMapper {

    public Mutilation convertToEntity(MutilationRequest mutilationDto){
        return Mutilation.builder()
                .type(mutilationDto.type())
                .price(mutilationDto.price())
                .build();
    }

    public MutilationDto convertToDto(Mutilation mutilation){
        return new MutilationDto(
                mutilation.getId(),
                mutilation.getType(),
                mutilation.getPrice()
        );
    }
}
