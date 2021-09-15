package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Shop;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Integer>{

	Shop findByUsername(String username);
	List<Shop> findByUsernameLikeOrderByUsername(String name);
	List<Shop> findSubjectsByShopnameContaining(String name);	
	List<Shop> findShopsByStatus(String name);
}
