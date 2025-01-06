package org.example.sportsnotification.exceptions;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

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
