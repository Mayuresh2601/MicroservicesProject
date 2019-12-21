package com.bridgelabz.fundoolabelmodule.service;

import com.bridgelabz.fundoolabelmodule.dto.LabelDTO;
import com.bridgelabz.fundoolabelmodule.response.Response;

public interface LabelServiceI {
	
	public Response createLabel(String token, LabelDTO labeldto);
	
	public Response updateLabel(String labelid, String email, LabelDTO labeldto);
	
	public Response deleteLabel(String labelid, String email);
	
	public Response findLabelById(String labelid, String email);

}
