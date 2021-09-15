package edu.poly.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	Admin findByUsername(String username);
	
}
