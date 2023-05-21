package com.grit.learning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.StringUtils;

import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.SignInDTO;
import com.grit.learning.model.CourseCategory;
import com.grit.learning.dto.CourseCategoryDTO;

import com.grit.learning.service.MessagePropertyService;
import com.grit.learning.service.CourseCategoryService;

import com.grit.learning.util.GritUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/course/category")
public class CourseCategoryController {

	@Autowired
	private ResponseGenerator responseGenerator;

	@Autowired
	MessagePropertyService messageSource;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@RequestBody CourseCategoryDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			if (StringUtils.isNullOrEmpty(request.getName()))
				return responseGenerator.errorResponse(context,
						messageSource.getMessage("course.category.name.not.found"), HttpStatus.NOT_FOUND);

			CourseCategory courseCategory = courseCategoryService.findByName(request.getName());
			if (courseCategory != null)
				return responseGenerator.errorResponse(context,
						messageSource.getMessage("course.category.already.exists"), HttpStatus.NOT_FOUND);

			else {
				courseCategory = new CourseCategory();
				courseCategory.setName(request.getName());
				courseCategory.setShortName(request.getShortName());

			}

			courseCategoryService.saveOrUpdate(courseCategory);
			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.category.create"),
					null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody CourseCategoryDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);

		Optional<CourseCategory> CourseCategoryOptional = courseCategoryService.findById(request.getId());
		CourseCategory courseCategory = null;
		if (CourseCategoryOptional == null)
			return responseGenerator.errorResponse(context, messageSource.getMessage("course.category.not.found"),
					HttpStatus.NOT_FOUND);
		else {
			courseCategory = CourseCategoryOptional.get();
			courseCategory.setId(request.getId());
			courseCategory.setName(request.getName());
			courseCategory.setShortName(request.getShortName());
		}

		courseCategoryService.saveOrUpdate(courseCategory);

		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.category.update"),
					null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			List<CourseCategoryDTO> courseCategoryList = new ArrayList<>();

			for (CourseCategory courseCategory : courseCategoryService.findAll()) {
				CourseCategoryDTO courseCategoryDTO = new CourseCategoryDTO();
				courseCategoryDTO.setId(courseCategory.getId());
				courseCategoryDTO.setName(courseCategory.getName());
				courseCategoryDTO.setShortName(courseCategory.getShortName());
				courseCategoryList.add(courseCategoryDTO);
			}

			courseCategoryList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

			return responseGenerator.successGetResponse(context,
					courseCategoryList.size() > 0 ? messageSource.getMessage("course.category.get.list")
							: messageSource.getMessage("course.category.get.list.empty"),

					courseCategoryList, HttpStatus.OK);
		} catch (Exception e) {

			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/get/{courseCategoryId}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("courseCategoryId") UUID courseCategoryId,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		Optional<CourseCategory> CourseCategoryOptional = courseCategoryService.findById(courseCategoryId);

		if (null == CourseCategoryOptional) {
			return responseGenerator.errorResponse(context, messageSource.getMessage("course.category.not.found"),
					HttpStatus.NOT_FOUND);
		}
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.category.get.one"),
					CourseCategoryOptional.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseEntity<?> delete(@PathVariable("id") UUID id, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<CourseCategory> CourseCategoryOptional = courseCategoryService.findById(id);

			if (null == CourseCategoryOptional) {
				return responseGenerator.errorResponse(context, messageSource.getMessage("course.category.not.found"),
						HttpStatus.NOT_FOUND);
			} else {
				courseCategoryService.deleteById(id);
			}

			return responseGenerator.successGetResponse(context, messageSource.getMessage("course.category.delete"),
					null, HttpStatus.OK);
		} catch (Exception e) {

			return responseGenerator.errorResponse(context, messageSource.getMessage("course.category.invalid.delete"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}