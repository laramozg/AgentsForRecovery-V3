package org.example.sportsorder.exceptions;

public enum ErrorCode {
    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,

    CITY_NOT_FOUND,

    MUTILATION_NOT_FOUND,
    VICTIM_NOT_FOUND,
    ORDER_NOT_FOUND

}