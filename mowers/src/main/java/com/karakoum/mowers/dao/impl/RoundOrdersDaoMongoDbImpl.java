package com.karakoum.mowers.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.karakoum.mowers.common.dao.BaseDaoMongo;
import com.karakoum.mowers.dao.RoundOrdersDao;
import com.karakoum.mowers.domain.roundorders.RoundOrders;

@Repository
public class RoundOrdersDaoMongoDbImpl extends BaseDaoMongo implements RoundOrdersDao {
	
	@Override
	public RoundOrders findById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		RoundOrders savedRoundOrders = mongoOperation.findById(objectId, RoundOrders.class, "roundorders");
		return savedRoundOrders;
    }
	
	@Override
	public List<RoundOrders> findAll() {
		MongoOperations mongoOperation = getMongoOperations();
		List<RoundOrders> savedRoundOrders = mongoOperation.findAll(RoundOrders.class, "roundorders");
		return savedRoundOrders;
    }
	
	@Override
	public void saveOrUpdate(RoundOrders roundorders) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.save(roundorders, "roundorders");
	}
	
	
	@Override
	public void deleteById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		RoundOrders roundorders = findById(objectId);
		mongoOperation.remove(roundorders, "roundorders");
	}
}
