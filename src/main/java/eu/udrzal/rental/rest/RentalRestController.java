package eu.udrzal.rental.rest;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.udrzal.rental.model.Rental;
import eu.udrzal.rental.repository.MovieRepository;
import eu.udrzal.rental.rest.request.CalculatePriceRequestDto;
import eu.udrzal.rental.rest.request.RentalRequestDto;
import eu.udrzal.rental.rest.response.PriceResponseDto;
import eu.udrzal.rental.rest.response.RentalResponseDto;
import eu.udrzal.rental.service.RentalPriceService;
import eu.udrzal.rental.service.RentalService;

import static eu.udrzal.rental.rest.RestConfig.APP_PATH;

@RestController
@RequestMapping(APP_PATH + "/rental")
public class RentalRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RentalPriceService rentalPriceService;
    @Autowired
    RentalService rentalService;

    @Autowired
    MovieRepository movieRepository;

    @RequestMapping(path = "calculate-price", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public PriceResponseDto calculatePrice(@Valid @RequestBody CalculatePriceRequestDto
                                                   calculatePriceRequestDto) {
        BigDecimal price = rentalPriceService.calculateRentalPrice(
                calculatePriceRequestDto.getFrom(),
                calculatePriceRequestDto.getTo(),
                movieRepository.findAll(calculatePriceRequestDto.getTapes()));

        return new PriceResponseDto()
                .setAmount(price);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDto createRental(@Valid @RequestBody RentalRequestDto rentalRequestDto) {
        Rental rental = rentalService.createRental(rentalRequestDto);
        logger.info("Created rental {}", rental.getId());
        return new RentalResponseDto(rental);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDto getRental(@PathVariable("id") long rentalId) {
        Rental rental = rentalService.findRentalOrThrow(rentalId);
        return new RentalResponseDto(rental);
    }

    @RequestMapping(path = "{id}/return", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public RentalResponseDto returnRental(@PathVariable("id") long rentalId) {
        Rental rental = rentalService.returnRental(rentalId);
        logger.info("Returned tapes for rental {}", rentalId);
        return new RentalResponseDto(rental);
    }

    @RequestMapping(path = "{id}/pay", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public RentalResponseDto payRental(@PathVariable("id") long rentalId, @RequestParam("amount") BigDecimal amount) {
        Rental rental = rentalService.payRental(rentalId, amount);
        logger.info("Paid {} of rental {}", amount, rentalId);
        return new RentalResponseDto(rental);
    }

}