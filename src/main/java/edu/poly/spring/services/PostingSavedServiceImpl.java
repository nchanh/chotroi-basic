package edu.poly.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.PostingSaved;
import edu.poly.spring.repositories.PostingSavedRepository;

@Service
public class PostingSavedServiceImpl implements PostingSavedService{

	@Autowired
	PostingSavedRepository postingSavedRepository;

	@Override
	public PostingSaved findByAssessor(String assessor) {
		return postingSavedRepository.findByAssessor(assessor);
	}

	@Override
	public <S extends PostingSaved> S save(S entity) {
		return postingSavedRepository.save(entity);
	}

	@Override
	public <S extends PostingSaved> Iterable<S> saveAll(Iterable<S> entities) {
		return postingSavedRepository.saveAll(entities);
	}

	@Override
	public Optional<PostingSaved> findById(Integer id) {
		return postingSavedRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return postingSavedRepository.existsById(id);
	}

	@Override
	public Iterable<PostingSaved> findAll() {
		return postingSavedRepository.findAll();
	}

	@Override
	public Iterable<PostingSaved> findAllById(Iterable<Integer> ids) {
		return postingSavedRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return postingSavedRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		postingSavedRepository.deleteById(id);
	}

	@Override
	public void delete(PostingSaved entity) {
		postingSavedRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends PostingSaved> entities) {
		postingSavedRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		postingSavedRepository.deleteAll();
	}
	
	
}
