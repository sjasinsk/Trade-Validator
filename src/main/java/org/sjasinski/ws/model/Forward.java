package org.sjasinski.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Forward contract.
 */
public class Forward extends Trade {
    public static final String TYPE_NAME = "Forward";

    private LocalDate valueDate;

    public Forward(@JsonProperty("customer") String customer,
                   @JsonProperty("ccyPair") String ccyPair,
                   @JsonProperty("direction") String direction,
                   @JsonProperty("tradeDate") String tradeDate,
                   @JsonProperty("amount1") BigDecimal amount1,
                   @JsonProperty("amount2") BigDecimal amount2,
                   @JsonProperty("rate") BigDecimal rate,
                   @JsonProperty("valueDate") String valueDate,
                   @JsonProperty("legalEntity") String legalEntity,
                   @JsonProperty("trader") String trader
    ) {
        super(customer, ccyPair, direction, tradeDate, amount1, amount2, rate, legalEntity, trader);
        this.valueDate = parseDate(valueDate);
    }

    public String getType() {
        return TYPE_NAME;
    }

    public LocalDate getValueDate() {
        return  valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }
}
