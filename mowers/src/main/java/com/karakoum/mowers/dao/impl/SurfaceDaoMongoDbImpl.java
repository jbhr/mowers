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
	
	@Override
	public Surface findById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		Surface savedSurface = mongoOperation.findById(objectId, Surface.class, "surfaces");
		return savedSurface;
    }
	
	@Override
	public List<Surface> findAll() {
		MongoOperations mongoOperation = getMongoOperations();
		List<Surface> savedSurfaces = mongoOperation.findAll(Surface.class, "surfaces");
		return savedSurfaces;
    }
	
	@Override
	public void saveOrUpdate(Surface surfaces) {
		MongoOperations mongoOperation = getMongoOperations();
		mongoOperation.save(surfaces, "surfaces");
	}
	
	
	
	@Override
	public void deleteById(String objectId) {
		MongoOperations mongoOperation = getMongoOperations();
		Surface surface = findById(objectId);
		if (surface != null) {
			//mongoOperation.remove(new Query(Criteria.where("id").is(surface.getId())), "surfaces");
			mongoOperation.remove(surface, "surfaces");
		}
	}
	
	@Override
	public List<Surface> findAllByName(String name) {
		MongoOperations mongoOperation = getMongoOperations();
		List<Surface> surfaces = mongoOperation.find(new Query(Criteria
				.where("name").is(name)), Surface.class, "surfaces" );
		return surfaces;
	}
}
