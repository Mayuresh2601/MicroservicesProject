package com.bridgelabz.fundoolabelmodule.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Label implements Serializable{
	/**
	 * Making Label Serializable
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	private String id;
	
	@NotEmpty(message = "Label Title is Mandatory")
	private String labelTitle;
	
	@Pattern(regexp = ".+@.\\.[a-z]")
	private String email;
	
	private String createDate;
	
	private String editDate;

}
