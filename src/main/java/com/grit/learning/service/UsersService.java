package com.grit.learning.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grit.learning.model.Users;
import com.grit.learning.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	public void saveOrUpdate(Users user) {
		usersRepository.save(user);
	}

	public List<Users> findAll() {

		return usersRepository.findAll();

	}

	public Users findById(UUID id) {
		Optional<Users> users = usersRepository.findById(id);
		
		if(users.isPresent()) {
			return users.get();
		} else {
			return null;
		}
	}

	public void Users(UUID id) {
		usersRepository.deleteById(id);
	}
	
	public Users findByEmailId(String email) {
		return usersRepository.findByEmailId(email);
	}
}
