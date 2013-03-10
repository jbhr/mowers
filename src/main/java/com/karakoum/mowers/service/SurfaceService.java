package com.karakoum.mowers.service;

import java.util.List;

import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Unit;

public interface SurfaceService {
	
	
	public void putUnitOnSurface(Surface surface, Unit unit, int orig_x, int orig_y, int orig_orientation);
	
	public Surface findById(String objectId);
	
	public List<Surface> findAll();
	
	public void saveOrUpdate(Surface unit);
	
	public void deleteById(String objectId);
	
	public void deleteAll();
}
