package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;

@Repository
public interface PostingDetailRepository extends CrudRepository<PostingDetail, Integer> {
	List<PostingDetail> findPostingDetailByTitleContaining(String tile);
	PostingDetail findPostingDetailByPostingId(Integer id);
}
