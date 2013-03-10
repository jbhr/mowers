package com.karakoum.mowers.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.karakoum.mowers.common.dao.BaseDaoMongo;
import com.karakoum.mowers.dao.UnitDao;
import com.karakoum.mowers.domain.unit.impl.Unit;

@Repository
public class UnitDaoMongoDbImpl extends BaseDaoMongo implements UnitDao {
	
	@Override
	public Unit findById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		Unit savedUnit = mongoOperation.findById(objectId, Unit.class, "units");
		return savedUnit;
    }
	
	@Override
	public List<Unit> findAll() {
		MongoOperations mongoOperation = getMongoOperations();
		List<Unit> savedUnits = mongoOperation.findAll(Unit.class, "units");
		return savedUnits;
    }
	
	@Override
	public void saveOrUpdate(Unit unit) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.save(unit, "units");
	}
	
	
	@Override
	public void deleteById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		Unit unit = findById(objectId);
		mongoOperation.remove(unit, "units");
	}
}
