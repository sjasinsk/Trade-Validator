package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;
import org.sjasinski.ws.validation.common.ValidationUtils;

public class CurrencyCodeValidatioRule implements ValidationRule {
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

    /**
     * Validates trade currencies against a set of valid currency codes.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        String baseCcy = ValidationUtils.getBaseCurrency(trade);
        String quoteCcy = ValidationUtils.getQuoteCurrency(trade);

        if (!ValidationUtils.isValidCurrencyCode(baseCcy)) {
            validationContext.addError(new ValidationError("Base currency '" + baseCcy + "' is not a valid currency code."));
        }

        if (!ValidationUtils.isValidCurrencyCode(quoteCcy)) {
            validationContext.addError(new ValidationError("Quote currency '" + quoteCcy + "' is not a valid currency code."));
        }

        if (VanillaOption.TYPE_NAME.equals(trade.getType())) {
            VanillaOption option = (VanillaOption) trade;
            String payCcy = option.getPayCcy();
            if (!ValidationUtils.isValidCurrencyCode(payCcy)) {
                validationContext.addError(new ValidationError("Pay currency '" + payCcy + "' is not a valid currency code."));
            }
            String premiumCcy = option.getPremiumCcy();
            if (!ValidationUtils.isValidCurrencyCode(premiumCcy)) {
                validationContext.addError(new ValidationError("Premium currency '" + premiumCcy + "' is not a valid currency code."));
            }
        }
    }
}
