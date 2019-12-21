package com.bridgelabz.fundoolabelmodule.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoolabelmodule.dto.LabelDTO;
import com.bridgelabz.fundoolabelmodule.response.Response;
import com.bridgelabz.fundoolabelmodule.service.LabelService;
import com.bridgelabz.fundoolabelmodule.utility.Jwt;

@RestController
@RequestMapping("/label")
public class LabelController {
	
	@Autowired
	private LabelService service;
	
	@Autowired
	private Jwt jwt;
	
	
	/**Method: To Create Label in Database
	 * @param token
	 * @param labeldto
	 * @return Create Label implementation Logic
	 */
	@PostMapping("/create")
	public Response createLabel(@RequestHeader String token, @Valid @RequestBody LabelDTO labeldto) {
		
		Response response = service.createLabel(token, labeldto);
		return response;
	}
	
	
	/**Method: To Update Label in Database
	 * @param id
	 * @param labeldto
	 * @return Update Label implementation Logic
	 */
	@PutMapping("/update")
	public Response updateLabel(@RequestHeader String labelid, @RequestHeader String token,@Valid @RequestBody LabelDTO labeldto) {
		
		String email = jwt.getEmailId(token);
		Response response = service.updateLabel(labelid, email, labeldto);
		return response;
	}

	
	/**Method: To Delete Label from Database
	 * @param id
	 * @return Delete Label implementation Logic
	 */
	@DeleteMapping("/delete")
	public Response deleteLabel(@RequestHeader String labelid, @RequestHeader String token) {
	
		String email = jwt.getEmailId(token);
		Response response = service.deleteLabel(labelid, email);
		return response;
	}
	
	
	/**Method: To Find Label By Id from database
	 * @param id
	 * @return Find Label by Id implementation Logic
	 */
	@GetMapping("/find")
	public Response findLabelById(@RequestHeader String labelid, @RequestHeader String token) {
		
		String email = jwt.getEmailId(token);
		Response response = service.findLabelById(labelid, email);
		return response;
	}
}