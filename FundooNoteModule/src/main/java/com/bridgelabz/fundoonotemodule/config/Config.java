/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class To Configure Maven Project
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.bridgelabz.fundoonotemodule.model.Note;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Config {
	
	
	/**Method: To Encrypt the password at backend
	 * @return Encrypting Password
	 */
	@Bean
	public BCryptPasswordEncoder BCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
	/**Method: To Map the Model with DTO class
	 * @return Mapping Models
	 */
	@Bean
	public ModelMapper mapper() {
		
		return new ModelMapper();
	}

	
	
	/**Method: To Implement Swagger On UserInterface
	 * @return SwaggerUI
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	
	/**Method: Radis Connectivity
	 * @return 
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisProperties properties = redisProperties();
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("server",6379);
		configuration.setHostName(properties.getHost());
		configuration.setPort(properties.getPort());

		return new JedisConnectionFactory(configuration);
	}

	/**Method: Redis Template
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Note> redisTemplate() {
		final RedisTemplate<String, Note> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<>(Note.class));
		
		return template;
	}

	
	/**Method: Properties of Redis
	 * @return Redis Properties
	 */
	@Bean
	@Primary
	public RedisProperties redisProperties() {
		
		return new RedisProperties();
	}
	
	
	/**Method: Creating Bean of Rest Template to use it in one instance
	 * @return Rest Template
	 */
	@Bean
	public RestTemplate resttemplate() {
		
		return new RestTemplate();
	}
	
	
	/**Method: Creating Bean of WebClient to use it in one instance
	 * @return WebClient Template
	 */
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		
		return WebClient.builder();
	}
}