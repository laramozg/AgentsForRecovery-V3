package org.example.sportsorder.controllers.victim.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record VictimDto(UUID uuid,
                        String firstName,
                        String lastName,
                        String workplace,
                        String position,
                        String residence,
                        String phone,
                        String description) {
}
