package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;

import static org.sjasinski.ws.validation.common.ValidationUtils.SUPPORTED_COUNTERPARTIES;

public class CounterpartyValidationRule implements ValidationRule {
    /**
     * Effective for all product types.
     *
     * @param trade the trade
     * @param validationContext the validation context
     * @return true, if effective
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return true;
    }

    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        if (!SUPPORTED_COUNTERPARTIES.contains(trade.getCustomer())) {
            validationContext.addError(
                    new ValidationError("Customer '" + trade.getCustomer() + "' is not supported."));
        }
    }
}
