package com.bridgelabz.fundoousermodule.service;

import com.bridgelabz.fundoousermodule.dto.ForgetDTO;
import com.bridgelabz.fundoousermodule.dto.LoginDTO;
import com.bridgelabz.fundoousermodule.dto.RegisterDTO;
import com.bridgelabz.fundoousermodule.response.Response;

public interface UserServiceI {
	
	public Response createUser(RegisterDTO regdto);
	
	public Response findUser(String userId);

	public Response login(LoginDTO logindto, String email);
	
	public Response forgetPassword(ForgetDTO forget);
	
	public Response verify(String email);

	public Response showUsers();
}
