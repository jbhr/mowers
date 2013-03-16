package com.karakoum.mowers.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karakoum.mowers.common.dao.BaseDaoMongo;
import com.karakoum.mowers.dao.RoundOrdersDao;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.domain.unit.impl.Unit;

@Repository
public class RoundOrdersDaoMongoDbImpl extends BaseDaoMongo implements RoundOrdersDao {
	
	
	public RoundOrdersDaoMongoDbImpl() {
		super();
		collectionName = "roundorders"; //Default collection name 
	}
	
	public RoundOrdersDaoMongoDbImpl(String collectionName) {
		super();
		this.collectionName = collectionName;
	}
	
	@Override
	public RoundOrders findById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		RoundOrders savedRoundOrders = mongoOperation.findById(objectId, RoundOrders.class, collectionName);
		return savedRoundOrders;
    }
	
	@Override
	public List<RoundOrders> findAll() {
		MongoOperations mongoOperation = getMongoOperations();
		List<RoundOrders> savedRoundOrders = mongoOperation.findAll(RoundOrders.class, collectionName);
		return savedRoundOrders;
    }
	
	@Override
	public void saveOrUpdate(RoundOrders roundorders) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.save(roundorders, collectionName);
	}
	
	
	@Override
	public void deleteById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.findAndRemove(new Query(Criteria.where("id").is(objectId)), RoundOrders.class, collectionName);
	}
}
