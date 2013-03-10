package com.karakoum.mowers.common.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.karakoum.mowers.Mowers;


public class BaseDaoMongo {
	
	protected static Logger log = Logger.getLogger(Mowers.class.getName());
	
	protected String collectionName;
	
	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Autowired
    private MongoOperations mongoOperation;
    
    public MongoOperations getMongoOperations() {
    	return mongoOperation;
    }
    
    /**
     * On donne la possibilité de modifier l'instance de mongoOperation
     * Utilisé dans le cadre des tests unitaires  
     * @return
     */
    protected void setMongoOperations(MongoOperations mongoOperation) {
    	this.mongoOperation = mongoOperation;
    }
    
}
