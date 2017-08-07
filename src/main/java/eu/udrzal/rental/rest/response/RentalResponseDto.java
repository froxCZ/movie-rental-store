package eu.udrzal.rental.rest.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import eu.udrzal.rental.model.MovieTape;
import eu.udrzal.rental.model.Rental;

public class RentalResponseDto {
    private Long id;
    private LocalDateTime rentalTime;
    private LocalDate expectedReturnDate;
    private LocalDateTime returnTime;
    private BigDecimal normalPrice = BigDecimal.ZERO;
    private BigDecimal surcharge = BigDecimal.ZERO;
    private BigDecimal paid = BigDecimal.ZERO;
    private int bonusPoints;
    private Long customerId;
    private Set<Long> tapes = new HashSet<>();
    private BigDecimal priceWithSurcharge;
    private BigDecimal amountToPay;

    public RentalResponseDto() {
    }

    public RentalResponseDto(Rental rental) {
        id = rental.getId();
        rentalTime = rental.getRentalTime();
        expectedReturnDate = rental.getExpectedReturnDate();
        returnTime = rental.getReturnTime();
        normalPrice = rental.getNormalPrice();
        surcharge = rental.getSurcharge();
        paid = rental.getPaid();
        bonusPoints = rental.getBonusPoints();
        customerId = rental.getCustomer().getId();
        tapes = rental.getTapes().stream().map(MovieTape::getId).collect(Collectors.toSet());
        priceWithSurcharge = rental.getPriceWithSurcharge();
        amountToPay = rental.getAmountToPay();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(LocalDateTime rentalTime) {
        this.rentalTime = rentalTime;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(BigDecimal normalPrice) {
        this.normalPrice = normalPrice;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getTapes() {
        return tapes;
    }

    public void setTapes(Set<Long> tapes) {
        this.tapes = tapes;
    }

    public BigDecimal getPriceWithSurcharge() {
        return priceWithSurcharge;
    }

    public void setPriceWithSurcharge(BigDecimal priceWithSurcharge) {
        this.priceWithSurcharge = priceWithSurcharge;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    @Override
    public String toString() {
        return "RentalResponseDto{" +
                "id=" + id +
                ", rentalTime=" + rentalTime +
                ", expectedReturnDate=" + expectedReturnDate +
                ", returnTime=" + returnTime +
                ", normalPrice=" + normalPrice +
                ", surcharge=" + surcharge +
                ", paid=" + paid +
                ", bonusPoints=" + bonusPoints +
                ", customerId=" + customerId +
                ", tapes=" + tapes +
                ", priceWithSurcharge=" + priceWithSurcharge +
                ", amountToPay=" + amountToPay +
                '}';
    }
}
