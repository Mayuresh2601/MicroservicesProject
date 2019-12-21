package com.bridgelabz.fundoonotemodule.service;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.response.Response;

public interface NoteServiceI {
	
	public Response createNote(String token, NoteDTO notedto);
	
	public Response updateNote(String noteid, String email, NoteDTO notedto);
	
	public Response deleteNote(String noteid, String email);
	
	public Response findNoteById(String noteid, String email);
	
	public Response isArchieve(String noteid, String email);
	
	public Response setColor(String noteid, String token, String colour);
	
}
