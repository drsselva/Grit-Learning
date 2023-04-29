package com.grit.learning.service;

import com.grit.learning.model.CourseContent;
import com.grit.learning.model.Response;
import com.grit.learning.repository.CourseContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseContentService {

    @Autowired
    private CourseContentRepo courseContentRepo;

    public List<CourseContent> getAllCourseContent(){
        List<CourseContent> courseContentList = courseContentRepo.findAll();
        return courseContentList;
    }

    public List<CourseContent> getCourseContentByEmailId(String email){
        List<CourseContent>courseContentList = courseContentRepo.findAll().stream().filter(user -> user.getUserEmailId().equals(email)).collect(Collectors.toList());
        return courseContentList;
    }

    public List<CourseContent> getCourseContentByCourse(String course){
        List<CourseContent>courseContentList = courseContentRepo.findAll().stream().filter(user -> user.getCourseName().equals(course)).collect(Collectors.toList());
        return courseContentList;
    }
    public List<CourseContent> getCourseContentByEmailAndCourse(String email,String course){
        List<CourseContent>courseContentList =
                courseContentRepo.findAll().stream().filter(courseContent -> (courseContent.getUserEmailId().equals(email)) && courseContent.getCourseName().equals(course)).collect(Collectors.toList());
        return courseContentList;
    }

}
