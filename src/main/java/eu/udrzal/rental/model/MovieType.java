package eu.udrzal.rental.model;

import java.math.BigDecimal;

import static eu.udrzal.rental.service.RentalPriceService.BASIC_PRICE;
import static eu.udrzal.rental.service.RentalPriceService.PREMIUM_PRICE;

public enum MovieType {
    NEW(PREMIUM_PRICE, 1, 2), REGULAR(BASIC_PRICE, 3, 1), OLD(BASIC_PRICE, 5, 1);

    private final BigDecimal pricePerDay;
    private final int daysIncludedInPrice;
    private final int bonusPoints;

    MovieType(BigDecimal pricePerDay, int daysIncludedInPrice, int bonusPoints) {
        this.pricePerDay = pricePerDay;
        this.daysIncludedInPrice = daysIncludedInPrice;
        this.bonusPoints = bonusPoints;
    }

    public BigDecimal calculateRentalPrice(long days) {
        long daysToPayExtra = Math.max(days - daysIncludedInPrice, 0);
        return pricePerDay.add(pricePerDay.multiply(BigDecimal.valueOf(daysToPayExtra)));
    }

    public BigDecimal calculateSurcharge(long daysOverdue) {
        /*
        TODO if client rented OLD movie for 1 day but returned on 3rd day, he will be still charged 2 overdue days
         even when daysIncludedInPrice is 5
         */
        return pricePerDay.multiply(BigDecimal.valueOf(daysOverdue));
    }

    public int getBonusPoints() {
        return bonusPoints;
    }


}
