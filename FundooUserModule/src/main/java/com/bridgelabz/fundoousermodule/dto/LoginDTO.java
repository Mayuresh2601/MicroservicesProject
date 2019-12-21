package com.bridgelabz.fundoousermodule.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginDTO {
	
	@NotBlank(message = "Email Id is Mandotary")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "EmailId Format is Invalid")
	private String email;
	
	@NotBlank(message = "Password is Mandatory")
	@Size(min = 6, max = 16,message = "Password should be Between 6 to 16 letters")
	private String password;

}