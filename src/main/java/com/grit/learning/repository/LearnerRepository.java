package com.grit.learning.repository;


import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Learner;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository  extends JpaRepository<Learner,UUID> {

}
