package eu.udrzal.rental.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // time when tapes were rented
    private LocalDateTime rentalTime;

    // date of expected return
    private LocalDate expectedReturnDate;

    // time of real return
    private LocalDateTime returnTime;

    // normal price when returned until the expected date
    private BigDecimal normalPrice = BigDecimal.ZERO;

    private BigDecimal surcharge = BigDecimal.ZERO;
    private BigDecimal paid = BigDecimal.ZERO;
    private int bonusPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rental")
    private Set<MovieTape> tapes = new HashSet<MovieTape>();

    @Version
    private Long version;

    @Transient
    public BigDecimal getPriceWithSurcharge() {
        return getNormalPrice().add(getSurcharge());
    }

    @Transient
    public BigDecimal getAmountToPay() {
        return getPriceWithSurcharge().subtract(getPaid());
    }

    public Long getId() {
        return id;
    }

    public Rental setId(Long id) {
        this.id = id;
        return this;
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

    public Set<MovieTape> getTapes() {
        return tapes;
    }

    public Rental setTapes(Set<MovieTape> tapes) {
        this.tapes = tapes;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
