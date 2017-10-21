package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import static org.junit.Assert.*;

public class CounterpartyValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new CounterpartyValidationRule();
        vc = new ValidationContext();
    }

    @Test
    public void shouldBeEffectiveForSpotProductType() throws Exception {
        assertTrue(rule.isEffective(TradeTestDataProvider.getValidSpot(), vc));
    }

    @Test
    public void shouldBeEffectiveForForwardProductType() throws Exception {
        assertTrue(rule.isEffective(TradeTestDataProvider.getValidForward(), vc));
    }

    @Test
    public void shouldBeEffectiveForVanillaOptionProductType() throws Exception {
        assertTrue(rule.isEffective(TradeTestDataProvider.getValidOptionEu(), vc));
    }

    @Test
    public void shouldValidateSuccessfullySupportedCustomerPLUTO1() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCustomer("PLUTO1");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullySupportedCustomerPLUTO2() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCustomer("PLUTO2");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForUnsupportedCustomer() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCustomer("PLUTO3");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}