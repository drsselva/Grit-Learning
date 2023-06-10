package com.grit.learning.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;




@Entity
@Table(name="course_session")
public class CourseSession  extends RecordModifiers implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "courseUUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
    private UUID id;
	private String courseTitle;
    private String description;
    private String courseImage;
    private Timestamp scheduledTime;
    private String educatorId;
    private String otherFile;
    private String docFile;
    private String courseLink;
    
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
	public String getCourseImage() {
		return courseImage;
	}
	public void setCourseImage(String courseImage) {
		this.courseImage = courseImage;
	}
	public Timestamp getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	public String getEducatorId() {
		return educatorId;
	}
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}
	public String getOtherFile() {
		return otherFile;
	}
	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}
	public String getDocFile() {
		return docFile;
	}
	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}
	public String getCourseLink() {
		return courseLink;
	}
	public void setCourseLink(String courseLink) {
		this.courseLink = courseLink;
	}
    
	
}
