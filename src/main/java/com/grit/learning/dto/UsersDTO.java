package com.grit.learning.dto;

import java.util.UUID;

public class UsersDTO {
	 	private UUID id;
		private String firstName;
	    private String lastName;
	    private String password;
	    private String emailId;
		private String userRole;
		private String profileImg;
		private String phone;
		
		public String getProfileImg() {
			return profileImg;
		}
		public void setProfileImg(String profileImg) {
			this.profileImg = profileImg;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmailId() {
			return emailId;
		}
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		public String getUserRole() {
			return userRole;
		}
		public void setUserRole(String userRole) {
			this.userRole = userRole;
		}
    
    

}
