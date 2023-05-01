package com.grit.learning.model;


public class Response {
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    private String statusCode;
    private String apiStatus;
   // private User user;


    public Response(String message, String statusCode, String apiStatus) {
        this.message = message;
        this.statusCode = statusCode;
        this.apiStatus = apiStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Response(){}
}
