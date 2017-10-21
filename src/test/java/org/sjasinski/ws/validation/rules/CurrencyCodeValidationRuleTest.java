package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencyCodeValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new CurrencyCodeValidatioRule();
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
    public void shouldValidateSuccessfullyValidCurrencyPair() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCcyPair("EURUSD");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForMalformedCurrencyPair() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCcyPair("EUR1USD3");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForInvalidBaseCurrency() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCcyPair("EUUUSD");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForInvalidQuoteCurrency() throws Exception {
        Trade trade = TradeTestDataProvider.getValidSpot();
        trade.setCcyPair("EURUSA");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyValidPayCurrency() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionEu();
        trade.setPayCcy("EUR");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForInvalidPayCurrency() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionEu();
        trade.setPayCcy("EUU");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyValidPremiumCurrency() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionEu();
        trade.setPayCcy("EUR");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForInvalidPremiumCurrency() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionEu();
        trade.setPremiumCcy("EUU");
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}