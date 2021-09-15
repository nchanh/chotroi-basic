package edu.poly.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.ChatBox;
import edu.poly.spring.repositories.ChatBoxRepository;

@Service
public class ChatBoxServiceImpl implements ChatBoxService{
@Autowired
private ChatBoxRepository chatBoxRepository;

@Override
public ChatBox save(ChatBox entity) {
	return chatBoxRepository.save(entity);
}

@Override
public Iterable<ChatBox> saveAll(Iterable<ChatBox> entities) {
	return chatBoxRepository.saveAll(entities);
}

@Override
public Optional<ChatBox> findById(Integer id) {
	return chatBoxRepository.findById(id);
}

@Override
public boolean existsById(Integer id) {
	return chatBoxRepository.existsById(id);
}

@Override
public Iterable<ChatBox> findAll() {
	return chatBoxRepository.findAll();
}

@Override
public Iterable<ChatBox> findAllById(Iterable<Integer> ids) {
	return chatBoxRepository.findAllById(ids);
}

@Override
public long count() {
	return chatBoxRepository.count();
}

@Override
public void deleteById(Integer id) {
	chatBoxRepository.deleteById(id);
}

@Override
public void delete(ChatBox entity) {
	chatBoxRepository.delete(entity);
}

@Override
public void deleteAll(Iterable<ChatBox> entities) {
	chatBoxRepository.deleteAll(entities);
}

@Override
public void deleteAll() {
	chatBoxRepository.deleteAll();
}

}
