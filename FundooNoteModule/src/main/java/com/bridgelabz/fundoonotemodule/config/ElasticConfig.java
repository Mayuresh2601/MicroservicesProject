/******************************************************************************
 * 
 *  Purpose: To Configure Elastic Search
 *  @author  Mayuresh Sunil Sonar
 *  @since   23-12-2019
 *
 ******************************************************************************/

package com.bridgelabz.fundoonotemodule.config;

import org.apache.http.HttpHost;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

	//Value Annotation for host
	@Value("${elasticsearch.host}")   
	private String elasticsearchHost;

	//Value Annotation for port
	@Value("${elasticsearch.port}")    
	private Integer elasticsearchPort;

	//Value Annotation for HTTP
	@Value("http")
	private String elasticsearchScheme;   
  
	
    /**Method: Write Client method for allow and Build Request and Response  
     * @return Response for Client 
     */
    @Bean
	public RestHighLevelClient client() { 

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(elasticsearchHost, elasticsearchPort, elasticsearchScheme)));
		return client;
	}

}
