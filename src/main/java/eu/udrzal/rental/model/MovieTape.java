package eu.udrzal.rental.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class MovieTape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_definition_id", nullable = false)
    MovieDefinition movieDefinition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = true)
    Rental rental;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public MovieTape setId(Long id) {
        this.id = id;
        return this;
    }

    public MovieDefinition getMovieDefinition() {
        return movieDefinition;
    }

    public MovieTape setMovieDefinition(MovieDefinition movieDefinition) {
        this.movieDefinition = movieDefinition;
        return this;
    }

    public Rental getRental() {
        return rental;
    }

    public MovieTape setRental(Rental rental) {
        this.rental = rental;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MovieTape movieTape = (MovieTape) o;

        return new EqualsBuilder()
                .append(id, movieTape.id)
                .append(movieDefinition, movieTape.movieDefinition)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(movieDefinition)
                .toHashCode();
    }
}
