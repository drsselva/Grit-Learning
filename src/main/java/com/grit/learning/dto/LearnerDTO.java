package com.grit.learning.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class LearnerDTO {
	private UUID id;
	private UUID courseId;
    private UUID educatorId;
    private UUID learnerId;
    private MultipartFile learnerFile;
    private String learnerFileName;
    
    
	public UUID getCourseId() {
		return courseId;
	}
	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getEducatorId() {
		return educatorId;
	}
	public void setEducatorId(UUID educatorId) {
		this.educatorId = educatorId;
	}
	public UUID getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(UUID learnerId) {
		this.learnerId = learnerId;
	}
	public MultipartFile getLearnerFile() {
		return learnerFile;
	}
	public void setLearnerFile(MultipartFile learnerFile) {
		this.learnerFile = learnerFile;
	}
	public String getLearnerFileName() {
		return learnerFileName;
	}
	public void setLearnerFileName(String learnerFileName) {
		this.learnerFileName = learnerFileName;
	}
}
