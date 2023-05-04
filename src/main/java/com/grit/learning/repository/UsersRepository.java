package com.grit.learning.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grit.learning.model.Users;

public interface UsersRepository  extends JpaRepository<Users,UUID> {

	Users findByEmailId(String emailId);

	

}
