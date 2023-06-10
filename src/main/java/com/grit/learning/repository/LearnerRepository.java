package com.grit.learning.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grit.learning.model.Learner;

public interface LearnerRepository  extends JpaRepository<Learner,UUID> {

//	@Query(value = "SELECT * FROM learner WHERE "
//			+ "COURSE_ID = :courseId", nativeQuery = true)
//	public List<Learner> findByCourseId(@Param("courseId") UUID courseId);
	
	public List<Learner> findByCourseId(UUID courseId);
}
