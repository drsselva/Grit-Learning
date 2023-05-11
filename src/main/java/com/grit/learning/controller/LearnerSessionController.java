package com.grit.learning.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.LearnerDTO;
import com.grit.learning.service.LearnerService;
import com.grit.learning.service.MessagePropertyService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/learner")
public class LearnerSessionController {

	// private static final Logger logger =
	// Logger.getLogger(LearnerSessionController.class);

	@Autowired
	private LearnerService learnerService;

	@Autowired
	MessagePropertyService messageSource;

	@Autowired
	private ResponseGenerator responseGenerator;

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

}
