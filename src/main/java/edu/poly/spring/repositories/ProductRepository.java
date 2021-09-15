package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Product;
import edu.poly.spring.models.ProductType;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	Product findByName(String name);
	List<Product> findByProducttype(ProductType producttype);

}
