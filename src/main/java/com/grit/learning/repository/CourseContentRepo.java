package com.grit.learning.repository;

import com.grit.learning.model.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseContentRepo  extends JpaRepository<CourseContent,Integer> {

}
