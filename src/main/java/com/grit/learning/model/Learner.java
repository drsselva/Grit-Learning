package com.grit.learning.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;




@Entity
@Table(name="learner")
public class Learner  extends RecordModifiers implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "learnerUUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
    private UUID id;
	private UUID courseId;
    private UUID educatorId;
    private UUID learnerId;
    private String learnerFile;
    
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
	public String getLearnerFile() {
		return learnerFile;
	}
	public void setLearnerFile(String learnerFile) {
		this.learnerFile = learnerFile;
	}
	public UUID getCourseId() {
		return courseId;
	}
	public void setCourseId(UUID courseId) {
		this.courseId = courseId;
	}
  
	
}
