package com.grit.learning.controller;

import com.grit.learning.model.Course;
import com.grit.learning.model.Response;
import com.grit.learning.model.Token;
import com.grit.learning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/getAllCourse")
    public ResponseEntity<List<Course>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
    }

    @GetMapping("/getCourseById/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable int courseId){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(courseId));
    }

    @PutMapping("/updateCourse")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(course));
    }

    @DeleteMapping("/deleteCourse")
    public ResponseEntity<Response> deleteCourse(@RequestBody Course course){

        return ResponseEntity.status(HttpStatus.OK).body(courseService.deleteCourse(course));
    }

    @PostMapping("/createCourse")
    public ResponseEntity<Response> createCourse(@RequestBody Course course){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.createCourse(course));
    }


}
