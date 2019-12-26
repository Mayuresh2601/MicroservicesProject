package com.bridgelabz.fundoonotemodule.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.model.User;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface NoteServiceI {
	
	public Response createNote(String token, NoteDTO notedto) throws IOException;
	
	public Response updateNote(String noteId, String email, NoteDTO notedto) throws IOException;
	
	public Response deleteNote(String noteid, String email) throws IOException;
	
	public Response findUserNote(String email);
	
	public List<Note> showNotes();
	
	public Response isArchieve(String noteId, String email);
	
	public Response setColor(String noteId, String token, String colour) throws IOException;
	
	public List<User> showUsers() throws JsonMappingException, JsonProcessingException;
	
	public List<User> getUserLastLogin();
}
