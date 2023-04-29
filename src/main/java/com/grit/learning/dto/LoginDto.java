package com.grit.learning.dto;

import lombok.Data;

@Data
public class LoginDto {
	public String emailId;
	public String password;
	public String newPassword;
}
