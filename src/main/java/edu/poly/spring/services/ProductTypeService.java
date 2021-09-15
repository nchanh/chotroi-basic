package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Product;
import edu.poly.spring.models.ProductType;

public interface ProductTypeService {

	void deleteAll();

	void deleteAll(Iterable<? extends ProductType> entities);

	void delete(ProductType entity);

	void deleteById(Integer id);

	long count();

	Iterable<ProductType> findAllById(Iterable<Integer> ids);

	Iterable<ProductType> findAll();

	boolean existsById(Integer id);

	Optional<ProductType> findById(Integer id);

	<S extends ProductType> Iterable<S> saveAll(Iterable<S> entities);

	<S extends ProductType> S save(S entity);

	
}
