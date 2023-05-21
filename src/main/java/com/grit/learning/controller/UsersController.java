package com.grit.learning.controller;

import java.util.List;
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

import com.grit.learning.Response.ResponseGenerator;
import com.grit.learning.Response.TransactionContext;
import com.grit.learning.dto.SignInDTO;
import com.grit.learning.dto.UsersDTO;
import com.grit.learning.model.Users;
import com.grit.learning.repository.UsersRepository;
import com.grit.learning.service.MessagePropertyService;
import com.grit.learning.service.UsersService;
import com.grit.learning.util.GritUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/user")
public class UsersController {

	// private static final Logger logger = Logger.getLogger(UsersController.class);

	@Autowired
	private ResponseGenerator responseGenerator;

	@Autowired
	MessagePropertyService messageSource;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersService usersService;

	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@RequestBody UsersDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Users isUserExist = usersService.findByEmailId(request.getEmailId());
			if (isUserExist == null) {
				Users users = new Users();
				users.setFirstName(request.getFirstName());
				users.setLastName(request.getLastName());
				users.setEmailId(request.getEmailId());
				users.setUserRole(request.getUserRole());
				users.setPassword(request.getPassword());
				usersService.saveOrUpdate(users);
			} else {
				return responseGenerator.errorResponse(context, "Email Id already exists", HttpStatus.FORBIDDEN);
			}
			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.create"), null,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@RequestBody UsersDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		Users users = null;
		Users isUserExist = usersService.findById(request.getId());
		if (isUserExist != null) {
			users = isUserExist;
		} else {
			return responseGenerator.errorResponse(context, "User id not found", HttpStatus.NOT_FOUND);
		}

		users.setId(request.getId());
		users.setFirstName(request.getFirstName());
		users.setLastName(request.getLastName());
		users.setPhone(request.getPhone());
		users.setProfileImg(request.getProfileImg());
		usersService.saveOrUpdate(users);

		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.update"), null,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {

			List<Users> userList = usersService.findAll();
			userList.sort((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()));

			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.get.list"),

					userList, HttpStatus.OK);
  		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/get/{userId}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("userId") UUID userId, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		Users userOptional = usersService.findById(userId);

		if (null == userOptional) {
			return responseGenerator.errorResponse(context, "User id not found", HttpStatus.NOT_FOUND);
		}
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.get.one"), userOptional,
					HttpStatus.OK);
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
			Users userObject = usersService.findById(id);

			if (null == userObject) {
				return responseGenerator.errorResponse(context, "User id not found", HttpStatus.NOT_FOUND);
			}

			return responseGenerator.successGetResponse(context, messageSource.getMessage("user.delete"), null,
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, messageSource.getMessage("user.invalid.delete"),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> login(@RequestBody SignInDTO request, @RequestHeader HttpHeaders httpHeader)
			throws Exception {
		TransactionContext context = null;
		try {

			httpHeader.set("token", GritUtil.generateToken());
			context = responseGenerator.generateTransationContext(httpHeader);
			Users userOptional = usersRepository.findByEmailId(request.getEmailId());

			if (userOptional == null || !userOptional.getPassword().equals(request.getPassword())) {
				return responseGenerator.errorResponse(context, "Invalid username or password.",
						HttpStatus.UNAUTHORIZED);
			} else {
				return responseGenerator.successGetResponse(context, messageSource.getMessage("user.login"),
						userOptional, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}