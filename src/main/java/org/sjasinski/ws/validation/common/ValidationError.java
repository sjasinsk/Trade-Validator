package org.sjasinski.ws.validation.common;

public class ValidationError {
    private String message;

    public ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
