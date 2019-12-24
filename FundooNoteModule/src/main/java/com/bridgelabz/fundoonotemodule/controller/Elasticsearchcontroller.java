/******************************************************************************
 * 
 *  Purpose: Create Controller for Elastic Search
 *  @author  Mayuresh Sunil Sonar
 *  @version 1.0
 *  @since  3-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoonotemodule.controller;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.bridgelabz.fundoonotemodule.service.ElasticSearchService;


@RestController
@RequestMapping("/elasticsearch")
public class Elasticsearchcontroller {
		
	@Autowired
	private ElasticSearchService elasticSearch;
	
	
	/**Method: To Create Document in Elastic Search DB
	 * @param Provide detail of user note
	 * @return note detail add in Elastic Search return Add successfully or not
	 * @throws IOException
	 */
	@PostMapping("/create")
	public ResponseEntity<Response> createDocument(@RequestBody Note note) throws IOException{
		
		return new ResponseEntity<Response>((elasticSearch.createDocument(note)),HttpStatus.OK);
	}
	
	
	/**Method: To Search Document by Id
	 * @param Provide Id for searching Perticular info of user note
	 * @return Detail information of User note
	 * @throws IOException
	 */
	@GetMapping("/read")
	public ResponseEntity<Response> searchById( @RequestParam String id) throws IOException{
		
		return new ResponseEntity<Response>((elasticSearch.readDocument(id)),HttpStatus.OK);
	}
	
	
	/**Method: To Delete Document by Id
	 * @param Provide User Id to Delete from Elastic Search DB
	 * @return User Id Document Delete
	 * @throws IOException
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteDocuemnt(@RequestParam String id) throws IOException{
		
		return new ResponseEntity<Response>((elasticSearch.deleteDocument(id)),HttpStatus.OK);
	}
	
	
	/**Method: To Show All Details of Document
	 * @param searchString
	 * @return Show all detail in Elastic Search DB
	 * @throws IOException
	 */
	@GetMapping("/showall")
	public Response searchField(@RequestBody String searchString) throws IOException {
           
		return new Response(200, " All Notes ", elasticSearch.search(searchString));
	}
	
	
	/**Method: To Update Document Elastic Search DB
	 * @param Provide Detail of Update information  of User note
	 * @param Provide User Id to Update Document 
	 * @return Update Document Successfull
	 * @throws IOException
	 */
	@PutMapping("/update")
	public ResponseEntity<Response> updateDocument(@RequestBody Note note, @RequestParam String id) throws IOException {
        
		return new ResponseEntity<Response>((elasticSearch.updateDocument(note, id)),HttpStatus.CREATED);
	}

	
	/**Method: To get the Result of the search field
	 * @param searchResponse
	 * @return List of Document contains the same keywords
	 * @throws IOException
	 */
	@GetMapping("/searchresult")
	public Response getSearchResult(@RequestBody SearchResponse searchResponse) throws IOException {
           
		return new Response(200, "Search Result", elasticSearch.getSearchResult(searchResponse));
	}
	
	
	/**Method: To get the Result of the search field containing Title field
	 * @param title
	 * @return List of Document contains the same keywords in Title field
	 * @throws IOException
	 */
	@GetMapping("/searchbytitle")
	public Response searchByTitle(@RequestBody String title) throws IOException {
           
		return new Response(200, "Search By Title", elasticSearch.searchByTitle(title));
	}
	
	
	/**Method: To get the Result of the search field containing Description field
	 * @param description
	 * @return List of Document contains the same keywords in Description field
	 * @throws IOException
	 */
	@GetMapping("/searchbydescription")
	public Response searchByDescription(@RequestBody String description) throws IOException {
           
		return new Response(200, "Search By Description", elasticSearch.searchByDescription(description));
	}
}
