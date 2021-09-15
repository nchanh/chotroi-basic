package edu.poly.spring.services;

import java.util.Optional;

import edu.poly.spring.models.Admin;

public interface AdminService {

	void deleteAll();

	void deleteAll(Iterable<? extends Admin> entities);

	void delete(Admin entity);

	void deleteById(Integer id);

	long count();

	Iterable<Admin> findAllById(Iterable<Integer> ids);

	Iterable<Admin> findAll();

	boolean existsById(Integer id);

	Optional<Admin> findById(Integer id);

	<S extends Admin> Iterable<S> saveAll(Iterable<S> entities);

	<S extends Admin> S save(S entity);

	Admin findByUsername(String username);

}
