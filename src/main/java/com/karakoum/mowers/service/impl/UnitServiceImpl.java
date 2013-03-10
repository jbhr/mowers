package com.karakoum.mowers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karakoum.mowers.dao.UnitDao;
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {
	
	@Autowired
    UnitDao unitDao;
	
	public Unit findById(String objectId) {
        return unitDao.findById(objectId);
    }
	
	@Override
    public List<Unit> findAll() {
        return unitDao.findAll();
    }
    
    @Override
    public void saveOrUpdate(Unit unit) {
    	unitDao.saveOrUpdate(unit);
    }
    
    @Override
    public void deleteById(String objectId) {
    	unitDao.deleteById(objectId);
    }
    
    @Override
    public void deleteAll() {
    	List<Unit> units = findAll();
    	for(Unit unit:units) {
    		deleteById(unit.getId());
    	}
    }
    
	@Override
	public void move(Unit unit, char movementCode) {
		unit.executeMoveOrder(movementCode);
	}
    
    
}
