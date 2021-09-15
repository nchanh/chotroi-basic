package edu.poly.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.ChatBox;

@Repository
public interface ChatBoxRepository extends CrudRepository<ChatBox, Integer> {

}
