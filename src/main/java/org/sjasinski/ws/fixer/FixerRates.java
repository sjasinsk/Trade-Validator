package org.sjasinski.ws.fixer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FixerRates {
    private final String base;
    private final String date;

    @JsonCreator
    public FixerRates(@JsonProperty("base") String base,
                      @JsonProperty("date") String date) {
        this.base = base;
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

}
