package com.grit.learning.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;

import com.grit.learning.dto.LoginDto;
import com.grit.learning.impl.UserServiceImpl;
import com.grit.learning.model.User;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getUserById(userId));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getUserByEmail(email));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getAllUsers());
    }

    @PostMapping(path="/loginUser" , consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
    	JSONObject result= new JSONObject();
    	String token = null;
    	result = userServiceImpl.loginUser(loginDto.emailId, loginDto.password);
    	if(result.has("token")) {
    		token = result.optString("token");
    	}
        return ResponseEntity.status(HttpStatus.OK).header("token", token).body(result.toString());

    }

    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestBody LoginDto loginDto){
       return ResponseEntity.status(200).body(userServiceImpl.forgotPassword(loginDto.emailId));
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(200).body(userServiceImpl.updatePassword(loginDto.emailId,
        		loginDto.password,loginDto.newPassword));
    }

    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.createUser(user));

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody LoginDto loginDto) {
    	JSONObject result= new JSONObject();
    	result = userServiceImpl.resetPassword(loginDto.emailId,loginDto.password);
        return ResponseEntity.status(200).body(result.toString());
    }

}
