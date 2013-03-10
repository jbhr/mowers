package com.karakoum.mowers.dao;

import java.util.List;

import com.karakoum.mowers.domain.surface.impl.Surface;

public interface SurfaceDao {
	
	public Surface findById(String objectId);
	public List<Surface> findAllByName(String name);
	public List<Surface> findAll();
	public void saveOrUpdate(Surface surface);
	public void deleteById(String objectId);
}
