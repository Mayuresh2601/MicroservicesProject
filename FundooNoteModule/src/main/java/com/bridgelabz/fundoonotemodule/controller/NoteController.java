/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Note Controller
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule.controller;

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

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.bridgelabz.fundoonotemodule.service.NoteService;
import com.bridgelabz.fundoonotemodule.utility.Jwt;

@RestController
@RequestMapping("/note")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private Jwt jwt;
	
	
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
	 * @param id
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
	 * @param id
	 * @return Delete Note Implementation Logic
	 */
	@DeleteMapping("/delete")
	public Response deleteNote(@RequestHeader String noteId, @RequestHeader String token) {
		
		Response response =  noteService.deleteNote(noteId, token);
		return response;
	}

	
	/**Method: To Find Note by Token
	 * @param id
	 * @return Find Note By Id Implementation Logic 
	 */
	@GetMapping("/find")
	public Response findNoteById(@RequestHeader String noteId, @RequestHeader String token) {
		
		Response response = noteService.findNoteById(noteId, token);
		return response;
	}

	
	/**Method: To Archieve/Unarchieve a Note 
	 * @param id
	 * @param token
	 * @return Archieve/Unarchieve a Note Implementation Logic
	 */
	@PutMapping("/archieve")
	public Response isArchieve(@RequestHeader String noteId, @RequestHeader String token) {
		
		Response response = noteService.isArchieve(noteId, token);
		return response;
	}
}