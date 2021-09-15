package edu.poly.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.ProductType;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {

}
