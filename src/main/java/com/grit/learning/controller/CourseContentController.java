package com.grit.learning.controller;

import com.grit.learning.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    @GetMapping("/getAllCourseContent")
    public ResponseEntity getAllCourseContent(){
        return ResponseEntity.status(HttpStatus.OK).body(courseContentService.getAllCourseContent());
    }

    @GetMapping("/getCourseContentByEmail/{email}")
    public ResponseEntity getCourseContentByEmailId(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(courseContentService.getCourseContentByEmailId(email));
    }

    @GetMapping("/getCourseContentByCourse/{course}")
    public ResponseEntity getCourseContentByCourseName(@PathVariable String course){
        return ResponseEntity.status(HttpStatus.OK).body(courseContentService.getCourseContentByCourse(course));
    }

    @GetMapping("/getCourseContentByEmailAndCourse/{email}/{course}")
    public ResponseEntity getCourseContentByEmailAndCourse(@PathVariable String email,@PathVariable String course){
        return ResponseEntity.status(HttpStatus.OK).body(courseContentService.getCourseContentByEmailAndCourse(email,course));
    }



}
