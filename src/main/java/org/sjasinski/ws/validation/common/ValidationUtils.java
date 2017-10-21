package org.sjasinski.ws.validation.common;

import org.apache.commons.lang3.EnumUtils;
import org.sjasinski.ws.model.Forward;
import org.sjasinski.ws.model.Spot;
import org.sjasinski.ws.model.Trade;
import org.sjasinski.ws.model.VanillaOption;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ValidationUtils {
    /**
     * Product types with value date, i.e. SPOT and FORWARD.
     */
    public static final List<String> PRODUCT_TYPES_WITH_VALUE_DATE = Collections.unmodifiableList(
            Arrays.asList(Forward.TYPE_NAME, Spot.TYPE_NAME));

    /**
     * Supported counterparties.
     */
    public static final List<String> SUPPORTED_COUNTERPARTIES = Collections.unmodifiableList(
            Arrays.asList("PLUTO1", "PLUTO2"));

    /**
     * Assumed current date.
     */
    private static final String CURRENT_DATE = "2016-08-10";

    /**
     * Gets value date of the contract.
     *
     * @param trade contract
     * @return value date
     */
    public static LocalDate getValueDate(Trade trade) {
        LocalDate date = null;
        if (Forward.TYPE_NAME.equals(trade.getType())) {
            date = ((Forward) trade).getValueDate();
        } else if (Spot.TYPE_NAME.equals(trade.getType())) {
            date = ((Spot) trade).getValueDate();
        }
        return date;
    }

    /**
     * Checks if ccyCode represents a valid currency code.
     * @param ccyCode
     * @return true, if ccyCode represents a valid currency code
     */
    public static boolean isValidCurrencyCode(String ccyCode) {
        return EnumUtils.isValidEnum(CurrencyCode.class, ccyCode);
    }

    /**
     * Checks if style represents a valid option style.
     * @param style
     * @return true, if ccyCode represents a valid currency code
     */
    public static boolean isValidOptionStyle(String style) {
        return EnumUtils.isValidEnum(OptionStyle.class, style);
    }

    /**
     * Gets quote currency of the contract.
     *
     * @param trade contract
     * @return quote currency
     */
    public static String getQuoteCurrency(Trade trade) {
        return trade.getCcyPair().substring(0, 3);
    }

    /**
     * Gets base currency of the contract.
     *
     * @param trade contract
     * @return base currency
     */
    public static String getBaseCurrency(Trade trade) {
        return trade.getCcyPair().substring(3);
    }

    /**
     * Checks if trade is an American option.
     * @param trade
     * @return true, if trade is an American option.
     */
    public static boolean isAmericanOption(Trade trade) {
        return VanillaOption.TYPE_NAME.equals(trade.getType())
                && OptionStyle.AMERICAN.toString().equals(((VanillaOption) trade).getStyle());
    }

    /**
     * Gets current date from coding test assumptions.
     *
     * @return current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.parse(CURRENT_DATE);
    }

    private ValidationUtils() {}
}
