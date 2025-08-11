package com.airbnb.clone.backend.shared.exception;

public class UserSynchronizationException extends RuntimeException{


    public UserSynchronizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSynchronizationException(String message) {
        super(message);
    }
}
