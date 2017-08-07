package eu.udrzal.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.udrzal.rental.model.MovieTape;

public interface MovieRepository extends JpaRepository<MovieTape, Long> {
}
