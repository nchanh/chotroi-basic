package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import edu.poly.spring.models.Shop;
import edu.poly.spring.repositories.ShopRepository;

public interface ShopService {

	void deleteAll();

	void deleteAll(Iterable<Shop> entities);

	void delete(Shop entity);

	void deleteById(Integer id);

	long count();

	Iterable<Shop> findAllById(Iterable<Integer> ids);

	Iterable<Shop> findAll();

	boolean existsById(Integer id);

	Optional<Shop> findById(Integer id);

	Iterable<Shop> saveAll(Iterable<Shop> entities);

	Shop save(Shop entity);

	Shop findByUsername(String username);

	List<Shop> findByUsernameLikeOrderByUsername(String name);

	List<Shop> findSubjectsByShopnameContaining(String name);

	List<Shop> findShopsByStatus(String name);

}
