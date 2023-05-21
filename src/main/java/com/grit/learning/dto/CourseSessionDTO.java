package com.grit.learning.dto;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)  
public class CourseSessionDTO {
	private UUID id;
	private String courseTitle;
    private String description;
    private Timestamp scheduledTime;
    private UsersDTO educator;
    private MultipartFile videoDoument;
    private MultipartFile pdfDocument;
    private String courseImage;
    private String videoDoumentName;
    private String pdfDocumentName;
    private String courseImageName;
    private String bucketUrl;
    private CourseCategoryDTO courseCategoryDTO;
    
  
    
	public CourseCategoryDTO getCourseCategoryDTO() {
		return courseCategoryDTO;
	}
	public void setCourseCategoryDTO(CourseCategoryDTO courseCategoryDTO) {
		this.courseCategoryDTO = courseCategoryDTO;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	public MultipartFile getVideoDoument() {
		return videoDoument;
	}
	public void setVideoDoument(MultipartFile videoDoument) {
		this.videoDoument = videoDoument;
	}
	public MultipartFile getPdfDocument() {
		return pdfDocument;
	}
	public void setPdfDocument(MultipartFile pdfDocument) {
		this.pdfDocument = pdfDocument;
	}
	public String getCourseImage() {
		return courseImage;
	}
	public void setCourseImage(String courseImage) {
		this.courseImage = courseImage;
	}
	public String getVideoDoumentName() {
		return videoDoumentName;
	}
	public void setVideoDoumentName(String videoDoumentName) {
		this.videoDoumentName = videoDoumentName;
	}
	public String getPdfDocumentName() {
		return pdfDocumentName;
	}
	public void setPdfDocumentName(String pdfDocumentName) {
		this.pdfDocumentName = pdfDocumentName;
	}
	public String getCourseImageName() {
		return courseImageName;
	}
	public void setCourseImageName(String courseImageName) {
		this.courseImageName = courseImageName;
	}
	public String getBucketUrl() {
		return bucketUrl;
	}
	public void setBucketUrl(String bucketUrl) {
		this.bucketUrl = bucketUrl;
	}
	public UsersDTO getEducator() {
		return educator;
	}
	public void setEducator(UsersDTO educator) {
		this.educator = educator;
	}
	
	
	
	
	
}
