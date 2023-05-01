package com.grit.learning.model;

import java.util.Date;

import javax.persistence.Column;

import javax.persistence.MappedSuperclass;





@MappedSuperclass

public class RecordModifiers {	
	
	@Column(name = "created_by", updatable = false)
	private String createdBy;
	
	
    @Column(name = "created_on", updatable = false)
	private	Date createdOn = new Date();
	
	
    @Column(name = "modified_by")
	private String modifiedBy;
	
	
	@Column(name = "modified_on")
	private	Date modifiedOn;
	 

	@Column(name="status")
	private String  status = "Active";


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
