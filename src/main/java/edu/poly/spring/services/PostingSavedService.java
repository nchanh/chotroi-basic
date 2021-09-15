package edu.poly.spring.services;

import java.util.Optional;

import edu.poly.spring.models.PostingSaved;

public interface PostingSavedService {

	void deleteAll();

	void deleteAll(Iterable<? extends PostingSaved> entities);

	void delete(PostingSaved entity);

	void deleteById(Integer id);

	long count();

	Iterable<PostingSaved> findAllById(Iterable<Integer> ids);

	Iterable<PostingSaved> findAll();

	boolean existsById(Integer id);

	Optional<PostingSaved> findById(Integer id);

	<S extends PostingSaved> Iterable<S> saveAll(Iterable<S> entities);

	<S extends PostingSaved> S save(S entity);

	PostingSaved findByAssessor(String assessor);

}
