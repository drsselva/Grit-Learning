package com.grit.learning.repository;


import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository  extends JpaRepository<Users,UUID> {

	Users findByUserName(String string);

	

}
