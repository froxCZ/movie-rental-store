package eu.udrzal.rental.integration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.udrzal.rental.rest.request.CalculatePriceRequestDto;
import eu.udrzal.rental.rest.request.RentalRequestDto;
import eu.udrzal.rental.rest.response.PriceResponseDto;
import eu.udrzal.rental.rest.response.RentalResponseDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class RentalAppIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test01_rentPayReturn() {
        RentalRequestDto rentalRequestDto = new RentalRequestDto();
        rentalRequestDto.setTapes(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));
        rentalRequestDto.setCustomerId(1L);
        rentalRequestDto.setTo(LocalDate.parse("2017-01-05"));
        ResponseEntity<RentalResponseDto> response = sendRentalRequest(rentalRequestDto);
        assertThat(response.getStatusCode().value(), is(HttpStatus.CREATED.value()));
        RentalResponseDto responseDto = response.getBody();
        assertThat(responseDto.getNormalPrice(), is(new BigDecimal("90")));
        assertThat(responseDto.getBonusPoints(), is(3));
        assertThat(responseDto.getAmountToPay(), is(new BigDecimal("90")));

        response = sendRentalRequest(rentalRequestDto);// renting already rented items
        assertThat(response.getStatusCode().value(), is(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        pay(1, "90");

        responseDto = getRental(1).getBody();
        assertThat(responseDto.getAmountToPay(), is(new BigDecimal("0.00")));
        assertThat(responseDto.getReturnTime(), is(nullValue()));

        responseDto = returnRental(1).getBody();
        assertThat(responseDto.getAmountToPay(), is(new BigDecimal("0.00")));
        assertThat(responseDto.getReturnTime(), is(notNullValue()));

    }

    @Test
    public void test02_rentInvalidDate() {
        RentalRequestDto rentalRequestDto = new RentalRequestDto();
        rentalRequestDto.setTapes(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));
        rentalRequestDto.setCustomerId(1L);
        rentalRequestDto.setTo(LocalDate.parse("2016-12-30"));
        ResponseEntity<RentalResponseDto> response = sendRentalRequest(rentalRequestDto);
        assertThat(response.getStatusCode().value(), is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    public void test03_calculatePrice() {
        CalculatePriceRequestDto calculatePriceRequestDto = new CalculatePriceRequestDto();
        calculatePriceRequestDto.setFrom(LocalDate.parse("2017-01-01"));
        calculatePriceRequestDto.setTo(LocalDate.parse("2017-01-05"));
        calculatePriceRequestDto.setTapes(Stream.of(1L,4L,13L).collect(Collectors.toList()));//OLD, REG, NEW
        PriceResponseDto response = restTemplate
                .postForEntity("/api/rental/calculate-price", calculatePriceRequestDto, PriceResponseDto.class)
                .getBody();
        assertThat(response.getAmount(),is(new BigDecimal("320")));
    }

    private ResponseEntity<RentalResponseDto> sendRentalRequest(RentalRequestDto rentalRequestDto) {
        return restTemplate.postForEntity("/api/rental", rentalRequestDto, RentalResponseDto.class);
    }

    private ResponseEntity<RentalResponseDto> getRental(long rentalId) {
        return restTemplate.getForEntity("/api/rental/" + rentalId, RentalResponseDto.class);
    }

    private ResponseEntity<RentalResponseDto> returnRental(long rentalId) {
        return restTemplate.postForEntity("/api/rental/" + rentalId + "/return", null, RentalResponseDto.class);
    }

    private PriceResponseDto pay(long rentalId, String amount) {
        ResponseEntity<PriceResponseDto> response =
                restTemplate.postForEntity("/api/rental/{rentalId}/pay?amount={amount}",
                        null,
                        PriceResponseDto.class,
                        rentalId,
                        amount);

        assertThat(response.getStatusCode().value(), is(HttpStatus.OK.value()));
        return response.getBody();

    }


}