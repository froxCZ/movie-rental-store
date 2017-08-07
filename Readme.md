### How to run
- Set mysql database connection information in `src/main/resources/application-dev.properties`
- For integration test, set mysql database connection information in `src/test/resources/test.properties`
- run with `mvn spring-boot:run`
- test with `mvn test`
- default port is `8080`

### Notes
- The app handles times in local timezone including storage to database
- Using `@Version` to prevent race condition updates on Customer(bonus_points), MovieTape(rental_id), Rental(paid_amount). Exceptions are not handled (but should be) even though they are very unlikely.
- Payments are just added to rental.paid_amount. Would be better to have a special entity `Payment` to also tract time of the payment.
- Rental response now contains only currently rented out tapes. Once returned, they are not available via API anymore. However, history of tape rentals is kept in `TapeRentalHistory` entity.
- Not mapping exceptions on reasonable status codes.
- Schema is now recreated on restart due to `spring.jpa.hibernate.ddl-auto=create`
- Did not implement any management of movies, tapes etc.
- Mapping would be nicer with Orika Mapper.


### API

- `POST api/rental` Creates new rental. `tapes` is a list of tape IDs.
request:
```
{
   	"to":"2017-12-07",
   	"tapes":[1],
   	"customerId":1
}
```
   
- `GET api/rental/{id}` Returns information about rental.

response:
```
{
       "id": 1,
       "rentalTime": "2017-08-08T01:33:05.687",
       "expectedReturnDate": "2017-12-07",
       "returnTime": null,
       "normalPrice": 3540,
       "surcharge": 0,
       "paid": 0,
       "bonusPoints": 1,
       "customerId": 1,
       "tapes": [
           1
       ],
       "priceWithSurcharge": 3540,
       "amountToPay": 3540
   }
```

- `POST api/rental/{id}/return` Endpoint for returning the Rental
- `POST api/rental/{id}/pay?amount={amount}` Endpoint for paying for rental
- `POST api/rental/calculate-price` Endpoint for getting price for a rental.

request:
```
{
    "from": "2017-12-07",
    "to": "2017-12-15",
    "tapes":[1,2.3]
}

```


   
   
   
   
   
   
                     
                 
