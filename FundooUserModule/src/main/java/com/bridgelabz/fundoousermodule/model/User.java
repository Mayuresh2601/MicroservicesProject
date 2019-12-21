package com.bridgelabz.fundoousermodule.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User implements Serializable{
	/**
	 * Making User Serializable
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	private String id;
	
	private String firstName;

	private String lastName;

	private String email;

	private String password;
	
	private String confirmPassword;

	private String mobileNumber;

	private boolean isValidate;
	
}
