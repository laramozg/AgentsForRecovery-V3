package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.victim.dto.VictimDto;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.models.Victim;
import org.springframework.stereotype.Component;

@Component
public class VictimMapper {

    public Victim convertToEntity(VictimRequest victimRequest){
        return Victim.builder()
                .firstName(victimRequest.firstName())
                .lastName(victimRequest.lastName())
                .workplace(victimRequest.workplace())
                .position(victimRequest.position())
                .residence(victimRequest.residence())
                .phone(victimRequest.phone())
                .description(victimRequest.description())
                .build();
    }

    public VictimDto convertToDto(Victim victim){
        return new VictimDto(
                victim.getId(),
                victim.getFirstName(),
                victim.getLastName(),
                victim.getWorkplace(),
                victim.getPosition(),
                victim.getResidence(),
                victim.getPhone(),
                victim.getDescription()
        );
    }
}
