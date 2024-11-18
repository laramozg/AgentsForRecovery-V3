package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.city.dto.CityDto;
import org.example.sportsorder.controllers.city.dto.CityRequest;
import org.example.sportsorder.models.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public City convertToEntity(CityRequest cityRequest){
        return City.builder()
                .name(cityRequest.name())
                .region(cityRequest.region())
                .build();
    }

    public CityDto convertToDto(City city){
        return new CityDto(city.getId(), city.getName(), city.getRegion());
    }
}
