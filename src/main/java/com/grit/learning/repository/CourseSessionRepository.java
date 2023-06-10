package com.grit.learning.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grit.learning.model.CourseSession;

public interface CourseSessionRepository  extends JpaRepository<CourseSession,UUID> {
	
	CourseSession findByEducatorIdAndCourseTitle(String educatorId, String title);

	List<CourseSession> findByEducatorIdOrderByScheduledTimeDesc(String educatorId);

	List<CourseSession> findAllByOrderByScheduledTimeDesc();
}
