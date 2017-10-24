package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;
import org.sjasinski.ws.validation.common.ValidationUtils;

import java.time.LocalDate;

import static org.sjasinski.ws.validation.common.ValidationUtils.PRODUCT_TYPES_WITH_VALUE_DATE;

public class ValueDateAgainstProductTypeValidationRule implements ValidationRule {
    /**
     * Effective for product types with value date, i.e. SPOT and FORWARD.
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return PRODUCT_TYPES_WITH_VALUE_DATE.contains(trade.getType());
    }

    /**
     * Value date cannot be in the past for any product.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        LocalDate valueDate = ValidationUtils.getValueDate(trade);
        if (ValidationUtils.getCurrentDate().isAfter(valueDate)) {
            validationContext.addError(
                    new ValidationError("Value date cannot be in the past, i.e. before "
                            + ValidationUtils.getCurrentDate() + "."));
        }
    }
}
