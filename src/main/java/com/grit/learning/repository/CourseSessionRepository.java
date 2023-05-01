package com.grit.learning.repository;


import com.grit.learning.model.CourseSession;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSessionRepository  extends JpaRepository<CourseSession,UUID> {

}
