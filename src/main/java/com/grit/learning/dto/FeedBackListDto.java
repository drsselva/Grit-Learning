package com.grit.learning.dto;

import java.util.UUID;

public class FeedBackListDto {
	private UUID id;
	private String feedBackFileName;
	private UUID learnerId;
	private String learnerName;
	private String learnerEmailId;
	private String bucketUrl;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getFeedBackFileName() {
		return feedBackFileName;
	}
	public void setFeedBackFileName(String feedBackFileName) {
		this.feedBackFileName = feedBackFileName;
	}
	public UUID getLearnerId() {
		return learnerId;
	}
	public void setLearnerId(UUID learnerId) {
		this.learnerId = learnerId;
	}
	public String getLearnerName() {
		return learnerName;
	}
	public void setLearnerName(String learnerName) {
		this.learnerName = learnerName;
	}
	public String getLearnerEmailId() {
		return learnerEmailId;
	}
	public void setLearnerEmailId(String learnerEmailId) {
		this.learnerEmailId = learnerEmailId;
	}
	public String getBucketUrl() {
		return bucketUrl;
	}
	public void setBucketUrl(String bucketUrl) {
		this.bucketUrl = bucketUrl;
	}
}
