package org.example.sportsfight.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Schema(description = "Класс ошибки")
public class Error {
    @Schema(description = "Id ошибки")
    private final String errorId = UUID.randomUUID().toString();
    @Schema(description = "Статус код")
    private final int status;
    @Schema(description = "Причина")
    private final ErrorCode code;

    public Error(int status, ErrorCode code) {
        this.status = status;
        this.code = code;
    }

    public String getErrorId() {
        return errorId;
    }

    public int getStatus() {
        return status;
    }

    public ErrorCode getCode() {
        return code;
    }

    public ResponseEntity<Error> asResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}
