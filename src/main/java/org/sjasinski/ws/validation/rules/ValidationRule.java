package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.ValidationContext;

/**
 * Validation rule interface
 */
public interface ValidationRule {

    /**
     * Checks if the validation rule is effective for the trade in the given context
     *
     * @param trade the trade
     * @param validationContext the validation context
     * @return true, if effective
     */
    boolean isEffective(Trade trade, ValidationContext validationContext);

    /**
     * Validates the trade in the given context
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    void validate(Trade trade, ValidationContext validationContext);
}