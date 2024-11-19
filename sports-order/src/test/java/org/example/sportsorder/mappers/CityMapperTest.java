package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.city.dto.CityRequest;
import org.example.sportsorder.models.City;
import org.junit.jupiter.api.Test;

import static org.example.sportsorder.utils.Models.CITY;
import static org.example.sportsorder.utils.Models.CITYREQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CityMapperTest {
    private final CityMapper cityMapper = new CityMapper();

    @Test
    void testConvertToEntity() {
        CityRequest request = CITYREQUEST;
        var city = cityMapper.convertToEntity(request);

        assertEquals(request.name(), city.getName());
        assertEquals(request.region(), city.getRegion());
    }

    @Test
    void testConvertToDto() {
        City city = CITY;
        var dto = cityMapper.convertToDto(city);

        assertEquals(city.getName(), dto.name());
        assertEquals(city.getRegion(), dto.region());
    }
}
