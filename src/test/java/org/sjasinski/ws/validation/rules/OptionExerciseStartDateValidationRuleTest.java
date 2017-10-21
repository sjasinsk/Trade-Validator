package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.model.VanillaOption;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class OptionExerciseStartDateValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new OptionExerciseStartDateValidationRule();
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
    public void shouldNotBeEffectiveForEuropeanOption() throws Exception {
        assertFalse(rule.isEffective(TradeTestDataProvider.getValidOptionEu(), vc));
    }

    @Test
    public void shouldNotBeEffectiveForAmericanOption() throws Exception {
        assertTrue(rule.isEffective(TradeTestDataProvider.getValidOptionUs(), vc));
    }

    @Test
    public void shouldValidateSuccessfullyExerciseStartAfterTradeBeforeExpiryDates() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldFailForExerciseStartBeforeTradeDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setExerciseStartDate(LocalDate.parse("2016-08-10"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForExerciseStartOnTradeDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setExerciseStartDate(trade.getTradeDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForExerciseStartOnExpiryDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setExerciseStartDate(LocalDate.parse("2016-08-19"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldFailForExerciseStartAfterExpiryDate() throws Exception {
        VanillaOption trade = TradeTestDataProvider.getValidOptionUs();
        trade.setExerciseStartDate(trade.getExpiryDate());
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}