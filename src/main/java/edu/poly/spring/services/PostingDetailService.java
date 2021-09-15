package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;

public interface PostingDetailService {

	void deleteAll();

	void deleteAll(Iterable<PostingDetail> entities);

	void delete(PostingDetail entity);

	void deleteById(Integer id);

	long count();

	Iterable<PostingDetail> findAllById(Iterable<Integer> ids);

	Iterable<PostingDetail> findAll();

	boolean existsById(Integer id);

	Optional<PostingDetail> findById(Integer id);

	Iterable<PostingDetail> saveAll(Iterable<PostingDetail> entities);

	PostingDetail save(PostingDetail entity);

	List<Posting> findAllPostings();

	List<PostingDetail> findPostingDetailByTitleContaining(String tile);

	PostingDetail findPostingDetailByPostingId(Integer id);

}
