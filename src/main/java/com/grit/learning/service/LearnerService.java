package com.grit.learning.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grit.learning.dto.LearnerDTO;
import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Learner;
import com.grit.learning.repository.CourseSessionRepository;
import com.grit.learning.repository.LearnerRepository;

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
		Optional<Learner> learner = learnerRepository.findById(id);
		if(learner.isPresent()) {
			return learner.get();
		} else {
			return null;
		}
	}
	
	public void Learner(UUID id){
		learnerRepository.deleteById(id);
	}

	public List<Learner> findAllByCourseId(UUID courseId){
		
		
		return learnerRepository.findByCourseId(courseId);
		
	}
	

}
