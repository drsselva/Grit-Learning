package com.grit.learning.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grit.learning.model.CourseSession;

public interface CourseSessionRepository  extends JpaRepository<CourseSession,UUID> {
	
	CourseSession findByEducatorIdAndCourseTitle(UUID educatorId, String title);
<<<<<<< HEAD


	List<CourseSession> findByEducatorIdOrderByScheduledTimeDesc(String educatorId);

	List<CourseSession> findAllByOrderByScheduledTimeDesc();
=======
>>>>>>> e0c7355ee107f30415092f7d17eb6d0c45d28e8f

	List<CourseSession> findByEducatorId(String educatorId);

	List<CourseSession> findByCourseCategoryId(UUID id);
<<<<<<< HEAD

=======
>>>>>>> e0c7355ee107f30415092f7d17eb6d0c45d28e8f
}
