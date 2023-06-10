package com.grit.learning.controller;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.CourseCategoryDTO;
import com.grit.learning.dto.CourseSessionDTO;
import com.grit.learning.dto.DownloadDTO;
import com.grit.learning.dto.UsersDTO;
import com.grit.learning.model.CourseSession;
import com.grit.learning.service.CourseSessionService;
import com.grit.learning.service.FileService;
import com.grit.learning.service.MessagePropertyService;
import com.grit.learning.util.GritUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/course/session")
public class CourseSessionController {

	// private static final Logger logger =
	// Logger.getLogger(CourseSessionController.class);

	@Value("${cloud.aws.s3.bucket.url}")
	private String bucketUrl;

	@Autowired
	private CourseSessionService courseSessionService;

	@Autowired
	private FileService fileService;

	@Autowired
	MessagePropertyService messageSource;

	@Autowired
	private ResponseGenerator responseGenerator;

	@PostMapping(path = "/create")
	public ResponseEntity<?> create(@RequestHeader HttpHeaders httpHeader,
			@RequestParam(value = "imageB64") String imageB64,
			@RequestParam(value = "videoFile") MultipartFile videoFile,
			@RequestParam(value = "pdfFile") MultipartFile pdfFile, @RequestParam(value = "title") String title,
			@RequestParam(value = "scheduledTime") String scheduledTime,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "educatorId") UUID educatorId,
			@RequestParam(value = "courseCategoryId") UUID courseCategoryId,
			@RequestParam(value = "courseLink") String courseLink) throws Exception {

		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			CourseSessionDTO request = new CourseSessionDTO();
			request.setPdfDocument(pdfFile);
			request.setVideoDoument(videoFile);
			request.setCourseImage(imageB64);
			request.setCourseTitle(title.trim());

			UsersDTO usersDTO = new UsersDTO();
			usersDTO.setId(educatorId);
			request.setEducator(usersDTO);

			request.setScheduledTime(Timestamp.valueOf(scheduledTime));
			request.setDescription(description);

			request.setCourseLink(courseLink);
			CourseCategoryDTO courseCategoryDTO = new CourseCategoryDTO();
			courseCategoryDTO.setId(courseCategoryId);
			request.setCourseCategoryDTO(courseCategoryDTO);

			courseSessionService.saveOrUpdate(request);
			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.session.create"),
					null, HttpStatus.OK);
		} catch (Exception e) {

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/download", produces = "application/json")
	public ResponseEntity<?> download(@RequestBody DownloadDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			// Users users = usersRepository.findById(educatorId).get();
			byte[] data = fileService.downloadFile(request.getFilePath());
			String fileNameText = request.getFilePath().substring(request.getFilePath().lastIndexOf('/') + 1);
			;
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
			// logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getCourseByEducator/{educatorId}", produces = "application/json")
	public ResponseEntity<?> getCourseSessionByEducator(@PathVariable("educatorId") String educatorId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			List<CourseSession> courseSessionList = courseSessionService.findByEducatorId(educatorId);
			HashMap<String, List<CourseSessionDTO>> response = new HashMap<>();
			List<CourseSessionDTO> responseDto = new ArrayList<>();
			List<CourseSessionDTO> currentLinkResponse = new ArrayList<>();

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			// Users users = usersRepository.findByUserName("arul");
			// Users users = usersRepository.findById(educatorId).get();
			// String name = bucketUrl + users.getUserName()+"/";

			for (CourseSession courseSession : courseSessionList) {
				CourseSessionDTO courseSessionDTO = new CourseSessionDTO();
				courseSessionDTO.setId(courseSession.getId());
				courseSessionDTO.setCourseTitle(courseSession.getCourseTitle());
				courseSessionDTO.setDescription(courseSession.getDescription());

				UsersDTO usersDTO = null;
				if (courseSession.getEducator() != null) {
					usersDTO = new UsersDTO();
					usersDTO.setId(courseSession.getEducator().getId());
					usersDTO.setFirstName(courseSession.getEducator().getFirstName());

				}
				courseSessionDTO.setEducator(usersDTO);
				courseSessionDTO.setScheduledTime(courseSession.getScheduledTime());
				courseSessionDTO.setVideoDoumentName(courseSession.getOtherFile());
				courseSessionDTO.setPdfDocumentName(courseSession.getDocFile());
				courseSessionDTO.setCourseImageName(courseSession.getCourseImage());
				courseSessionDTO.setBucketUrl(bucketUrl);
				courseSessionDTO.setCourseLink(courseSession.getCourseLink());

				long diff = GritUtil.diffInMinutes(currentTimestamp, courseSession.getScheduledTime());

				System.out.println("****************" + diff);
				if (courseSession.getCourseLink() != null && !courseSession.getCourseLink().isEmpty()) {
					if (diff > -10 && diff < 30) {
						currentLinkResponse.add(courseSessionDTO);
					} else {
						responseDto.add(courseSessionDTO);
					}
				} else {
					responseDto.add(courseSessionDTO);
				}

				response.put("response", responseDto);
				response.put("currentLinkResponse", currentLinkResponse);

			}

			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.session.get.list"),
					response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getAllCourse", produces = "application/json")
	public ResponseEntity<?> getAllCourseSession(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			HashMap<String, List<CourseSessionDTO>> response = new HashMap<>();
			List<CourseSessionDTO> activeResponse = new ArrayList<>();
			List<CourseSessionDTO> inActiveResponse = new ArrayList<>();
			List<CourseSession> courseSessionList = courseSessionService.findAll();

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			for (CourseSession courseSession : courseSessionList) {
				CourseSessionDTO courseSessionDTO = new CourseSessionDTO();
				courseSessionDTO.setId(courseSession.getId());
				courseSessionDTO.setCourseTitle(courseSession.getCourseTitle());
				courseSessionDTO.setDescription(courseSession.getDescription());
				UsersDTO usersDTO = null;
				if (courseSession.getEducator() != null) {
					usersDTO = new UsersDTO();
					usersDTO.setId(courseSession.getEducator().getId());
					usersDTO.setFirstName(courseSession.getEducator().getFirstName());

				}

				courseSessionDTO.setEducator(usersDTO);
				courseSessionDTO.setScheduledTime(courseSession.getScheduledTime());
				courseSessionDTO.setVideoDoumentName(courseSession.getOtherFile());
				courseSessionDTO.setPdfDocumentName(courseSession.getDocFile());
				courseSessionDTO.setCourseImageName(courseSession.getCourseImage());
				courseSessionDTO.setBucketUrl(bucketUrl);
				courseSessionDTO.setCourseLink(courseSession.getCourseLink());

				int flag = currentTimestamp.compareTo(courseSession.getScheduledTime());
				if (flag >= 0) {
					long diff = GritUtil.diffInMinutes(currentTimestamp, courseSession.getScheduledTime());
					System.out.println("****************" + diff);
					if (courseSession.getCourseLink() != null && !courseSession.getCourseLink().isEmpty()) {
						if (diff < 30) {
							activeResponse.add(courseSessionDTO);
						}
					} else {
						activeResponse.add(courseSessionDTO);
					}
					if (flag >= 0) {
						activeResponse.add(courseSessionDTO);

					} else {
						inActiveResponse.add(courseSessionDTO);
					}

				}
				response.put("activeResponse", activeResponse);
				response.put("inActiveResponse", inActiveResponse);
			}

			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.session.get.list"),
					response, HttpStatus.OK);
		} catch (Exception e) {

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getCourseCategory/{id}", produces = "application/json")
	public ResponseEntity<?> getCourseCategoryById(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			List<CourseSession> courseSessionList = courseSessionService.findByCourseCategoryId(id);
			List<CourseSessionDTO> response = new ArrayList<>();

			for (CourseSession courseSession : courseSessionList) {
				CourseSessionDTO courseSessionDTO = new CourseSessionDTO();
				courseSessionDTO.setId(courseSession.getId());
				courseSessionDTO.setCourseTitle(courseSession.getCourseTitle());
				UsersDTO usersDTO = null;
				if (courseSession.getEducator() != null) {
					usersDTO = new UsersDTO();
					usersDTO.setId(courseSession.getEducator().getId());
					usersDTO.setFirstName(courseSession.getEducator().getFirstName());
				}
				courseSessionDTO.setEducator(usersDTO);
				courseSessionDTO.setCourseImageName(courseSession.getCourseImage());
				courseSessionDTO.setBucketUrl(bucketUrl);
				response.add(courseSessionDTO);
			}

			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.session.get.list"),
					response, HttpStatus.OK);
		} catch (Exception e) {
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getCourseDetails/{courseSessionId}", produces = "application/json")
	public ResponseEntity<?> getCourseDetaisById(@PathVariable("courseSessionId") UUID courseSessionId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			CourseSession courseSession = courseSessionService.findById(courseSessionId);

			CourseSessionDTO courseSessionDTO = new CourseSessionDTO();
			courseSessionDTO.setId(courseSession.getId());
			courseSessionDTO.setCourseTitle(courseSession.getCourseTitle());
			courseSessionDTO.setDescription(courseSession.getDescription());
			UsersDTO usersDTO = null;
			if (courseSession.getEducator() != null) {
				usersDTO = new UsersDTO();
				usersDTO.setId(courseSession.getEducator().getId());
				usersDTO.setFirstName(courseSession.getEducator().getFirstName());
				usersDTO.setEmailId(courseSession.getEducator().getEmailId());
				usersDTO.setLastName(courseSession.getEducator().getLastName());
				usersDTO.setPhone(courseSession.getEducator().getPhone());
				usersDTO.setUserRole(courseSession.getEducator().getUserRole());
				usersDTO.setProfileImg(courseSession.getEducator().getProfileImg());
			}

			courseSessionDTO.setEducator(usersDTO);
			courseSessionDTO.setScheduledTime(courseSession.getScheduledTime());
			courseSessionDTO.setVideoDoumentName(courseSession.getOtherFile());
			courseSessionDTO.setPdfDocumentName(courseSession.getDocFile());
			courseSessionDTO.setCourseImageName(courseSession.getCourseImage());
			courseSessionDTO.setBucketUrl(bucketUrl);

			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.session.get"),
					courseSessionDTO, HttpStatus.OK);
		} catch (Exception e) {
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
