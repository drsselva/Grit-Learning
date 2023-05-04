package com.grit.learning.service;

import org.apache.commons.codec.binary.Base64;

import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.dto.LearnerDTO;
import com.grit.learning.model.CourseContentFileUpload;
import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Learner;
import com.grit.learning.model.Response;
import com.grit.learning.model.Users;
import com.grit.learning.repository.CourseSessionRepository;
import com.grit.learning.repository.LearnerRepository;
import com.grit.learning.repository.UsersRepository;
import com.grit.learning.util.file.AWSS3DirectoryUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearnerService {
	
	@Autowired
    private FileService fileService;
	
	
	@Autowired
    private LearnerRepository learnerRepository;
	
	@Autowired
    private CourseSessionRepository courseSessionRepository;



	public void saveOrUpdate(LearnerDTO request ) throws Exception{
		Learner learner = new Learner();
		CourseSession courseSession = courseSessionRepository.findById(request.getCourseId()).get();
		if(courseSession != null) {
			String directoryPath = request.getEducatorId() +"/"+courseSession.getCourseTitle()+"/"+"FeedBack";
			
			learner.setCourseId(request.getCourseId());
			
			learner.setLearnerId(request.getLearnerId());
			learner.setEducatorId(request.getEducatorId());
			
			String fileName = System.currentTimeMillis() + "_"+request.getLearnerId()+"_"+ request.getLearnerFile().getOriginalFilename();
			learner.setLearnerFile(directoryPath+ "/" + fileName);
			
			fileService.uploadFile(directoryPath, fileName, request);
			learnerRepository.save(learner);
		} else {
			throw new Exception("Failed");
		}
	}
	
	public List<Learner> findAll(){
		
		
		return learnerRepository.findAll();
		
	}
	
	public Learner findById(UUID id){
		return learnerRepository.findById(id).get();
	}
	
	public void Learner(UUID id){
		learnerRepository.deleteById(id);
	}

	
	

}
