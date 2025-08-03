package com.example.user_service.exception;

import java.io.Serializable;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
