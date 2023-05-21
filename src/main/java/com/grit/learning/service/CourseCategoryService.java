package com.grit.learning.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grit.learning.model.Users;
import com.grit.learning.repository.CourseCategoryRepository;
import com.grit.learning.dto.CourseCategoryDTO;
import com.grit.learning.model.CourseCategory;


@Service
public class CourseCategoryService {

	@Autowired
	private CourseCategoryRepository courseCategoryRepository;

	public void saveOrUpdate(CourseCategory courseCategory) {
		courseCategoryRepository.save(courseCategory);
	}

	public List<CourseCategory> findAll() {

		return courseCategoryRepository.findAll();

	}

	public CourseCategory findByName(String name) {
		
		return courseCategoryRepository.findByName(name);
	}

	public Optional findById(UUID id) {
		return courseCategoryRepository.findById(id);
	}

	public void deleteById(UUID id) {
		courseCategoryRepository.deleteById(id);
		
	}

	
	
	
}
