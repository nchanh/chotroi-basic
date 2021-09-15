package edu.poly.spring.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;

public interface PostingService {

	void deleteAll();

	void deleteAll(Iterable<Posting> entities);

	void delete(Posting entity);

	void deleteById(Integer id);

	long count();

	Iterable<Posting> findAllById(Iterable<Integer> ids);

	Iterable<Posting> findAll();

	boolean existsById(Integer id);

	Optional<Posting> findById(Integer id);

	Iterable<Posting> saveAll(Iterable<Posting> entities);

	Posting save(Posting entity);

	Posting findTopByOrderByIdDesc();

	List<Posting> findPostingsByStatus(String name);

	List<Posting> findPostingsByProductId(Integer id);

	List<Posting> findByShop(Shop shop);

	List<Posting> findByUser(User user);

	List<Posting> findTop16ByOrderByDateDesc();

	List<Posting> findPostingsByStatusAndShop(String name, Shop shop);

	List<Posting> findPostingsByStatusAndUser(String name, User user);

}
