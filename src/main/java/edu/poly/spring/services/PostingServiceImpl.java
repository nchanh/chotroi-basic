package edu.poly.spring.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.repositories.PostingRepository;

@Service
public class PostingServiceImpl implements PostingService {
	@Autowired
	private PostingRepository postingRepository;

	@Override
	public List<Posting> findPostingsByStatusAndUser(String name, User user) {
		return postingRepository.findPostingsByStatusAndUser(name, user);
	}

	@Override
	public List<Posting> findPostingsByStatusAndShop(String name, Shop shop) {
		return postingRepository.findPostingsByStatusAndShop(name, shop);
	}

	@Override
	public List<Posting> findTop16ByOrderByDateDesc() {
		return postingRepository.findTop16ByOrderByDateDesc();
	}

	@Override
	public List<Posting> findByUser(User user) {
		return postingRepository.findByUser(user);
	}

	@Override
	public List<Posting> findByShop(Shop shop) {
		return postingRepository.findByShop(shop);
	}

	@Override
	public List<Posting> findPostingsByProductId(Integer id) {
		return postingRepository.findPostingsByProductId(id);
	}

	@Override
	public List<Posting> findPostingsByStatus(String name) {
		return postingRepository.findPostingsByStatus(name);
	}

	@Override
	public Posting findTopByOrderByIdDesc() {
		return postingRepository.findTopByOrderByIdDesc();
	}

	@Override
	public Posting save(Posting entity) {
		return postingRepository.save(entity);
	}

	@Override
	public Iterable<Posting> saveAll(Iterable<Posting> entities) {
		return postingRepository.saveAll(entities);
	}

	@Override
	public Optional<Posting> findById(Integer id) {
		return postingRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return postingRepository.existsById(id);
	}

	@Override
	public Iterable<Posting> findAll() {
		return postingRepository.findAll();
	}

	@Override
	public Iterable<Posting> findAllById(Iterable<Integer> ids) {
		return postingRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return postingRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		postingRepository.deleteById(id);
	}

	@Override
	public void delete(Posting entity) {
		postingRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<Posting> entities) {
		postingRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		postingRepository.deleteAll();
	}

}
