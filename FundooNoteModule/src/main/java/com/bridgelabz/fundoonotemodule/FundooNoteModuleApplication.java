/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes Note Module
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class FundooNoteModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNoteModuleApplication.class, args);
	}

}
