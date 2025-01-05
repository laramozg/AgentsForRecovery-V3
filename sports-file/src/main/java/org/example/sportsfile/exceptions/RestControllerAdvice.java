package org.example.sportsfile.exceptions;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ServerWebInputException;

@ControllerAdvice
public class RestControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    @ApiResponse(responseCode = "500", useReturnTypeSchema = true)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> defaultHandler(Exception ex) {
        logger.error("Handle default error", ex);
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.SERVICE_UNAVAILABLE)
                .asResponseEntity();
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Error> internalException(InternalException ex) {
        logger.error("Internal error", ex);
        return new Error(ex.getHttpStatus().value(), ex.getErrorCode())
                .asResponseEntity();
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<Error> s3Exception(Exception ex) {
        logger.error("Error working with s3", ex);
        return new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.FILE_SERVICE_UNAVAILABLE)
                .asResponseEntity();
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Error> fileException(Exception exception) {
        logger.error("Error with file resolution", exception);
        return new Error(HttpStatus.BAD_REQUEST.value(), ErrorCode.WRONG_FILE)
                .asResponseEntity();
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Error> validationExceptions(Exception exception) {
        logger.error("Handle validation error", exception);
        return new Error(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_REQUEST)
                .asResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> authorizationDeniedException(AccessDeniedException ex) {
        logger.error("Access denied", ex);
        return new Error(HttpStatus.FORBIDDEN.value(), ErrorCode.FORBIDDEN)
                .asResponseEntity();
    }
}