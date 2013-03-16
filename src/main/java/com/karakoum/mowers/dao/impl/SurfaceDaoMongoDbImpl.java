package com.karakoum.mowers.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.karakoum.mowers.common.dao.BaseDaoMongo;
import com.karakoum.mowers.dao.SurfaceDao;
import com.karakoum.mowers.domain.surface.impl.Surface;

@Repository
public class SurfaceDaoMongoDbImpl extends BaseDaoMongo implements SurfaceDao {
	
	public SurfaceDaoMongoDbImpl() {
		super();
		collectionName = "surfaces"; //Default collection name 
	}
	
	
	public SurfaceDaoMongoDbImpl(String collectionName) {
		super();
		this.collectionName = collectionName;
	}
	
	@Override
	public Surface findById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		Surface savedSurface = mongoOperation.findById(objectId, Surface.class, collectionName);
		return savedSurface;
    }
	
	@Override
	public List<Surface> findAll() {
		MongoOperations mongoOperation = getMongoOperations();
		List<Surface> savedSurfaces = mongoOperation.findAll(Surface.class, collectionName);
		return savedSurfaces;
    }
	
	@Override
	public void saveOrUpdate(Surface surfaces) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.save(surfaces, collectionName);
	}
	
	
	
	@Override
	public void deleteById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.findAndRemove(new Query(Criteria.where("id").is(objectId)), Surface.class, collectionName);
	}
	
	@Override
	public List<Surface> findAllByName(String name) {
		MongoOperations mongoOperation = getMongoOperations();
		List<Surface> surfaces = mongoOperation.find(new Query(Criteria
				.where("name").is(name)), Surface.class, collectionName);
		return surfaces;
	}
}
