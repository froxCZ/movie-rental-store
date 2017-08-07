package eu.udrzal.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.udrzal.rental.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
