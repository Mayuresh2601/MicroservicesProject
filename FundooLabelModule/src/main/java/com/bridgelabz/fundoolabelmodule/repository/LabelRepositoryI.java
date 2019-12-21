/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @interface To connect Label Mongo Repository
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoolabelmodule.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoolabelmodule.model.Label;

@Repository
public interface LabelRepositoryI extends MongoRepository<Label, String>{

}
