package com.grit.learning.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.dto.LearnerDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class FileService {

	 @Value("${cloud.aws.s3.bucket.name}")
	 private String bucketName;

	    @Autowired
	    private AmazonS3 s3Client;
	    
	   

		public String uploadFile(CourseSessionDTO courseSession, String directoryPath) throws Exception {
	    	
	    	try {
			
			/*File fileimageObj = convertMultiPartFileToFile(courseSession.getCourseImage());
	        String fileImageName =  courseSession.getCourseImage().getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName,  directoryPath + "/"+fileImageName, fileimageObj));
	        fileimageObj.delete();*/
			
			File filepdfObj = convertMultiPartFileToFile(courseSession.getPdfDocument());
	        String filePdfName =courseSession.getPdfDocument().getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName,directoryPath + "/"+ filePdfName, filepdfObj));
	        filepdfObj.delete();
	        
	        	if(!courseSession.getVideoDoument().isEmpty()) {
			        File filevideoObj = convertMultiPartFileToFile(courseSession.getVideoDoument());
			        String filevideoName = courseSession.getVideoDoument().getOriginalFilename();
			        s3Client.putObject(new PutObjectRequest(bucketName, directoryPath + "/"+filevideoName, filevideoObj));
			        filevideoObj.delete();
		        }
			
	    	}
	    	catch(Exception ex) {
	    		ex.printStackTrace();
	    		throw new Exception("File Upload Failed");
	    	}
			return   "s3 file upload successfully";
	    }


	    


		public byte[] downloadFile(String filePath) {

		
			      
			 S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, filePath));
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
	            e.printStackTrace();
	        }
	        return convertedFile;
	    }


		public void uploadFile(String directoryPath, String fileName, LearnerDTO request) throws IOException {
			
			File fileimageObj = convertMultiPartFileToFile(request.getLearnerFile());
			s3Client.putObject(new PutObjectRequest(bucketName,  directoryPath + "/"+fileName, fileimageObj));
			fileimageObj.delete();
		
	       
			
		}
	    
}
