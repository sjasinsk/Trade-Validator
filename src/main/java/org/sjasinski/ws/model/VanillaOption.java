package org.sjasinski.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Vanilla option contract.
 */
public class VanillaOption extends Trade {
    public static final String TYPE_NAME = "VanillaOption";

    private String style;
    private String strategy;
    private LocalDate deliveryDate;
    private LocalDate expiryDate;
    private LocalDate exerciseStartDate;
    private String payCcy;
    private BigDecimal premium;
    private String premiumCcy;
    private String premiumType;
    private LocalDate premiumDate;

    public VanillaOption(@JsonProperty("customer") String customer,
                         @JsonProperty("ccyPair") String ccyPair,
                         @JsonProperty("style") String style,
                         @JsonProperty("direction") String direction,
                         @JsonProperty("strategy") String strategy,
                         @JsonProperty("tradeDate") String tradeDate,
                         @JsonProperty("amount1") BigDecimal amount1,
                         @JsonProperty("amount2") BigDecimal amount2,
                         @JsonProperty("rate") BigDecimal rate,
                         @JsonProperty("deliveryDate") String deliveryDate,
                         @JsonProperty("expiryDate") String expiryDate,
                         @JsonProperty("excerciseStartDate") String exerciseStartDate,
                         @JsonProperty("payCcy") String payCcy,
                         @JsonProperty("premium") BigDecimal premium,
                         @JsonProperty("premiumCcy") String premiumCcy,
                         @JsonProperty("premiumType") String premiumType,
                         @JsonProperty("premiumDate") String premiumDate,
                         @JsonProperty("legalEntity") String legalEntity,
                         @JsonProperty("trader") String trader
    ) {
        super(customer, ccyPair, direction, tradeDate, amount1, amount2, rate, legalEntity, trader);
        this.style = style;
        this.strategy = strategy;
        this.deliveryDate = parseDate(deliveryDate);
        this.expiryDate = parseDate(expiryDate);
        this.exerciseStartDate = parseDate(exerciseStartDate);
        this.payCcy = payCcy;
        this.premium = premium;
        this.premiumCcy = premiumCcy;
        this.premiumType = premiumType;
        this.premiumDate = parseDate(premiumDate);
    }

    public String getType() {
        return TYPE_NAME;
    }

    public String getStyle() {
        return style;
    }

    public String getStrategy() {
        return strategy;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getExerciseStartDate() {
        return exerciseStartDate;
    }

    public String getPayCcy() {
        return payCcy;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public String getPremiumCcy() {
        return premiumCcy;
    }

    public String getPremiumType() {
        return premiumType;
    }

    public LocalDate getPremiumDate() {
        return premiumDate;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setExerciseStartDate(LocalDate exerciseStartDate) {
        this.exerciseStartDate = exerciseStartDate;
    }

    public void setPayCcy(String payCcy) {
        this.payCcy = payCcy;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public void setPremiumCcy(String premiumCcy) {
        this.premiumCcy = premiumCcy;
    }

    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }

    public void setPremiumDate(LocalDate premiumDate) {
        this.premiumDate = premiumDate;
    }
}
