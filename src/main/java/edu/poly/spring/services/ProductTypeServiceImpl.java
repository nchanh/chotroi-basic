package edu.poly.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.ProductType;
import edu.poly.spring.repositories.ProductTypeRepository;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{

	@Autowired
	ProductTypeRepository productTypeRepository;

	@Override
	public <S extends ProductType> S save(S entity) {
		return productTypeRepository.save(entity);
	}

	@Override
	public <S extends ProductType> Iterable<S> saveAll(Iterable<S> entities) {
		return productTypeRepository.saveAll(entities);
	}

	@Override
	public Optional<ProductType> findById(Integer id) {
		return productTypeRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return productTypeRepository.existsById(id);
	}

	@Override
	public Iterable<ProductType> findAll() {
		return productTypeRepository.findAll();
	}

	@Override
	public Iterable<ProductType> findAllById(Iterable<Integer> ids) {
		return productTypeRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return productTypeRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productTypeRepository.deleteById(id);
	}

	@Override
	public void delete(ProductType entity) {
		productTypeRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends ProductType> entities) {
		productTypeRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		productTypeRepository.deleteAll();
	}

		
}
