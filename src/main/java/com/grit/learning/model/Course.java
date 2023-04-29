package com.grit.learning.model;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name="course_id")
    private Integer courseId;
    @Column(name="course_name")
    private String courseName;
    @Column(name="course_duration")
    private String courseDuration;
    @Column(name="status")
    private String status;
    @Column(name="fees")
    private String courseFees;

    public Course(int courseId, String courseName, String courseDuration, String status, String courseFees) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.status = status;
        this.courseFees = courseFees;
    }
    public Course(){

    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourseFees(String courseFees) {
        this.courseFees = courseFees;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public String getStatus() {
        return status;
    }

    public String getCourseFees() {
        return courseFees;
    }
}
