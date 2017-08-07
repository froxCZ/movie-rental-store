package eu.udrzal.rental.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.udrzal.rental.model.MovieTape;
import eu.udrzal.rental.model.Rental;
import eu.udrzal.rental.repository.MovieRepository;
import eu.udrzal.rental.repository.RentalRepository;

import static com.google.common.collect.Sets.newHashSet;
import static eu.udrzal.rental.service.TestData.HP2;
import static eu.udrzal.rental.service.TestData.HP5;
import static eu.udrzal.rental.service.TestData.HP7;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {

    public static final long RENTAL_ID = 1;
    Set<MovieTape> TAPES_1 = newHashSet(HP2, HP5);

    @InjectMocks
    RentalService rentalService;

    @Mock
    RentalPriceService rentalPriceService;

    @Mock
    RentalRepository rentalRepository;

    @Mock
    MovieRepository movieRepository;

    @Mock
    Clock clock;

    private Rental BASE_RENTAL = new Rental().setId(RENTAL_ID);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        when(rentalRepository.findOne(RENTAL_ID)).thenReturn(BASE_RENTAL);
        Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    public void createRental() throws Exception {
        /*
        TODO: Had no time. Quite demanding test that requires a lot of mocking. It's optimistically tested in
        integration test
         */
    }

    @Test
    public void calculateBonusPoints() throws Exception {
        assertThat(rentalService.calculateBonusPoints(asList(HP2, HP5)), is(2));
        assertThat(rentalService.calculateBonusPoints(asList(HP2, HP7)), is(3));
    }

    @Test
    public void returnRental() throws Exception {
        BASE_RENTAL.setTapes(TAPES_1);
        rentalService.returnRental(RENTAL_ID);
        verify(rentalPriceService).calculateSurcharge(anyObject(), anyObject(), eq(TAPES_1));
        //TODO better mocks and verifies.
    }

    @Test
    public void payRental() throws Exception {
        BASE_RENTAL.setPaid(new BigDecimal("0"));
        BASE_RENTAL.setNormalPrice(new BigDecimal("25"));
        assertThat(rentalService.payRental(1, new BigDecimal("20")).getAmountToPay(), is(new BigDecimal("5")));
        verify(rentalRepository).save(BASE_RENTAL);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Trying to pay more than there is left to pay.");
        assertThat(rentalService.payRental(1, new BigDecimal("20")).getAmountToPay(), is(new BigDecimal("5")));


    }

}