package com.grit.learning.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grit.learning.model.CourseCategory;

@Repository
public interface CourseCategoryRepository  extends JpaRepository<CourseCategory,UUID> {

	CourseCategory findByName(String name);

	
	

}
