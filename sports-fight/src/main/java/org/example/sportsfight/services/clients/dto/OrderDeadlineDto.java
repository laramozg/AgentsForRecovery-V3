package org.example.sportsfight.services.clients.dto;

import java.time.LocalDate;
import java.util.UUID;

public record OrderDeadlineDto(
        UUID id,
        LocalDate deadline
) {
}
