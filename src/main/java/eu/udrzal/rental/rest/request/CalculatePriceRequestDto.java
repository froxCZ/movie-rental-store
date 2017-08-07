package eu.udrzal.rental.rest.request;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.constraints.NotNull;

public class CalculatePriceRequestDto {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
    @NotNull
    private Collection<Long> tapes;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Collection<Long> getTapes() {
        return tapes;
    }

    public void setTapes(Collection<Long> tapes) {
        this.tapes = tapes;
    }
}
