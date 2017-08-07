package eu.udrzal.rental.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TapeRentalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tape_id")
    private MovieTape tape;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieTape getTape() {
        return tape;
    }

    public TapeRentalHistory setTape(MovieTape tape) {
        this.tape = tape;
        return this;
    }

    public Rental getRental() {
        return rental;
    }

    public TapeRentalHistory setRental(Rental rental) {
        this.rental = rental;
        return this;
    }
}
