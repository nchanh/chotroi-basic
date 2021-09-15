package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Rate;
import edu.poly.spring.models.Shop;

public interface RateService {

	void deleteAll();

	void deleteAll(Iterable<? extends Rate> entities);

	void delete(Rate entity);

	void deleteById(Integer id);

	long count();

	Iterable<Rate> findAllById(Iterable<Integer> ids);

	Iterable<Rate> findAll();

	boolean existsById(Integer id);

	Optional<Rate> findById(Integer id);

	<S extends Rate> Iterable<S> saveAll(Iterable<S> entities);

	<S extends Rate> S save(S entity);

	Rate findBySupplyUnitAndAssessor(String usernameSupplyUnit, String usernameAssessor);

	List<Rate> findAllBySupplyUnitLike(String username);

}
