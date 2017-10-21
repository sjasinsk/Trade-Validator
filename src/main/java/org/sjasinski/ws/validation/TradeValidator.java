package org.sjasinski.ws.validation;

import org.sjasinski.ws.fixer.FixerService;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.rules.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Trade validator.
 */
public class TradeValidator {
    private List<ValidationRule> validationRuleset;

    public TradeValidator(FixerService fixerService) {
        validationRuleset = Arrays.asList(
                new CounterpartyValidationRule(),
                new CurrencyCodeValidatioRule(),
                new OptionDeliveryDateValidationRule(),
                new OptionExerciseStartDateValidationRule(),
                new OptionStyleValidationRule(),
                new ValueDateAgainstProductTypeValidationRule(),
                new ValueDateAgainstTradeDateValidationRule(),
                new ValueDateOnWorkingDayValidationRule(fixerService));
    }


    public ValidationContext validate(Trade trade) {
        ValidationContext context = new ValidationContext();
        validationRuleset.stream()
                .filter(r -> r.isEffective(trade, context))
                .forEach(r -> r.validate(trade, context));

        return context;
    }

    public List<ValidationContext> validate(List<Trade> trades) {
        List<ValidationContext> contexts = new ArrayList<>();

        trades.forEach(trade -> contexts.add(validate(trade)));

        return contexts;
    }
}
