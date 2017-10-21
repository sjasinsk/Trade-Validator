package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.Spot;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;
import org.sjasinski.ws.validation.common.ValidationUtils;

import java.time.LocalDate;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValueDateAgainstProductTypeValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new ValueDateAgainstProductTypeValidationRule();
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
    public void shouldNotBeEffectiveForVanillaOptionProductType() throws Exception {
        assertFalse(rule.isEffective(TradeTestDataProvider.getValidOptionEu(), vc));
    }

    @Test
    public void shouldValidateSuccessfullyForValueOnTradeDate() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        trade.setValueDate(trade.getValueDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyForValueDateToday() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        trade.setValueDate(ValidationUtils.getCurrentDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyForValueDateInTheFuture() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForValueDateInThePast() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        trade.setValueDate(LocalDate.parse("2016-07-09"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}