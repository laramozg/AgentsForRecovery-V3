package org.example.sportsfile.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record UploadFileResponse(
        @Schema(description = "Путь к файлу")
        String path) {
}
