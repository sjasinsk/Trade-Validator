package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class OptionDeliveryDateValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new OptionDeliveryDateValidationRule();
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
    public void shouldValidateSuccessfullyDeliveryDateAfterPremiumAndExpiryDates() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForDeliveryBeforePremiumDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setDeliveryDate(LocalDate.parse("2016-08-11"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForDeliveryOnPremiumDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setDeliveryDate(trade.getPremiumDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForDeliveryBeforeExpiryDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setDeliveryDate(LocalDate.parse("2016-08-18"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForDeliveryOnExpiryDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setDeliveryDate(trade.getExpiryDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}