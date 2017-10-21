package org.sjasinski.ws.validation.rules;

import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationError;
import org.sjasinski.ws.validation.common.ValidationUtils;

public class OptionStyleValidationRule implements ValidationRule {
    /**
     * Effective for option product types.
     */
    @Override
    public boolean isEffective(Trade trade, ValidationContext validationContext) {
        return VanillaOption.TYPE_NAME.equals(trade.getType());
    }

    /**
     * Validates option style.
     *
     * @param trade the trade
     * @param validationContext the validation context
     */
    @Override
    public void validate(Trade trade, ValidationContext validationContext) {
        String style = ((VanillaOption) trade).getStyle();
        if (!ValidationUtils.isValidOptionStyle(style)) {
            validationContext.addError(new ValidationError("Invalid style '" + style + "'."));
        }
    }
}
