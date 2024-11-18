package org.example.sportsorder.controllers.city.dto;

import jakarta.validation.constraints.NotBlank;

public record CityRequest(@NotBlank String name,
                          @NotBlank String region) {
}
