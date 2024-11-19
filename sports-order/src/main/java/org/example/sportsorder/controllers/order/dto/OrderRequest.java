package org.example.sportsorder.controllers.order.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderRequest(@NotNull UUID cityId,
                           @NotNull UUID victimId,
                           @NotNull LocalDate deadline,
                           List<UUID> mutilationIds) {
}
