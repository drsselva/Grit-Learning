package com.grit.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Users;
import com.grit.learning.util.file.AWSS3DirectoryUtil;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

@Service
@Slf4j

public class FileService {

	 @Value("${cloud.aws.s3.bucket.name}")
	 private String bucketName;

	    @Autowired
	    private AmazonS3 s3Client;
	    
	   

	    public String uploadFile(Users users, CourseSessionDTO courseSession) throws IOException {
	    	
	    	
			String directoryPath="";
			
			
			 
			
			if(directoryPath.isEmpty()) {
				directoryPath = users.getUserName();
			}
			File fileimageObj = convertMultiPartFileToFile(courseSession.getCourseImage());
	        String fileImageName = System.currentTimeMillis() + "_" + courseSession.getCourseImage().getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName,  directoryPath + "/"+fileImageName, fileimageObj));
	        fileimageObj.delete();
			
			File filepdfObj = convertMultiPartFileToFile(courseSession.getPdfDocument());
	        String filePdfName = System.currentTimeMillis() + "_" + courseSession.getPdfDocument().getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName,directoryPath + "/"+ filePdfName, filepdfObj));
	        filepdfObj.delete();
	        
	        File filevideoObj = convertMultiPartFileToFile(courseSession.getVideoDoument());
	        String filevideoName = System.currentTimeMillis() + "_" + courseSession.getVideoDoument().getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName, directoryPath + "/"+filevideoName, filevideoObj));
	        filevideoObj.delete();
			
		
			return   "s3 file upload successfully";
	    }


	    


		public byte[] downloadFile(Users users,String fileName) {

			String	directoryPath = users.getUserName()+"/";
			 S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, directoryPath + fileName));
	      //  S3Object s3Object = s3Client.getObject(bucketName, fileName);
	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
	        try {
	            byte[] content = IOUtils.toByteArray(inputStream);
	            return content;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


	    public String deleteFile(String fileName) {
	        s3Client.deleteObject(bucketName, fileName);
	        return fileName + " removed ...";
	    }


	    private File convertMultiPartFileToFile(MultipartFile file) {
	        File convertedFile = new File(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	            log.error("Error converting multipartFile to file", e);
	        }
	        return convertedFile;
	    }
	    
}
