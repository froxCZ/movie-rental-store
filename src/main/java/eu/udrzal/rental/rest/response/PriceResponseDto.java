package eu.udrzal.rental.rest.response;

import java.math.BigDecimal;

public class PriceResponseDto {
    BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public PriceResponseDto setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
