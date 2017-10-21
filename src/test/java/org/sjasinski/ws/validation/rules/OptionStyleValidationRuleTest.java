package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionStyleValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new OptionStyleValidationRule();
        vc = new ValidationContext();
    }

    @Test
    public void shouldNotBeEffectiveForSpotProductType() throws Exception {
        assertFalse(rule.isEffective(TradeTestDataProvider.getValidSpot(), vc));
    }

    @Test
    public void shouldNotBeEffectiveForForwardProductType() throws Exception {
        assertFalse(rule.isEffective(TradeTestDataProvider.getValidForward(), vc));
    }

    @Test
    public void shouldBeEffectiveForVanillaOptionProductType() throws Exception {
        assertTrue(rule.isEffective(TradeTestDataProvider.getValidOptionEu(), vc));
    }

    @Test
    public void shouldValidateSuccessfullyEuropeanOption() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionEu();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyAmericanOption() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForUnsupportedOptionStyle() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setStyle("CHINESE");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}