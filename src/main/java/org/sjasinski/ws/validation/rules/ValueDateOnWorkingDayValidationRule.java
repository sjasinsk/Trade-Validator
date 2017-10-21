package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.fixer.FixerRates;
import org.sjasinski.ws.fixer.FixerService;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;
import org.sjasinski.ws.validation.common.ValidationUtils;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.sjasinski.ws.validation.common.ValidationUtils.PRODUCT_TYPES_WITH_VALUE_DATE;

public class ValueDateOnWorkingDayValidationRule implements ValidationRule {

    private FixerService fixerService;

    /**
     * Effective for product types with value date, i.e. SPOT and FORWARD.
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return PRODUCT_TYPES_WITH_VALUE_DATE.contains(trade.getType());
    }

    /**
     * Value date cannot fall on weekend or non-working day for currency.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        LocalDate valueDate = ValidationUtils.getValueDate(trade);
        String baseCcy = ValidationUtils.getBaseCurrency(trade);
        FixerRates rates = fixerService.rates(valueDate.toString(), baseCcy);
        if (!valueDate.toString().equals(rates.getDate())) {
            validationContext.addError(
                    new ValidationError("Value date cannot fall on weekend or non-working day for currency."));
        }
    }

    public ValueDateOnWorkingDayValidationRule(FixerService fixerService) {
        this.fixerService = fixerService;
    }
}
