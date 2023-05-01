package com.grit.learning.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CourseSessionDTO {
	private String courseTitle;
    private String description;
    private String scheduledTime;
    private UUID educatorId;
    private MultipartFile videoDoument;
    private MultipartFile pdfDocument;
    private MultipartFile courseImage;

}
