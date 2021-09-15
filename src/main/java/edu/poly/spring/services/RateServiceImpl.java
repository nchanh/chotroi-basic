package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.Rate;
import edu.poly.spring.models.Shop;
import edu.poly.spring.repositories.RateRepository;
import edu.poly.spring.repositories.ShopRepository;

@Service
public class RateServiceImpl implements RateService{

	@Autowired
	RateRepository rateRepository;

	@Override
	public List<Rate> findAllBySupplyUnitLike(String username) {
		return rateRepository.findAllBySupplyUnitLike(username);
	}

	@Override
	public Rate findBySupplyUnitAndAssessor(String usernameSupplyUnit, String usernameAssessor) {
		return rateRepository.findBySupplyUnitAndAssessor(usernameSupplyUnit, usernameAssessor);
	}

	@Override
	public <S extends Rate> S save(S entity) {
		return rateRepository.save(entity);
	}

	@Override
	public <S extends Rate> Iterable<S> saveAll(Iterable<S> entities) {
		return rateRepository.saveAll(entities);
	}

	@Override
	public Optional<Rate> findById(Integer id) {
		return rateRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return rateRepository.existsById(id);
	}

	@Override
	public Iterable<Rate> findAll() {
		return rateRepository.findAll();
	}

	@Override
	public Iterable<Rate> findAllById(Iterable<Integer> ids) {
		return rateRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return rateRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		rateRepository.deleteById(id);
	}

	@Override
	public void delete(Rate entity) {
		rateRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends Rate> entities) {
		rateRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		rateRepository.deleteAll();
	}
	
	
}
