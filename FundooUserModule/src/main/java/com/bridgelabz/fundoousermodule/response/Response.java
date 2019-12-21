package com.bridgelabz.fundoousermodule.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class Response implements Serializable{
	/**
	 * Making User Response Serializable
	 */
	private static final long serialVersionUID = 1L;
	

	private int status;
	
	private String message;
	
	private Object data;
	
	public Response(int status, String message, Object data) {
		
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
