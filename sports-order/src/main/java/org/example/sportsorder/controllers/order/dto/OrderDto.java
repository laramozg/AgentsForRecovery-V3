package org.example.sportsorder.controllers.order.dto;

import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderDto(UUID id,
                       UUID userId,
                       UUID cityId,
                       UUID victimId,
                       LocalDate deadline,
                       String state,
                       List<MutilationDto> mutilations) {
}
