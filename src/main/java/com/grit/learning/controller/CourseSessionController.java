package com.grit.learning.controller;


import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.model.CourseContentFileUpload;
import com.grit.learning.model.Response;
import com.grit.learning.model.Users;
import com.grit.learning.repository.UsersRepository;
import com.grit.learning.service.CourseSessionService;
import com.grit.learning.service.FileService;
import com.grit.learning.service.MessagePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
@RestController
@RequestMapping("/course/session")
public class CourseSessionController {
	
	
	private static final Logger logger = Logger.getLogger(CourseSessionController.class);

    @Autowired
    private CourseSessionService courseSessionService;
    
    @Autowired
    private FileService fileService;
    
 
    
	@Autowired
	MessagePropertyService messageSource;
    
    
    @Autowired
	private ResponseGenerator responseGenerator;
    
    @Autowired
    private UsersRepository usersRepository;
    
    
    @PostMapping(path = "/create")
	public ResponseEntity<?> create( @RequestHeader HttpHeaders httpHeader,
			@RequestParam(value = "imageFile") MultipartFile imageFile,
			@RequestParam(value = "videoFile") MultipartFile videoFile,
			@RequestParam(value = "pdfFile") MultipartFile pdfFile,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "scheduledTime") String scheduledTime,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "educatorId") String educatorId
			) throws Exception{

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
		
			CourseSessionDTO request = new CourseSessionDTO();
			request.setPdfDocument(pdfFile);
			request.setVideoDoument(videoFile);
			request.setCourseImage(imageFile);
			request.setCourseTitle(title);
			//request.setEducatorId(UUID.fromString(educatorId));
			request.setScheduledTime(scheduledTime);
			request.setDescription(description);
			courseSessionService.saveOrUpdate(request);
		return responseGenerator.successResponse(context, messageSource.getMessage("course.session.create"),
				HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		logger.error(e.getMessage(), e);
		return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);}
	}
    
   
    
 
	@GetMapping(value = "/download/{filename}", produces = "application/json")
	public ResponseEntity<?> download(@PathVariable("filename") String filename,
			 @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Users users = usersRepository.findByUserName("arul");
			
			// String filePath ="https://gritlaas.s3.amazonaws.com/"+users.getUserName()+"/"+filename;
			
			//byte[] data = Files.readAllBytes(Paths.get(filePath));

			byte[] data = fileService.downloadFile(users,filename);
			String fileNameText = filename;
			String fileName = URLEncoder.encode(fileNameText, "UTF-8").replaceAll("\\+", "%20");
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			httpHeaders.setContentLength(data.length);
			httpHeaders.setContentDispositionFormData("attachment", fileName);
			httpHeaders.set("fileName", fileName);
			httpHeaders.add("fileName", fileName);
			return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	


//	 @GetMapping("/download/{fileName}")
//	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
//	        byte[] data = fileService.downloadFile(fileName);
//	        ByteArrayResource resource = new ByteArrayResource(data);
//	        return ResponseEntity
//	                .ok()
//	                .contentLength(data.length)
//	                .header("Content-type", "application/octet-stream")
//	                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//	                .body(resource);
//	    }
//
//	    @DeleteMapping("/delete/{fileName}")
//	    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
//	        return new ResponseEntity<>(fileService.deleteFile(fileName), HttpStatus.OK);
//	    }
	}
}
