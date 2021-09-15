package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.poly.spring.models.Rate;
import edu.poly.spring.models.Shop;

public interface RateRepository extends CrudRepository<Rate, Integer> {

	List<Rate> findAllBySupplyUnitLike(String username);
	
	Rate findBySupplyUnitAndAssessor(String usernameSupplyUnit, String usernameAssessor);
}
