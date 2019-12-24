/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Record Service
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoousermodule.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoousermodule.dto.ForgetDTO;
import com.bridgelabz.fundoousermodule.dto.LoginDTO;
import com.bridgelabz.fundoousermodule.dto.RegisterDTO;
import com.bridgelabz.fundoousermodule.model.User;
import com.bridgelabz.fundoousermodule.repository.UserRepositoryI;
import com.bridgelabz.fundoousermodule.response.Response;
import com.bridgelabz.fundoousermodule.utility.Jms;
import com.bridgelabz.fundoousermodule.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
@CacheConfig(cacheNames = "user")
public class UserService implements UserServiceI{

	@Autowired
	private UserRepositoryI userrepository;
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private Jms jms;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment userEnvironment;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	/**
	 *Method: To Add New User into Database
	 */
	@Cacheable(value = "Register", key = "#regdto")
	@Override
	public Response createUser(RegisterDTO regdto) {
	
		User user = mapper.map(regdto, User.class);

		if (user != null) {
			List<User> userlist = userrepository.findAll();
			List<String> userEmail = new ArrayList<>();
			for (int i = 0; i < userlist.size(); i++) {
				userEmail.add(userlist.get(i).getEmail());
			}
			
			boolean isVerify = userEmail.contains(regdto.getEmail());
			if(isVerify) {
				return new Response(404, userEnvironment.getProperty("EMAILID_EXISTS"), null);
			}
			
			boolean passwordChecker = regdto.getPassword().equals(regdto.getConfirmPassword());
			if (passwordChecker) {
				user.setPassword(bCryptPasswordEncoder.encode(regdto.getPassword()));
				user.setConfirmPassword(bCryptPasswordEncoder.encode(regdto.getConfirmPassword()));
			} 
			else {
				return new Response(404, userEnvironment.getProperty("PASSWORD_NOT_MATCH"), null);
			}

			String token = jwt.createToken(user.getEmail());
			jms.sendMail(user.getEmail(), token);
			userrepository.save(user);
			return new Response(200, userEnvironment.getProperty("Add_User"), userEnvironment.getProperty("ADD_USER"));
		}
		return new Response(404, userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To find User By Id in Database
	 */
	@Cacheable(value = "FindUser", key = "#userId")
	@Override
	public Response findUser(String userId) {
	
		User user = userrepository.findById(userId).get();
		return new Response(200, userEnvironment.getProperty("Find_User"), user);
	}
	
	
	/**
	 *Method: Display All User Details Present in Database
	 */
	@Override
	public Response showUsers() {
		
		List<User> userlist = userrepository.findAll();
		return new Response(200, userEnvironment.getProperty("Show_Users"), userlist);
//		return userlist;
	}
	
	
	/**
	 *Method: Login User if password matches the existing user record
	 */
	@Cacheable(value = "Login", key = "#email")
	@Override
	public Response login(LoginDTO logindto, String email) {
		User user = userrepository.findByEmail(email);
		if(logindto != null) {
				
			boolean isValid = bCryptPasswordEncoder.matches(logindto.getPassword(), user.getPassword());
			
			if(isValid) {
				user.setValidate(true);
				userrepository.save(user);
				return new Response(200, userEnvironment.getProperty("Login"), userEnvironment.getProperty("USER_LOGIN_SUCCESSFUL"));
			}
		}
		return new Response(404, userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	/**
	 *Method: To generate JWT token of emailId and send it on mail
	 */
	@SuppressWarnings("unused")
	@Cacheable(value = "ForgetPassword", key = "#forget")
	@Override
	public Response forgetPassword(ForgetDTO forget) {
		
		List<User> userlist = userrepository.findAll();
		List<String> email = new ArrayList<>();
		for (int i = 0; i < userlist.size(); i++) {
			email.add(userlist.get(i).getEmail());
		}
		
		if(email != null) {
			User user = mapper.map(forget, User.class);
			String token = jwt.createToken(user.getEmail());
			System.out.println("Recieved token:::::::  "+token);
			jms.sendMail(user.getEmail(), token);
			return new Response(200, userEnvironment.getProperty("CHECK_YOUR_MAIL"), userEnvironment.getProperty("CHECK_YOUR_MAIL"));
		}	
		return new Response(404, userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	/**
	 *Method: To Verify the EmailId and Password  
	 */
	@Cacheable(value = "VerifyUser", key = "#email")
	@Override
	public Response verify(String email) {

		User user = userrepository.findByEmail(email);
		
		if (email.equals(user.getEmail())) {
			user.setValidate(true);
			userrepository.save(user);
			return new Response(200, userEnvironment.getProperty("VERIFY_USER"), null);
		}
		return new Response(404, userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
}