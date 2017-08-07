package eu.udrzal.rental.rest.request;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class RentalRequestDto {
    @NotNull
    private LocalDate to;
    @NotNull
    private Set<Long> tapes;
    @NotNull
    private Long customerId;

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Set<Long> getTapes() {
        return tapes;
    }

    public void setTapes(Set<Long> tapes) {
        this.tapes = tapes;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
