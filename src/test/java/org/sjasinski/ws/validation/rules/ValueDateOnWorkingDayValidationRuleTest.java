package org.sjasinski.ws.validation.rules;

import org.junit.Before;
import org.junit.Test;
import org.sjasinski.ws.fixer.FixerRates;
import org.sjasinski.ws.fixer.FixerService;
import org.sjasinski.ws.model.Spot;
import org.sjasinski.ws.validation.common.TradeTestDataProvider;
import org.sjasinski.ws.validation.common.ValidationContext;
import org.sjasinski.ws.validation.common.ValidationResult;

import java.time.LocalDate;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ValueDateOnWorkingDayValidationRuleTest {
    private ValidationRule rule;
    private ValidationContext vc;

    @Before
    public void setUp(){
        rule = new ValueDateOnWorkingDayValidationRule(new FixerService() {
            @Override
            public FixerRates rates(String date, String base)  {
                if ("2016-01-01".equals(date)) {
                    return new FixerRates(base, "2016-01-03");
                }
                if ("2017-10-15".equals(date)) {
                    return new FixerRates(base, "2017-10-16");
                }
                return new FixerRates(base, date);
            }
        });
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
    public void shouldValidateSuccessfullyForValueDateOnWorkingDay() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        rule.validate(trade, vc);
        assertEquals(ValidationResult.SUCCESS, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyForValueDateOnNonWorkingDay() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        trade.setValueDate(LocalDate.parse("2016-01-01"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }

    @Test
    public void shouldValidateSuccessfullyForValueDateOnWeekend() throws Exception {
        Spot trade = TradeTestDataProvider.getValidSpot();
        trade.setValueDate(LocalDate.parse("2017-10-15"));
        rule.validate(trade, vc);
        assertEquals(ValidationResult.FAILURE, vc.getValidationResult());
    }
}