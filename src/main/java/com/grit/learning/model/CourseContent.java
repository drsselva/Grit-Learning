package com.grit.learning.model;

import javax.persistence.*;

@Entity
@Table(name="course_content")
public class CourseContent {
    @Id
    @Column(name="course_content_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String courseContentId;

    @Column(name="user_email_id")
    private String userEmailId;

    @Column(name="course_name")
    private String courseName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name="content_location")
    private String contentLocation;

    public String getCourseContentId() {
        return courseContentId;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentLocation() {
        return contentLocation;
    }

    public void setCourseContentId(String courseContentId) {
        this.courseContentId = courseContentId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentLocation(String contentLocation) {
        this.contentLocation = contentLocation;
    }

    public CourseContent(String courseContentId, String userEmailId, String courseName, String contentType, String contentLocation) {
        this.courseContentId = courseContentId;
        this.userEmailId = userEmailId;
        this.courseName = courseName;
        this.contentType = contentType;
        this.contentLocation = contentLocation;
    }

    public CourseContent(){}
}
