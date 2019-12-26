/******************************************************************************   
 *  
 *  Purpose: Create Service Interface for Elastic Search
 *  @author  Mayuresh Sunil Sonar
 *  @since 24-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoonotemodule.service;

import java.io.IOException;
import java.util.List;

import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.response.Response;

public interface ElasticSearchServiceI { 
	
	public Response createDocument(Note note) throws IOException; 

	public Response readDocument(String id) throws IOException; 

	public List<Note> search(String searchstring) throws IOException; 

	public Response deleteDocument(String id) throws IOException; 

	public Response updateDocument(Note note, String id) throws IOException; 
	
	public Response searchByTitle(String title) throws IOException; 

	public Response searchByDescription(String description) throws IOException; 																				
	
}
