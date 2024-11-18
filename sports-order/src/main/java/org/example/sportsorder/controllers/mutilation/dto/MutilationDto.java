package org.example.sportsorder.controllers.mutilation.dto;

import java.util.UUID;

public record MutilationDto(UUID id,
                            String type,
                            Long price) {
}
