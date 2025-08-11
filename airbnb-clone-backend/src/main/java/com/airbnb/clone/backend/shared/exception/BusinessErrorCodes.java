package com.airbnb.clone.backend.shared.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum BusinessErrorCodes {
    USER_NOT_FOUND("U-1000","USER_NOT_FOUND",NOT_FOUND),
    USER_ALREADY_EXISTS("U-1001","USER_ALREADY_EXISTS",BAD_REQUEST),
    MISSING_EMAIL_FIELD("U-1002","MISSING_EMAIL",BAD_REQUEST),
    MISSING_PASSWORD_FIELD("U-1003","MISSING_PASSWORD",BAD_REQUEST),
    MISSING_FIRST_NAME_FIELD("U-1004","MISSING_FIRST_NAME",BAD_REQUEST),
    MISSING_LAST_NAME_FIELD("U-1005","MISSING_LAST_NAME",BAD_REQUEST),
    ;

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    BusinessErrorCodes(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
