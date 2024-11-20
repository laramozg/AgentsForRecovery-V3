package org.example.sportsfight.controllers.performer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerformerRequest (@NotBlank String passportSeriesNumber,
                                @NotNull Double weight,
                                @NotNull Double height){
}
