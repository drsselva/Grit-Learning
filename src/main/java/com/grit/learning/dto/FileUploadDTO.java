package com.grit.learning.dto;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class FileUploadDTO {

	private String name;
	private String fileBase64;
	private String fileName;

}
