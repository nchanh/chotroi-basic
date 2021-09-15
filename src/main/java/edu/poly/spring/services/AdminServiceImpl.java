package edu.poly.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.poly.spring.models.Admin;
import edu.poly.spring.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin findByUsername(String username) {
		return adminRepository.findByUsername(username);
	}

	@Override
	public <S extends Admin> S save(S entity) {
		return adminRepository.save(entity);
	}

	@Override
	public <S extends Admin> Iterable<S> saveAll(Iterable<S> entities) {
		return adminRepository.saveAll(entities);
	}

	@Override
	public Optional<Admin> findById(Integer id) {
		return adminRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return adminRepository.existsById(id);
	}

	@Override
	public Iterable<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Iterable<Admin> findAllById(Iterable<Integer> ids) {
		return adminRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return adminRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		adminRepository.deleteById(id);
	}

	@Override
	public void delete(Admin entity) {
		adminRepository.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends Admin> entities) {
		adminRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		adminRepository.deleteAll();
	}

}
