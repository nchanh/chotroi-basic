package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.Shop;
import edu.poly.spring.repositories.ShopRepository;

@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	ShopRepository shopRepository;
	
	@Override
	public List<Shop> findShopsByStatus(String name) {
		return shopRepository.findShopsByStatus(name);
	}

	@Override
	public List<Shop> findSubjectsByShopnameContaining(String name) {
		return shopRepository.findSubjectsByShopnameContaining(name);
	}

	@Override
	public List<Shop> findByUsernameLikeOrderByUsername(String name) {
		return shopRepository.findByUsernameLikeOrderByUsername("%" + name + "%");
	}

	@Override
	public Shop findByUsername(String username) {
		return shopRepository.findByUsername(username);
	}

	@Override
	public Shop save(Shop entity) {
		return shopRepository.save(entity);
	}

	@Override
	public Iterable<Shop> saveAll(Iterable<Shop> entities) {
		return shopRepository.saveAll(entities);
	}

	@Override
	public Optional<Shop> findById(Integer id) {
		return shopRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return shopRepository.existsById(id);
	}

	@Override
	public Iterable<Shop> findAll() {
		return shopRepository.findAll();
	}

	@Override
	public Iterable<Shop> findAllById(Iterable<Integer> ids) {
		return shopRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return shopRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		shopRepository.deleteById(id);
	}

	@Override
	public void delete(Shop entity) {
		shopRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<Shop> entities) {
		shopRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		shopRepository.deleteAll();
	}
	
}
