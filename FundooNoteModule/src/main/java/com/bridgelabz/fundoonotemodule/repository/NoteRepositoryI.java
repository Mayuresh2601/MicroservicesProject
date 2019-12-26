/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @interface To connect Note Mongo Repository
*  @author  Mayuresh Sunil Sonar
*  @since 21-12-2019
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotemodule.model.Note;

@Repository
public interface NoteRepositoryI extends MongoRepository<Note, String>{
	
	public List<Note> findByEmailId(String email);

}
