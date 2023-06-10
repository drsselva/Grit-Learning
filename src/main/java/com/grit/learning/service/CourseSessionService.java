package com.grit.learning.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.model.CourseCategory;
import com.grit.learning.model.CourseSession;
import com.grit.learning.model.Users;
import com.grit.learning.repository.CourseCategoryRepository;
import com.grit.learning.repository.CourseSessionRepository;

@Service
public class CourseSessionService {
	
	@Autowired
    private FileService fileService;
	
	
	@Autowired
    private CourseSessionRepository courseSessionRepository;
	
	@Autowired
	private CourseCategoryService courseCategoryService;
	

	@Autowired
	private UsersService usersService;



	public void saveOrUpdate(CourseSessionDTO request ) throws Exception{
		CourseSession courseSession = new CourseSession();
		CourseSession courseExist = courseSessionRepository.findByEducatorIdAndCourseTitle(request.getEducator().getId(),request.getCourseTitle());
		
		//Users users = usersRepository.findById(request.getEducatorId()).get();
		
		if(courseExist == null) {
			String directoryPath="";
			
			if(directoryPath.isEmpty()) {
				directoryPath = request.getEducator().getId()+ "/" + request.getCourseTitle();
			}
			courseSession.setCourseTitle(request.getCourseTitle());
			courseSession.setCourseImage(request.getCourseImage());
			courseSession.setDescription(request.getDescription());
			if(!request.getVideoDoument().isEmpty()) {
				courseSession.setOtherFile(directoryPath+"/"+request.getVideoDoument().getOriginalFilename());
			}
			courseSession.setDocFile(directoryPath+"/"+request.getPdfDocument().getOriginalFilename());
			courseSession.setScheduledTime(request.getScheduledTime());

//			courseSession.setEducatorId(request.getEducatorId());
			courseSession.setCourseLink(request.getCourseLink());
			
			Users educator= usersService.findById(request.getEducator().getId());
			courseSession.setEducator(educator);
			
			
			Optional<CourseCategory>  courseCategoryOptional = courseCategoryService.findById(request.getCourseCategoryDTO().getId());
			
			if(courseCategoryOptional != null)
				courseSession.setCourseCategory(courseCategoryOptional.get());
			else
				throw new Exception("Course category not found");
			

			
			fileService.uploadFile(request, directoryPath);
			courseSessionRepository.save(courseSession);
		} else {
			throw new Exception("Course title already exists for the Educator");
		}
	}
	
	public List<CourseSession> findAll(){
		
		
		return courseSessionRepository.findAllByOrderByScheduledTimeDesc();
		
	}
	
	public List<CourseSession> findByEducatorId(String educatorId){
		
		
		return courseSessionRepository.findByEducatorIdOrderByScheduledTimeDesc(educatorId);
		
	}
	
	public CourseSession findById(UUID id){
		Optional<CourseSession> courseSession = courseSessionRepository.findById(id);
		if(courseSession.isPresent()) {
			return courseSession.get();
		} else {
			return null;
		}
	}
	
	public void CourseSession(UUID id){
		courseSessionRepository.deleteById(id);
	}

	public List<CourseSession> findByCourseCategoryId(UUID id) {
		
		return courseSessionRepository.findByCourseCategoryId(id);
	}

	
	

}
