package org.example.sportsfile.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record UploadFileResponse(
        @JsonProperty("path")
        @Schema(description = "Путь к файлу")
        String path) {
}
