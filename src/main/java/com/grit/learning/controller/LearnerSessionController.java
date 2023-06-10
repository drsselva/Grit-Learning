package com.grit.learning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.FeedBackListDto;
import com.grit.learning.dto.LearnerDTO;
import com.grit.learning.model.Learner;
import com.grit.learning.model.Users;
import com.grit.learning.service.LearnerService;
import com.grit.learning.service.MessagePropertyService;
import com.grit.learning.service.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/learner")
public class LearnerSessionController {

	// private static final Logger logger =
	// Logger.getLogger(LearnerSessionController.class);
	@Value("${cloud.aws.s3.bucket.url}")
	private String bucketUrl;
	
	@Autowired
	private LearnerService learnerService;

	@Autowired
	MessagePropertyService messageSource;

	@Autowired
	private ResponseGenerator responseGenerator;
	
	@Autowired
	private UsersService usersService;

	@PostMapping(path = "/file/upload")
	public ResponseEntity<?> create(@RequestHeader HttpHeaders httpHeader,
			@RequestParam(value = "feedbackFile") MultipartFile feedbackFile,
			@RequestParam("educatorId") UUID educatorId, @RequestParam("learnerId") UUID learnerId,
			@RequestParam("courseId") UUID courseId) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			LearnerDTO request = new LearnerDTO();
			request.setEducatorId(educatorId);
			request.setLearnerFile(feedbackFile);
			request.setLearnerId(learnerId);
			request.setCourseId(courseId);

			learnerService.saveOrUpdate(request);
			return responseGenerator.successGetResponse(context, messageSource.getMessage("learner.file.upload"), null,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/getAllFeedbackByCourse/{courseId}", produces = "application/json")
	public ResponseEntity<?> getAllFeedback(@PathVariable("courseId") UUID courseId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			
			List<FeedBackListDto> feedBackList = new ArrayList<>();
			List<Learner> learnerFeedBackList = learnerService.findAllByCourseId(courseId);
	
			for(Learner learner : learnerFeedBackList) {
				FeedBackListDto feedBackListDto = new FeedBackListDto();
				feedBackListDto.setId(learner.getId());
				feedBackListDto.setLearnerId(learner.getLearnerId());
				feedBackListDto.setFeedBackFileName(learner.getLearnerFile());
				Users user = usersService.findById(learner.getLearnerId());
				if(user != null) {
					feedBackListDto.setLearnerEmailId(user.getEmailId());
					
					String name = (user.getFirstName()!=null && !user.getFirstName().isEmpty()) ? user.getFirstName() : ""; 
					feedBackListDto.setLearnerName((name+" "+((user.getLastName()!=null && !user.getLastName().isEmpty()) ? user.getLastName() : "")).trim());
				}
				feedBackListDto.setBucketUrl(bucketUrl);
				feedBackList.add(feedBackListDto);
			}		
			return responseGenerator.successGetResponse(context, messageSource.getMessage("learner.feedback.get"), feedBackList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
