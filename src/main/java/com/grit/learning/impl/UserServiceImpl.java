package com.grit.learning.impl;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grit.learning.exception.UserNotFoundException;
import com.grit.learning.model.Response;
import com.grit.learning.model.User;
import com.grit.learning.repository.UserRepo;
import com.grit.learning.util.GritUtil;


@Service
public class UserServiceImpl {

    @Autowired
    UserRepo userRepo;

    public Optional<User> getUserById(int userId){
        return userRepo.findById(userId);

    }

    public User getUserByEmail(String email){
       return userRepo.findAll().stream().
                    filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);

    }
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public JSONObject loginUser(String email,String password){
        try{
        	JSONObject response = new JSONObject();
            User user = getUserByEmail(email);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    response.put("StatusCode", "200");
                    response.put("Message", "Success full login");
                    response.put("ApiStatus", "true");
                    
                    response.put("firstName", user.getFirstName());
                    response.put("lastName", user.getLastName());
                    response.put("phone", user.getPhone());
                    response.put("email", user.getEmail());
                    response.put("educationType", user.getEducationType());
                    response.put("age", user.getAge());
                    response.put("role", user.getRole());
                    response.put("token", GritUtil.generateToken());
                   return response;
                } else {
                    response.put("StatusCode", "200");
                    response.put("Message", "Email and password doesn't match");
                    response.put("ApiStatus", "false");
                    return response;
                }
            } else{
                response.put("StatusCode", "200");
                response.put("Message", "Email doesn't registered");
                response.put("ApiStatus", "false");
                return response;
            }
        } catch (Exception ex){
            throw new UserNotFoundException("Given email not registered");
        }
    }

    public Response updatePassword(String email, String password, String newPassword){
        try {
            Response response =new Response();
            User user =getUserByEmail(email);
            if(user !=null) {
                if (user.getPassword().equals(password)) {
                    user.setPassword(newPassword);
                    userRepo.save(user);
                    response.setStatusCode("200");
                    response.setMessage("Successfully updated the password");
                    response.setApiStatus("true");
                    return response;
                } else {
                    response.setStatusCode("200");
                    response.setMessage("Current password dose not match in our system");
                    response.setApiStatus("false");
                    return response;
                }
            } else {
                response.setStatusCode("200");
                response.setMessage("Email not registered");
                response.setApiStatus("false");
                return response;
            }
        } catch (UserNotFoundException ue){
            throw new UserNotFoundException("User not found");
        }
    }

    public Response createUser(User user){
        try {
            Response response =new Response();
            if( user.getEmail() !=null && user.getPassword() !=null
                    && user.getRole() !=null){
                if(getUserByEmail(user.getEmail()) == null) {
                    userRepo.save(user);
                    response.setStatusCode("200");
                    response.setMessage("User registered successfully.");
                    response.setApiStatus("true");
                    return response;
                } else {
                    response.setStatusCode("200");
                    response.setMessage( "This email id: "+user.getEmail() +" already registered");
                    response.setApiStatus("true");
                    return response;
                }

            } else {
                response.setStatusCode("200");
                response.setMessage( "Please check UserId,Fisrt name,Eamil,role and Password. These fields are required.");
                response.setApiStatus("true");
                return response;
            }
        } catch(Exception e){
            throw new RuntimeException("User not saved successfully");
        }

    }

    public Response forgotPassword(String email){
        Response response =new Response();
        User user = getUserByEmail(email);
        if(user !=null){
            response.setStatusCode("200");
            response.setMessage("Email is registered please send reset link");
            response.setApiStatus("true");
            return response;
        } else {
            response.setStatusCode("200");
            response.setMessage("Email not registered");
            response.setApiStatus("false");
            return response;
        }
    }

    public JSONObject resetPassword(String email,String password){
         User user = getUserByEmail(email);
         JSONObject response = new JSONObject();
            if(user !=null){
                user.setPassword(password);
                try {
                    userRepo.save(user);
                    
                    response.put("StatusCode", "200");
                    response.put("Message", "Password reset successfully");
                    response.put("ApiStatus", "true");
                    return response;
                } catch (UserNotFoundException ex){
                        throw new UserNotFoundException("Email not registered");
                }

            } else {
                response.put("StatusCode", "200");
                response.put("Message", "Email not registered");
                response.put("ApiStatus", "false");
                return response;
            }
    }

}
