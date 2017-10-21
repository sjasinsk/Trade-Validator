package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;

public class OptionDeliveryDateValidationRule implements ValidationRule {
    /**
     * Effective for option product types.
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return VanillaOption.TYPE_NAME.equals(trade.getType());
    }

    /**
     * Validates option delivery vs expiry and premium dates.
     * Expiry date and premium date shall be before delivery date.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        VanillaOption option = (VanillaOption) trade;
        if (!option.getDeliveryDate().isAfter(option.getExpiryDate())) {
            validationContext.addError(new ValidationError("Delivery date has to be after the expiry date."));
        }
        if (!option.getDeliveryDate().isAfter(option.getPremiumDate())) {
            validationContext.addError(new ValidationError("Delivery date has to be after the premium date."));
        }
    }
}
