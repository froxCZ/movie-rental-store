package eu.udrzal.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.udrzal.rental.model.TapeRentalHistory;

public interface TapeRentalHistoryRepository extends JpaRepository<TapeRentalHistory, Long> {
}
