package com.karakoum.mowers.dao;

import java.util.List;

import com.karakoum.mowers.domain.unit.impl.Unit;

public interface UnitDao {
	
	public Unit findById(String objectId);
	public List<Unit> findAll();
	public void saveOrUpdate(Unit unit);
	public void deleteById(String objectId);
}
