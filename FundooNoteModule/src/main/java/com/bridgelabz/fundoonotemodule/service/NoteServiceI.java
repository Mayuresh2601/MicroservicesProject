package com.bridgelabz.fundoonotemodule.service;

import java.util.List;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.model.User;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface NoteServiceI {
	
	public Response createNote(String token, NoteDTO notedto);
	
	public Response updateNote(String noteid, String email, NoteDTO notedto);
	
	public Response deleteNote(String noteid, String email);
	
	public Response findUserNote(String email);
	
	public List<Note> showNotes();
	
	public Response isArchieve(String noteid, String email);
	
	public Response setColor(String noteid, String token, String colour);
	
	public List<User> showUsers() throws JsonMappingException, JsonProcessingException;
	
}
