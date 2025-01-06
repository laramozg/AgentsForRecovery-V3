package org.example.sportsnotification.exceptions;

import com.fasterxml.jackson.core.JacksonException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {
    private final Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);

    @ApiResponse(responseCode = "500", useReturnTypeSchema = true)
    @ExceptionHandler(Exception.class)
    public void defaultHandler(Exception ex) {
        logger.error("Handle default error", ex);
    }

    @ExceptionHandler(MailException.class)
    public void internalException(MailException ex) {
        logger.error("Mail error", ex);
    }

    @ExceptionHandler(JacksonException.class)
    public void authorizationDeniedException(JacksonException ex) {
        logger.error("Parse message error", ex);
    }
}