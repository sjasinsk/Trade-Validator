package org.sjasinski.ws.validation.common;

import java.util.ArrayList;
import java.util.List;

public class ValidationContext {
    List<ValidationError> validationErrors = new ArrayList<>();

    /**
     * Adds error to validation context.
     *
     * @param error the error
     */
    public void addError(ValidationError error) {
        validationErrors.add(error);
    }

    public List<ValidationError> getErrors() {
        return validationErrors;
    }

    public ValidationResult getValidationResult() {
        return validationErrors.isEmpty() ? ValidationResult.SUCCESS : ValidationResult.FAILURE;
    }
}
