package org.sjasinski.ws.validation.common;

import org.sjasinski.ws.model.Forward;
import org.sjasinski.ws.model.Spot;
import org.sjasinski.ws.model.VanillaOption;

import java.math.BigDecimal;

public class TradeTestDataProvider {

    public static Spot getValidSpot() {
        return new Spot(
                "PLUTO1",
                "EURUSD",
                "BUY",
                "2016-08-11",
                new BigDecimal("1000000.00"),
                new BigDecimal("1120000.00"),
                new BigDecimal("1.12"),
                "2016-08-15",
                "CS Zurich",
                "Johann Baumfiddler"
        );
    }

    public static Forward getValidForward() {
        return new Forward(
                "PLUTO1",
                "EURUSD",
                "BUY",
                "2016-08-11",
                new BigDecimal("1000000.00"),
                new BigDecimal("1120000.00"),
                new BigDecimal("1.12"),
                "2016-08-15",
                "CS Zurich",
                "Johann Baumfiddler"
        );
    }


    public static VanillaOption getValidOptionUs() {
        return new VanillaOption(
                "PLUTO1",
                "EURUSD",
                "AMERICAN",
                "BUY",
                "CALL",
                "2016-08-11",
                new BigDecimal("1000000.00"),
                new BigDecimal("1120000.00"),
                new BigDecimal("1.12"),
                "2016-08-22",
                "2016-08-19",
                "2016-08-12",
                "USD",
                new BigDecimal("0.20"),
                "USD",
                "%USD",
                "2016-08-12",
                "CS Zurich",
                "Johann Baumfiddler"
        );
    }


    public static VanillaOption getValidOptionEu() {
        return new VanillaOption(
                "PLUTO2",
                "EURUSD",
                "EUROPEAN",
                "BUY",
                "CALL",
                "2016-08-11",
                new BigDecimal("1000000.00"),
                new BigDecimal("1120000.00"),
                new BigDecimal("1.12"),
                "2016-08-22",
                "2016-08-19",
                null,
                "USD",
                new BigDecimal("0.20"),
                "USD",
                "%USD",
                "2016-08-12",
                "CS Zurich",
                "Johann Baumfiddler"
        );
    }
}
