package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.repositories.PostingDetailRepository;
import edu.poly.spring.repositories.PostingRepository;

@Service
public class PostingDetailServiceImpl implements PostingDetailService {
	
	@Autowired
	PostingDetailRepository postingDetailRepository;
	
	@Autowired
	PostingRepository postingRepository;

	@Override
	public PostingDetail findPostingDetailByPostingId(Integer id) {
		return postingDetailRepository.findPostingDetailByPostingId(id);
	}

	@Override
	public List<PostingDetail> findPostingDetailByTitleContaining(String tile) {
		return postingDetailRepository.findPostingDetailByTitleContaining(tile);
	}

	@Override
	public List<Posting> findAllPostings() {
		return (List<Posting>) postingRepository.findAll();
	}

	@Override
	public PostingDetail save(PostingDetail entity) {
		return postingDetailRepository.save(entity);
	}

	@Override
	public Iterable<PostingDetail> saveAll(Iterable<PostingDetail> entities) {
		return postingDetailRepository.saveAll(entities);
	}

	@Override
	public Optional<PostingDetail> findById(Integer id) {
		return postingDetailRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return postingDetailRepository.existsById(id);
	}

	@Override
	public Iterable<PostingDetail> findAll() {
		return postingDetailRepository.findAll();
	}

	@Override
	public Iterable<PostingDetail> findAllById(Iterable<Integer> ids) {
		return postingDetailRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return postingDetailRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		postingDetailRepository.deleteById(id);
	}

	@Override
	public void delete(PostingDetail entity) {
		postingDetailRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<PostingDetail> entities) {
		postingDetailRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		postingDetailRepository.deleteAll();
	}

}
