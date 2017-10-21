package org.sjasinski.ws.validation.common;

public enum ValidationResult {
    SUCCESS("Success"),
    FAILURE("Failure");

    private String description;

    ValidationResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
