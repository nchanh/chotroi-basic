package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;

@Repository
public interface PostingRepository extends CrudRepository<Posting, Integer> {
	Posting findTopByOrderByIdDesc();

	List<Posting> findPostingsByStatus(String name);

	List<Posting> findPostingsByProductId(Integer id);

	List<Posting> findByUser(User user);

	List<Posting> findByShop(Shop shop);

	List<Posting> findTop16ByOrderByDateDesc();

	List<Posting> findPostingsByStatusAndUser(String name, User user);
	
	List<Posting> findPostingsByStatusAndShop(String name, Shop shop);

}
