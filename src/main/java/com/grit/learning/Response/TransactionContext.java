package com.grit.learning.Response;

public class TransactionContext {

	private String correlationId;
	private String ApplicationLabel;
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getApplicationLabel() {
		return ApplicationLabel;
	}
	public void setApplicationLabel(String applicationLabel) {
		ApplicationLabel = applicationLabel;
	}
	
	
	
}
