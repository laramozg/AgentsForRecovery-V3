package org.example.sportsorder.controllers.victim.dto;

import jakarta.validation.constraints.NotBlank;

public record VictimRequest(@NotBlank String firstName,
                            @NotBlank String lastName,
                            String workplace,
                            String position,
                            String residence,
                            String phone,
                            String description) {
}
