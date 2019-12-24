/******************************************************************************  
 *  
 *  Purpose: Create Service class to write all logic of Elastic Search
 *  @author  Mayuresh Sunil Sonar
 *  @version 1.0
 *  @since  3-12-2019
 *
 ******************************************************************************/
package com.bridgelabz.fundoonotemodule.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotemodule.model.Note;
import com.bridgelabz.fundoonotemodule.response.Response;
import com.bridgelabz.fundoonotemodule.utility.ResponseUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchService implements Elasticsearchservice {

	private final String INDEX = "note"; // create for elastic search same like as database schema
	private final String TYPE = "notes"; // create for elastic search same like database table

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;  


	/**
	 *Method: Storing All Data in Elastic Search
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response createDocument(Note note) throws IOException {

		Map<String, Object> map = mapper.convertValue(note, Map.class);
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String date = now.format(formatter);
		note.setCreateDate(date);
		note.setColor("#FFFFFF");
		
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, note.getId()).source(map); 
		IndexResponse indexresponse = client.index(indexrequest, RequestOptions.DEFAULT);

		ResponseUtility.CustomSucessResponse("HTTP_SUCCESS_MSG", note);
		return ResponseUtility.customSuccessResponse(indexresponse.getResult().name()); 
	}

	
	/**
	 *Method: To Read Perticular User Note Id Details
	 */
	@Override
	public Response readDocument(String id) throws IOException {

		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource(); 
		return ResponseUtility.CustomSucessResponse("Success", mapper.convertValue(resultMap, Note.class));
	}

	
	/**
	 *Method: To Search Field using keyword
	 * @throws IOException 
	 */
	@Override
	public List<Note> search(String searchstring) throws IOException {
		
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		return getSearchResult(searchResponse);
	}
	
	
	/**
	 *Method: Delete Perticular User note in Elastic Search Details
	 */
	@Override
	public Response deleteDocument(String id) throws IOException {

		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse deleteresponse = client.delete(deleterequest, RequestOptions.DEFAULT);

		return new Response(200, "delete note", deleteresponse.getResult().name()); 
	}

	
	/**
	 * Method: Update Perticular User note in Elastic Search Details
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Response updateDocument(Note note, String id) throws IOException {

		Note noteModel = findById(id);

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id);
		Map<String, Object> map = mapper.convertValue(note, Map.class);

		updateRequest.doc(map);

		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT); 
		return new Response(200, "update note", updateResponse.getResult().name());
	}

	
	/**Method: To Find the Note by Id
	 * @param id
	 * @return Note Details
	 * @throws IOException
	 */
	public Note findById(String id) throws IOException {

		GetRequest request = new GetRequest(INDEX, TYPE, id);

		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		Map<String, Object> map = getResponse.getSource();

		return mapper.convertValue(map, Note.class);
	}

	
	/**Method: To get result of the Keyword on the Address Bar
	 * @param searchResponse
	 * @return List of Notes
	 */
	public List<Note> getSearchResult(SearchResponse searchResponse) {

		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<Note> userList = new ArrayList<Note>();
		
		if (searchHit.length > 0) {
			Arrays.stream(searchHit).forEach(i -> userList.add(mapper.convertValue(i.getSourceAsMap(), Note.class)));
		}
		return userList;
	}

	
	/**
	 * Method: Search Perticular Title of User note If Title is found 
	 */
	@Override
	public Response searchByTitle(String title) throws IOException {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 

		searchSourceBuilder.query(
				QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery(title).lenient(true).field("title")));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		return new Response(200, "user title", getSearchResult(searchResponse));
	}
	
	
	/**
	 * Method: Search Perticluar description of user note if description is found
	 */
	@Override
	public Response searchByDescription(String description) throws IOException {
		SearchRequest searchRequest = new SearchRequest(); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query( QueryBuilders.boolQuery() 
									.should(QueryBuilders.queryStringQuery(description)
									.lenient(true).field("description"))); 
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT); 																				// request

		return new Response(200, "user title", getSearchResult(searchResponse));
	}

}
