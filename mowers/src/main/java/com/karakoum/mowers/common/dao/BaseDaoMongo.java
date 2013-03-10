package com.karakoum.mowers.common.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.karakoum.mowers.Mowers;


public class BaseDaoMongo {
	
	protected static Logger log = Logger.getLogger(Mowers.class.getName());
	
	@Autowired
    private MongoOperations mongoOperation;
    
    public MongoOperations getMongoOperations() {
    	return mongoOperation;
    }
    
}
