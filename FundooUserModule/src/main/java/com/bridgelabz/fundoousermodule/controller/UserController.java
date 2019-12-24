/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Record Controller
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoousermodule.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoousermodule.dto.ForgetDTO;
import com.bridgelabz.fundoousermodule.dto.LoginDTO;
import com.bridgelabz.fundoousermodule.dto.RegisterDTO;
import com.bridgelabz.fundoousermodule.model.User;
import com.bridgelabz.fundoousermodule.response.Response;
import com.bridgelabz.fundoousermodule.service.UserService;
import com.bridgelabz.fundoousermodule.utility.Jwt;
import com.netflix.ribbon.proxy.annotation.Http;

@RestController
@RequestMapping("/fundoouser")
public class UserController {
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private UserService userService;
	
	
	/**Method: To Create User in Database
	 * @param regdto
	 * @return Create User implementation Logic
	 */
	@PostMapping("/register")
	public Response createUser(@Valid @RequestBody RegisterDTO regdto) {
		
		Response response = userService.createUser(regdto); 
		return response;
	}
	
	
	/**Method: To Find User By Id from database
	 * @param id
	 * @return Find User by Id implementation Logic
	 */
	
	@GetMapping("/find/{id}")
	public Response findUser(@PathVariable String id) {
		
		Response response = userService.findUser(id);
		return response;
	}
	
	
	
	/**Method: To Show All Users present in database
	 * @return Display All Users Implementation Logic
	 */
	@GetMapping("/showall")
	public Response showUsers() {

		Response response =  userService.showUsers();
		return response;
	}
	
	
	/**Method: To Login in database
	 * @param logindto
	 * @return Implementation Logic of login
	 */
	@PostMapping("/login")
	public Response login(@Valid @RequestBody LoginDTO logindto,@RequestHeader String token) {
		
		String email = jwt.getEmailId(token);
		Response response =  userService.login(logindto, email);
		return response;
	}
	
	
	/**Method: To check forget password implementation
	 * @param registerdto
	 * @return Implementation Logic of forget password
	 */
	@PostMapping("/forgetpassword")
	public Response forgetPassword(@Valid @RequestBody ForgetDTO forget) {
		
		Response response = userService.forgetPassword(forget);
		return response;
	}
	
	
	/**Method: To Verify EmailId and Password in the Database
	 * @param token
	 * @return Implementation Logic of verifying user
	 */
	@PostMapping("/verify")
	public Response verifyUser(@Valid @RequestHeader String token) {
		
		String email = jwt.getEmailId(token);
		Response response = userService.verify(email);
		return response;
	}
}