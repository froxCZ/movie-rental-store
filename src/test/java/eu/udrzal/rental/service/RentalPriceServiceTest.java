package eu.udrzal.rental.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import eu.udrzal.rental.model.MovieDefinition;
import eu.udrzal.rental.model.MovieTape;
import eu.udrzal.rental.model.MovieType;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RentalPriceServiceTest {

    private static final MovieTape HP2 = new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP2")
            .setType(MovieType.OLD));
    private static final MovieTape HP5 =
            new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP5").setType(MovieType.REGULAR));
    private static final MovieTape HP7 = new MovieTape().setMovieDefinition(new MovieDefinition().setTitle("HP7")
            .setType(MovieType.NEW));

    @InjectMocks
    RentalPriceService rentalPriceService;

    @Test
    public void calculateRentalPriceSingle() throws Exception {
        List<MovieTape> singleOld = singletonList(HP2);
        List<MovieTape> singleRegular = singletonList(HP5);
        List<MovieTape> singleNew = singletonList(HP7);
        assertRentalPrice("2017-01-01", "2017-01-04", singleOld, "30");
        assertRentalPrice("2017-01-01", "2017-01-01", singleOld, "30");
        assertRentalPrice("2017-01-01", "2017-01-10", singleOld, "180");

        assertRentalPrice("2017-01-01", "2017-01-01", singleRegular, "30");
        assertRentalPrice("2017-01-01", "2017-01-04", singleRegular, "60");

        assertRentalPrice("2017-01-01", "2017-01-01", singleNew, "40");
        assertRentalPrice("2017-01-01", "2017-01-02", singleNew, "80");
    }

    @Test
    public void calculateRentalPriceMultiple() throws Exception {
        List<MovieTape> multiple = asList(HP2, HP5, HP7);

        assertRentalPrice("2017-01-01", "2017-01-01", multiple, "100");
        assertRentalPrice("2017-01-01", "2017-01-02", multiple, "140");
        assertRentalPrice("2017-01-01", "2017-01-05", multiple, "320");
        assertRentalPrice("2017-01-01", "2017-01-06", multiple, "420");
    }

    private void assertRentalPrice(String from, String to, List<MovieTape> tapes, String expected) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        assertThat(rentalPriceService.calculateRentalPrice(fromDate, toDate, tapes), is(new BigDecimal(expected)));
    }

    @Test
    public void calculateSurcharge() throws Exception {
        List<MovieTape> singleOld = singletonList(HP2);
        List<MovieTape> twoOld = asList(HP2,HP2);

        assertThat(rentalPriceService.calculateSurcharge(LocalDate.parse("2017-01-03"),
                LocalDate.parse("2017-01-08"), singleOld),
                is (new BigDecimal("150")));

        assertThat(rentalPriceService.calculateSurcharge(LocalDate.parse("2017-01-03"),
                LocalDate.parse("2017-01-08"), twoOld),
                is(new BigDecimal("300")));

        assertThat(rentalPriceService.calculateSurcharge(LocalDate.parse("2017-01-10"),
                LocalDate.parse("2017-01-08"), singleOld),
                is (new BigDecimal("0")));

    }

}