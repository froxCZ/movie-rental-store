package eu.udrzal.rental.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import eu.udrzal.rental.model.MovieTape;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RentalPriceService {
    public static final BigDecimal PREMIUM_PRICE = new BigDecimal("40");
    public static final BigDecimal BASIC_PRICE = new BigDecimal("30");


    public BigDecimal calculateRentalPrice(LocalDate from, LocalDate to, Collection<MovieTape> movieTapes) {
        long days = 1 + DAYS.between(from, to);
        return movieTapes.stream()
                .map(movie -> movie.getMovieDefinition().getType())
                .map(movieType -> movieType.calculateRentalPrice(days))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateSurcharge(LocalDate expectedReturnDate,
                                         LocalDate returnDate,
                                         Collection<MovieTape> tapes) {
        long daysOverdue = Math.max(DAYS.between(expectedReturnDate, returnDate), 0L);
        return tapes.stream()
                .map(tape -> tape.getMovieDefinition().getType().calculateSurcharge(daysOverdue))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
