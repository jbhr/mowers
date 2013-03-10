package com.karakoum.mowers.service;

import java.util.List;

import com.karakoum.mowers.domain.unit.impl.Unit;

public interface UnitService {
	
	public Unit findById(String objectId);

    public List<Unit> findAll();

    public void saveOrUpdate(Unit unit);

    public void deleteById(String objectId);
    
    public void deleteAll();
    
    public void move(Unit unit, char movementCode);
    
}
