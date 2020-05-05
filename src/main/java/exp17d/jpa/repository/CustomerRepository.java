package exp17d.jpa.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import exp17d.jpa.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
    List<Customer> findByLastName(String lastName);
} 