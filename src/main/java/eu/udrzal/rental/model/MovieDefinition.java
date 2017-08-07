package eu.udrzal.rental.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class MovieDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private MovieType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movieDefinition")
    private Set<MovieTape> movieTapes = new HashSet<MovieTape>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public MovieDefinition setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieType getType() {
        return type;
    }

    public MovieDefinition setType(MovieType type) {
        this.type = type;
        return this;
    }

    public Set<MovieTape> getMovieTapes() {
        return movieTapes;
    }

    public MovieDefinition setMovieTapes(Set<MovieTape> movieTapes) {
        this.movieTapes = movieTapes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MovieDefinition that = (MovieDefinition) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(type)
                .toHashCode();
    }
}
