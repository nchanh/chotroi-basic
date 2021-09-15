package edu.poly.spring.services;

import java.util.Optional;

import edu.poly.spring.models.ChatBox;

public interface ChatBoxService {

	void deleteAll();

	void deleteAll(Iterable<ChatBox> entities);

	void delete(ChatBox entity);

	void deleteById(Integer id);

	long count();

	Iterable<ChatBox> findAllById(Iterable<Integer> ids);

	Iterable<ChatBox> findAll();

	boolean existsById(Integer id);

	Optional<ChatBox> findById(Integer id);

	Iterable<ChatBox> saveAll(Iterable<ChatBox> entities);

	ChatBox save(ChatBox entity);

}
