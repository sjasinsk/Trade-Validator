package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;
import org.sjasinski.ws.validation.common.ValidationUtils;

public class OptionExerciseStartDateValidationRule implements ValidationRule {
    /**
     * Effective for American options.
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return ValidationUtils.isAmericanOption(trade);
    }

    /**
     * American option has exerciseStartDate, which has to be after the trade date but before the expiry date.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        VanillaOption option = (VanillaOption) trade;
        if (!option.getExerciseStartDate().isAfter(option.getTradeDate())) {
            validationContext.addError(new ValidationError("Exercise start has to be after the trade date."));
        }
        if (!option.getExerciseStartDate().isBefore(option.getExpiryDate())) {
            validationContext.addError(new ValidationError("Exercise start has to be before the expiry date."));
        }
    }
}
