package com.grit.learning.service;

import org.apache.commons.codec.binary.Base64;

import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.model.CourseContentFileUpload;
import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Response;
import com.grit.learning.model.Users;
import com.grit.learning.repository.CourseSessionRepository;
import com.grit.learning.repository.UsersRepository;
import com.grit.learning.util.file.AWSS3DirectoryUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseSessionService {
	
	@Autowired
    private FileService fileService;
	
	
	@Autowired
    private CourseSessionRepository courseSessionRepository;
	
	@Autowired
	 private UsersRepository usersRepository;



	public void saveOrUpdate(CourseSessionDTO request ) throws IOException{
		CourseSession courseSession = new CourseSession();
		Users users = usersRepository.findByUserName("arul");
		courseSession.setCourseTitle(request.getCourseTitle());
		courseSession.setCourseImage(request.getCourseImage().getOriginalFilename());
		courseSession.setDescription(request.getDescription());
		courseSession.setDocFile(request.getPdfDocument().getOriginalFilename());
		courseSession.setOtherFile(request.getVideoDoument().getOriginalFilename());
		courseSession.setScheduledTime(request.getScheduledTime());
		courseSession.setEducatorId(users.getId());
		fileService.uploadFile(users, request);
		courseSessionRepository.save(courseSession);
	
	}
	
	public Iterable<CourseSession> findAll(){
		return courseSessionRepository.findAll();
		
	}
	
	public CourseSession findById(UUID id){
		return courseSessionRepository.findById(id).get();
	}
	
	public void CourseSession(UUID id){
		courseSessionRepository.deleteById(id);
	}

	
	

}
