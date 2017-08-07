package eu.udrzal.rental.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import eu.udrzal.rental.model.Customer;
import eu.udrzal.rental.model.MovieTape;
import eu.udrzal.rental.model.MovieType;
import eu.udrzal.rental.model.Rental;
import eu.udrzal.rental.model.TapeRentalHistory;
import eu.udrzal.rental.repository.CustomerRepository;
import eu.udrzal.rental.repository.MovieRepository;
import eu.udrzal.rental.repository.RentalRepository;
import eu.udrzal.rental.repository.TapeRentalHistoryRepository;
import eu.udrzal.rental.rest.request.RentalRequestDto;

@Service
public class RentalService {

    @Autowired
    Clock clock;

    @Autowired
    RentalPriceService rentalPriceService;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TapeRentalHistoryRepository tapeRentalHistoryRepository;

    @Autowired
    MovieRepository movieRepository;

    /**
     * Creates a Rental from data passed via RentalRequestDto.
     */
    @Transactional
    public Rental createRental(RentalRequestDto rentalRequestDto) {
        Rental rental = new Rental();

        rentTapes(rental, rentalRequestDto.getTapes());
        rental.setRentalTime(LocalDateTime.now(clock));
        rental.setExpectedReturnDate(rentalRequestDto.getTo());
        boolean rentalIsBeforeOrEqualToExpectedReturn = !rental
                .getRentalTime().toLocalDate().isAfter(rental.getExpectedReturnDate());
        Preconditions.checkArgument(rentalIsBeforeOrEqualToExpectedReturn, "Expected return date cannot be before rental date.");

        Customer customer = customerRepository.findOne(rentalRequestDto.getCustomerId());
        Preconditions.checkNotNull(customer, "Could not find customer with given id.");
        rental.setCustomer(customer);
        rental.setBonusPoints(calculateBonusPoints(rental.getTapes()));
        customer.setBonusPoints(customer.getBonusPoints() + rental.getBonusPoints());

        BigDecimal rentalPrice = rentalPriceService.calculateRentalPrice(
                rental.getRentalTime().toLocalDate(),
                rental.getExpectedReturnDate(),
                rental.getTapes());
        rental.setNormalPrice(rentalPrice);

        rentalRepository.save(rental);
        return rental;
    }

    /**
     * Verifies that provided tapes can be rented and sets them on the provided rental.
     */
    private void rentTapes(Rental rental, Collection<Long> tapes) {
        List<MovieTape> tapesToRent = movieRepository.findAll(tapes);
        validateTapesToRent(tapesToRent);

        rental.setTapes(tapesToRent.stream().collect(Collectors.toSet()));
        tapesToRent.forEach(movie -> movie.setRental(rental));

        tapesToRent.stream()
                .map(tape -> new TapeRentalHistory().setRental(rental).setTape(tape))
                .forEach(tapeRentalHistoryRepository::save);
    }

    private void validateTapesToRent(List<MovieTape> tapesToRent) {
        List<Long> alreadyRentedTapes = tapesToRent.stream()
                .filter(movie -> Objects.nonNull(movie.getRental()))
                .map(MovieTape::getId)
                .collect(Collectors.toList());
        Preconditions.checkState(alreadyRentedTapes.isEmpty(),
                "Cannot rent tapes %s. They are already rented.",
                alreadyRentedTapes);
    }

    /**
     * Calculate bonus points for the tapes
     */
    public int calculateBonusPoints(Collection<MovieTape> tapes) {
        return tapes.stream()
                .map(tape -> tape.getMovieDefinition().getType())
                .mapToInt(MovieType::getBonusPoints)
                .sum();
    }

    @Transactional
    public Rental returnRental(long rentalId) {
        Rental rental = findRentalOrThrow(rentalId);
        rental.setReturnTime(LocalDateTime.now(clock));
        rental.setSurcharge(rentalPriceService.calculateSurcharge(
                rental.getExpectedReturnDate(),
                rental.getReturnTime().toLocalDate(),
                rental.getTapes()));
        rental.getTapes().forEach(tape -> tape.setRental(null));
        return rental;
    }

    @Transactional
    public Rental payRental(long rentalId, BigDecimal amount) {
        Rental rental = findRentalOrThrow(rentalId);
        BigDecimal amountToPay = rental.getAmountToPay();
        Preconditions.checkArgument(amountToPay.compareTo(amount) >= 0, "Trying to pay more than there is left to pay.");
        rental.setPaid(rental.getPaid().add(amount));
        rentalRepository.save(rental);
        return rental;
    }

    @Transactional
    public Rental findRentalOrThrow(long rentalId) {
        Rental rental = rentalRepository.findOne(rentalId);
        Preconditions.checkArgument(rental != null, "Did not find the rental.");
        return rental;
    }
}
