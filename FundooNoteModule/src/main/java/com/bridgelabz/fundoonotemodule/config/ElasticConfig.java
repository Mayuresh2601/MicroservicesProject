/******************************************************************************
 *  Compilation:  javac -d bin ElasticConfig.java
 *  Execution:    
 *               
 *  
 *  Purpose:       main purpose this class create for configuration elastic search
 *
 *  @author  pandit walde
 *  @version 1.0
 *  @since   3-12-2019
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


	@Value("${elasticsearch.host}")   //value annotation for host
	private String elasticsearchHost;

	@Value("${elasticsearch.port}")    //value annotation for port
	private Integer elasticsearchPort;

	@Value("http")
	private String elasticsearchScheme;   //value annotation for http
  
	
    /**Method: Write Client method for allow and Build Request and Response  
     * @return Response for Client 
     */
    @Bean
	public RestHighLevelClient client() { // write client method for allow and bulid request and response  

		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(elasticsearchHost, elasticsearchPort, elasticsearchScheme)));
		return client;
	}

}
