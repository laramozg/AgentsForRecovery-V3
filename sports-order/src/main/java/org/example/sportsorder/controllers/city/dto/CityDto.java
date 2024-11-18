package org.example.sportsorder.controllers.city.dto;

import java.util.UUID;

public record CityDto(UUID id,
                      String name,
                      String region) {
}
