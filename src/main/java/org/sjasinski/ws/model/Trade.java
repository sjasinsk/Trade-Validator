package org.sjasinski.ws.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Abstract class representing a generic trade.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = Spot.class, name = Spot.TYPE_NAME),
        @Type(value = Forward.class, name = Forward.TYPE_NAME),
        @Type(value = VanillaOption.class, name = VanillaOption.TYPE_NAME)})
public abstract class Trade {
    private String customer;
    private String ccyPair;
    private String direction;
    private LocalDate tradeDate;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private BigDecimal rate;
    private String legalEntity;
    private String trader;

    public Trade(@JsonProperty("customer") String customer,
                 @JsonProperty("ccyPair") String ccyPair,
                 @JsonProperty("direction") String direction,
                 @JsonProperty("tradeDate") String tradeDate,
                 @JsonProperty("amount1") BigDecimal amount1,
                 @JsonProperty("amount2") BigDecimal amount2,
                 @JsonProperty("rate") BigDecimal rate,
                 @JsonProperty("legalEntity") String legalEntity,
                 @JsonProperty("trader") String trader
                 ) {
        this.customer = customer;
        this.ccyPair = ccyPair;
        this.direction = direction;
        this.tradeDate = LocalDate.parse(tradeDate);
        this.amount1 = amount1;
        this.amount2 = amount2;
        this.rate = rate;
        this.legalEntity = legalEntity;
        this.trader = trader;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public abstract String getType();

    public String getDirection() {
        return direction;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public String getTrader() {
        return trader;
    }


    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    protected LocalDate parseDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDate.parse(date);
    }
}
