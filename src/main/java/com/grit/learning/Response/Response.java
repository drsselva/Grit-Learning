package com.grit.learning.Response;

public class Response {

	private Object data;
	private Error error;
	private String timeStamp;
	private String message;
	private String status;
	private String code;
	
	//private List<String> errorMessages;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
//	public List<String> getErrorMessages() {
//		return errorMessages;
//	}
//	public void setErrorMessages(List<String> errorMessages) {
//		this.errorMessages = errorMessages;
//	}
	
	
	
}
