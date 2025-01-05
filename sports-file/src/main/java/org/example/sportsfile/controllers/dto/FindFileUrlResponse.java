package org.example.sportsfile.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record FindFileUrlResponse(
        @Schema(description = "Ссылка для файла")
        String url) {
}
