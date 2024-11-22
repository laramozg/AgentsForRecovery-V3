package org.example.sportsfight.exceptions;

public enum ErrorCode {
    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,
    PERFORMER_NOT_FOUND,
    FIGHT_NOT_FOUND

}