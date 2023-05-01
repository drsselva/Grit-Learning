package com.grit.learning.util.file;

import java.util.UUID;

import com.amazonaws.util.StringUtils;

public class AWSS3DirectoryUtil {

	public static final String DIRECTORY_COURSECONTENT= "COURSECONTENT";
	
	public static final String GRITLAAS_S3_BASE_URL= "https://gritlaas.s3.us-east-1.amazonaws.com";
	
	public static String getString(UUID projectId, String fileName) {
		if(null==fileName) {
			return AWSS3DirectoryUtil.GRITLAAS_S3_BASE_URL+"/default.jpg";
		}
		return AWSS3DirectoryUtil.GRITLAAS_S3_BASE_URL+"/"+projectId+"/"+fileName;
	}
	

}
