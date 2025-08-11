package com.airbnb.clone.backend.user.adapter.in.rest;

import com.airbnb.clone.backend.shared.exception.BusinessErrorCodes;
import com.airbnb.clone.backend.shared.exception.UserSynchronizationException;
import com.airbnb.clone.backend.user.adapter.in.rest.dto.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice()
public class UserResourceExceptionHandler {

    @ExceptionHandler(UserSynchronizationException.class)
    public ResponseEntity<List<ErrorResponse>> handleDataIntegrityViolationException(
            UserSynchronizationException e) {

        List<ErrorResponse> errorResponses = new ArrayList<>();
        BusinessErrorCodes errorCodes;

        Throwable cause = e.getCause();

       if(cause!=null && cause instanceof DataIntegrityViolationException){
           String causeMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
           return handleTransactionalException(causeMessage);
       }
       errorResponses.add(new ErrorResponse("U-1006",e.getMessage()));
       return ResponseEntity.internalServerError().body(errorResponses);

    }

    private ResponseEntity<List<ErrorResponse>> handleTransactionalException(String causeMessage) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        BusinessErrorCodes errorCodes;
        if (causeMessage.contains("email") && causeMessage.contains("null value")) {
            errorCodes = BusinessErrorCodes.MISSING_EMAIL_FIELD;
            errorResponses.add(new ErrorResponse(errorCodes.getCode(), errorCodes.getDescription()));
        }
        if (causeMessage.contains("first_name") && causeMessage.contains("null value")) {
            errorCodes = BusinessErrorCodes.MISSING_FIRST_NAME_FIELD;
            errorResponses.add(new ErrorResponse(errorCodes.getCode(), errorCodes.getDescription()));
        }
        if (causeMessage.contains("last_name") && causeMessage.contains("null value")) {
            errorCodes = BusinessErrorCodes.MISSING_LAST_NAME_FIELD;
            errorResponses.add(new ErrorResponse(errorCodes.getCode(), errorCodes.getDescription()));
        }
        return ResponseEntity.badRequest().body(errorResponses);
    }

}
