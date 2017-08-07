package eu.udrzal.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.udrzal.rental.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
