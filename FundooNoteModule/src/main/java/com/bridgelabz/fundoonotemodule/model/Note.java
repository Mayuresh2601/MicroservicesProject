package com.bridgelabz.fundoonotemodule.model;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Note implements Serializable{
	/**
	 * Making Note Serializable
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	private String id;
	
	@NotBlank(message= "Title is Mandatory")
	private String title;
	
	@NotBlank(message = "Description is Mandatory")
	private String description;
	
	@NotBlank(message = "Email ID is Mandatory")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "EmailId Format is Invalid")
	private String emailId;
	
	@NotBlank(message = "Created Note Date is Mandatory")
	private String createDate;
	
	private String editDate;
	
	private boolean isArchieve;

//	@JsonIgnore
//	@DBRef(lazy = true)
//	private List<Label> labellist = new ArrayList<>();
		
}
