package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Product;
import edu.poly.spring.models.ProductType;

public interface ProductService {

	void deleteAll();

	void deleteAll(Iterable<? extends Product> entities);

	void delete(Product entity);

	void deleteById(Integer id);

	long count();

	Iterable<Product> findAllById(Iterable<Integer> ids);

	Iterable<Product> findAll();

	boolean existsById(Integer id);

	Optional<Product> findById(Integer id);

	<S extends Product> Iterable<S> saveAll(Iterable<S> entities);

	<S extends Product> S save(S entity);

	Product findByName(String name);

	List<Product> findByProducttype(ProductType productType);

}
