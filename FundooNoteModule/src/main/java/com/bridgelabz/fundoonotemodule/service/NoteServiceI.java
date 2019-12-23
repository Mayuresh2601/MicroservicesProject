package com.bridgelabz.fundoonotemodule.service;

import java.util.List;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.model.User;
import com.bridgelabz.fundoonotemodule.response.Response;

public interface NoteServiceI {
	
	public Response createNote(String token, NoteDTO notedto);
	
	public Response updateNote(String noteid, String email, NoteDTO notedto);
	
	public Response deleteNote(String noteid, String email);
	
	public Response findUserNote(String email);
	
	public Response isArchieve(String noteid, String email);
	
	public Response setColor(String noteid, String token, String colour);
	
	public List<User> getUsers();
	
}
