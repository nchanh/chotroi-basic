package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.poly.spring.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
	List<User> findByUsernameLikeOrderByFullname(String name);
	List<User> findUsersByFullnameContaining(String name);
	List<User> findUsersByStatus(String name);
}
