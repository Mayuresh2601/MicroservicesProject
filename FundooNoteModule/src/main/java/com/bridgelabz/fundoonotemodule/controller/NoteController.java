/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Note Controller
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.model.User;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.bridgelabz.fundoonotemodule.service.NoteService;
import com.bridgelabz.fundoonotemodule.utility.Jwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/fundoonote")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	Environment noteEnvironment;
	
	
	/**Method: To create a Note
	 * @param token
	 * @param notedto
	 * @return Create Note Implementation Logic
	 */
	@PostMapping("/create")
	public Response createNote(@RequestHeader String token, @Valid @RequestBody NoteDTO notedto) {
		
		String email = jwt.getEmailId(token);
		Response response = noteService.createNote(email, notedto);
		return response;
	}
	
	
	/**Method: To Update a Note
	 * @param noteId
	 * @param token
	 * @param notedto
	 * @return Update Note Implementation Logic
	 */
	
	@PutMapping("/update")
	public Response updateNote(@RequestHeader String noteId,@RequestHeader String token, @Valid @RequestBody NoteDTO notedto) {
		
		String email = jwt.getEmailId(token);
		Response response = noteService.updateNote(noteId, email, notedto);
		return response;
	}

	
	/**Method: To Delete a Note
	 * @param noteId
	 * @param token
	 * @return Delete Note Implementation Logic
	 */
	@DeleteMapping("/delete")
	public Response deleteNote(@RequestHeader String noteId, @RequestHeader String token) {
		
		Response response =  noteService.deleteNote(noteId, token);
		return response;
	}

	
	/**Method: To Find Note by Token
	 * @param noteId
	 * @param token
	 * @return Find Note By Id Implementation Logic 
	 */
	@GetMapping("/find/{id}")
	public Response findUserNote(@PathVariable String token) {
		
		Response response = noteService.findUserNote(token);
		return response;
	}
	
	
	/**Method: To Display All Notes
	 * @return Display All Notes Implementation Logic
	 */
	@GetMapping("/showall")
	public Response showNotes() {
		
		List<Note> note = noteService.showNotes();
		return new Response(200, noteEnvironment.getProperty("Show_Notes"), note);
	}

	
	/**Method: To Archieve/Unarchieve a Note 
	 * @param noteId
	 * @param token
	 * @return Archieve/Unarchieve a Note Implementation Logic
	 */
	@PutMapping("/archieve")
	public Response isArchieve(@RequestHeader String noteId, @RequestHeader String token) {
		
		Response response = noteService.isArchieve(noteId, token);
		return response;
	}
	
	
	/**Method: To Set Colour to Note 
	 * @param noteId
	 * @param token
	 * @param colour
	 * @return Setting a Colour to Note Implementation Logic
	 */
	@PutMapping("/setcolour")
	public Response Colour(@RequestHeader String noteId, @RequestHeader String token, @RequestHeader String colour) {
		
		Response response = noteService.setColor(noteId, token, colour);
		return response;
	}
	
	
	/**Method: To Show All Users present in database
	 * @return Display All Users Implementation Logic
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	@GetMapping("/showallusers")
	public List<User> showUsers() throws JsonMappingException, JsonProcessingException {

		List<User> userlist =  noteService.showUsers();
		return userlist;
	}
}