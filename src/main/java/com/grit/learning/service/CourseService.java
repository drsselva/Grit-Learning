package com.grit.learning.service;

import com.grit.learning.exception.CourseNotFoundException;
import com.grit.learning.model.Course;
import com.grit.learning.model.Response;
import com.grit.learning.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    public List<Course> getAllCourses(){
        return courseRepo.findAll();
    }

    public Course getCourseById(int courseId) {
        try {
            return courseRepo.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Course Not found"));
        }catch (CourseNotFoundException ce){
            throw new CourseNotFoundException("Course Not Found");

        }
    }

    public List<Course> getCourseByName(String courseName){
        try{
            return courseRepo.findAll().stream().filter(course -> course.getCourseName().contains(courseName)).collect(Collectors.toList());
        }catch (CourseNotFoundException ce){
            throw new CourseNotFoundException("Course not with given course name");
        }
    }

    public Course updateCourse(Course course){
        try{
            Course updatedCourse = courseRepo.findById(course.getCourseId()).orElseThrow(() ->  new CourseNotFoundException("Provided Course Id not found"));
            updatedCourse.setCourseName(course.getCourseName());
            updatedCourse.setStatus(course.getStatus());
            updatedCourse.setCourseDuration(course.getCourseDuration());
            updatedCourse.setCourseFees(course.getCourseFees());
            return courseRepo.save(updatedCourse);
        }catch(CourseNotFoundException ce){
            throw new CourseNotFoundException("Provided Course Id not found");
        }
    }

    public Response deleteCourse(Course course){
            try{
                Response response =new Response();
                Course deleteCourse = courseRepo.findById(course.getCourseId()).orElseThrow(() -> new CourseNotFoundException("Course not found for delete"));
                if(deleteCourse !=null) {

                    courseRepo.delete(deleteCourse);
                    response.setMessage("Course deleted successfully");
                    response.setMessage("200");
                    response.setApiStatus("true");
                    return response;
                } else {
                    response.setMessage("Course not available for delete");
                    response.setStatusCode("200");
                    response.setApiStatus("false");
                    return response;
                }

            } catch(CourseNotFoundException ce){
                throw new CourseNotFoundException("Course not found for delete");
            }

    }

    public Response createCourse(Course course){
                try{
                    Response response =new Response();
                    if(course !=null){
                        courseRepo.save(course);
                        response.setMessage("Course added successfully");
                        response.setMessage("200");
                        response.setApiStatus("true");
                        return response;
                    } else {

                        response.setMessage("course detail is empty please add course details");
                        response.setMessage("200");
                        response.setApiStatus("False");
                        return response;
                    }
                } catch (CourseNotFoundException ex){
                    throw  new CourseNotFoundException("course detail not saved successfully");
                }

    }
}
