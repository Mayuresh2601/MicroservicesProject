/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Note Service
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotemodule.dto.NoteDTO;
import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.model.User;
import com.bridgelabz.fundoonotemodule.repository.NoteRepositoryI;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.bridgelabz.fundoonotemodule.utility.Jwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
@PropertySource("classpath:message.properties")
@CacheConfig(cacheNames = "note")
public class NoteService implements NoteServiceI{
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private NoteRepositoryI noterepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment noteEnvironment;
	
	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private WebClient.Builder webClient;

	/**
	 *Method: To Create a Note for User
	 */
	@Cacheable(value = "CreateNote", key = "#email")
	@Override
	public Response createNote(String email, NoteDTO notedto) {

		if(email != null) {
			Note note = mapper.map(notedto, Note.class);
			note.setEmailId(email);
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String date = now.format(formatter);
			note.setCreateDate(date);
			note.setColor("#FFFFFF");
			noterepository.save(note);
			
			return new Response(200, noteEnvironment.getProperty("Create_Note"), noteEnvironment.getProperty("CREATE_NOTE"));
		}
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}
	
	
	/**
	 *Method: To Update a Note for User
	 */
	@Cacheable(value = "UpdateNote", key = "#noteid")
	@Override
	public Response updateNote(String noteid, String email, NoteDTO notedto) {
		
		if(email != null) {
		
			Note noteupdate = noterepository.findById(noteid).get();
			if(noteupdate != null) {
				noteupdate.setTitle(notedto.getTitle());
				noteupdate.setDescription(notedto.getDescription());
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String date = now.format(formatter);
				
				noteupdate.setEditDate(date);
				noterepository.save(noteupdate);
				
				return new Response(200, noteEnvironment.getProperty("Update_Note"), noteEnvironment.getProperty("UPDATE_NOTE"));
			}
			return new Response(404, noteEnvironment.getProperty("UPDATE_NOTE_NULL"), null);
		}
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Delete a Note
	 */
	@Cacheable(value = "DeleteNote", key = "#noteid")
	@Override
	public Response deleteNote(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		
		if(email != null) {
			noterepository.deleteById(noteid);
			
			return new Response(200, noteEnvironment.getProperty("Delete_Note"), noteEnvironment.getProperty("DELETE_NOTE"));
		}
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: Search a Note using Id
	 */
	@Cacheable(value = "FindNote", key = "#noteid")
	@Override
	public Response findUserNote(String token) {
		
		String email = jwt.getEmailId(token);
		
		if(email != null) {
			List<Note> note =  noterepository.findByEmailId(email);
			

			return new Response(200, noteEnvironment.getProperty("Find_Note"), note);
		}
		
		
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}
	
	
	/**
	 *Method: To Display All Notes
	 */
	@Override
	public List<Note> showNotes() {
		
		return noterepository.findAll();
	}
	
	
	/**
	 *Method: To Archieve/Unarchieve Note for User
	 */
	@Cacheable(value = "ArchieveNote", key = "#noteid")
	@Override
	public Response isArchieve(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		List<Note> listofNote= noterepository.findByEmailId(email);
		Note note =listofNote.stream().filter(i->i.getId().equals(noteid)).findAny().orElse(null);
		if(email != null)
		{
			if(note.getId().equals(noteid)) {
				note.setArchieve(!(note.isArchieve()));
				noterepository.save(note);
				System.out.println(noteEnvironment.getProperty("Archieve_Note"));
				System.out.println(note.isArchieve());
				if(note.isArchieve()) {
					return new Response(200, noteEnvironment.getProperty("Archieve_Note"), note.isArchieve());
				}else {
					return new Response(200, noteEnvironment.getProperty("UnArchieve_Note"), note.isArchieve());
				}
			}
			return new Response(404, noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}


	/**
	 *Method: To Set Colour to Note
	 */
	@Override
	public Response setColor(String noteid, String token, String colour) {
	
		String email = jwt.getEmailId(token);
		if(email != null) {
			
			Note note =  noterepository.findById(noteid).get();
			if(note != null) {
				
				String pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(colour);
				boolean isMatches = m.matches();
				
				if(isMatches) {
					note.setColor(colour);
					noterepository.save(note);
					return new Response(200, noteEnvironment.getProperty("Colour_Setting"), noteEnvironment.getProperty("COLOUR_SET"));
				}
				else {
					return new Response(200, noteEnvironment.getProperty("INVALID_COLOUR_CODE"), null);
				}
			}
			return new Response(404, noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(404, noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> showUsers() throws JsonMappingException, JsonProcessingException {

		String url = "http://localhost:8081/fundoouser/showall";
		
		Response userResponse = restTemplate.getForObject(url, Response.class);
		
		List<User> userList = (List<User>) userResponse.getData(); 
		
		return userList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserLastLogin() {
		
		String url = "http://localhost:8081/fundoouser/showlastlogin";
		
		Response userResponse = restTemplate.getForObject(url, Response.class);
		
		List<User> userList = (List<User>) userResponse.getData(); 
        
		return userList;
	}
	
	
}