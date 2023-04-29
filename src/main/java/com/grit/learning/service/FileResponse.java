package com.grit.learning.service;

public class FileResponse {
    private String finleName;

    public String getFinleName() {
        return finleName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getMessage() {
        return message;
    }

    private String fileUrl;
    private String message;

    public void setFinleName(String finleName) {
        this.finleName = finleName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FileResponse(String finleName, String fileUrl, String message) {
        this.finleName = finleName;
        this.fileUrl = fileUrl;
        this.message = message;
    }
}
