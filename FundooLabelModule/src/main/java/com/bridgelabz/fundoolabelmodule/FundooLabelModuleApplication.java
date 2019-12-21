/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes Label Module
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoolabelmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class FundooLabelModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooLabelModuleApplication.class, args);
	}

}
