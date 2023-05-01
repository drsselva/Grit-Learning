package com.grit.learning.model;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.grit.learning.enumeration.ImageType;
import com.grit.learning.util.file.AWSS3DirectoryUtil;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="course_content_fileupload")
public class CourseContentFileUpload extends RecordModifiers implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	private UUID id;
	private String name;
	private String fileName;
	
	@Transient
	private ImageType imageType;
	
	@Transient
	private String fileBase64;
	
	@Transient
	private String fileUrl;
	
	@Transient
	private Boolean showDownloadBtn =false;
	
//	public String getFileUrl() {
//		if( courseContentId.getId()!= null) {
//			
//			return AWSS3DirectoryUtil.GRITLAAS_S3_BASE_URL+"/"+courseContentId+"/"+fileName;
//		}
//		
//		return "";
	
  

}
