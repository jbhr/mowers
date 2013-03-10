package com.karakoum.mowers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karakoum.mowers.dao.SurfaceDao;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Unit;
import com.karakoum.mowers.service.SurfaceService;
import com.karakoum.mowers.service.UnitService;

@Service
public class SurfaceServiceImpl implements SurfaceService {
	
	@Autowired
    private SurfaceDao surfaceDao;
	
	@Autowired
    private UnitService unitService;
	
 	
	public Surface findById(String objectId) {
        return surfaceDao.findById(objectId);
    }
	
	@Override
    public List<Surface> findAll() {
        return surfaceDao.findAll();
    }
    
    @Override
    public void saveOrUpdate(Surface surface) {
    	surfaceDao.saveOrUpdate(surface);
    }
    
    @Override
    public void deleteById(String objectId) {
    	surfaceDao.deleteById(objectId);
    }
    
    @Override
    public void deleteAll() {
    	List<Surface> surfaces = findAll();
    	for(Surface surface:surfaces) {
    		deleteById(surface.getId());
    	}
    }

	
	@Override
	public void putUnitOnSurface(Surface surface, Unit unit, int orig_x, int orig_y, int orig_orientation_indice) {
		
		if (surface != null) {
			surface.attachUnit(unit, orig_x, orig_y, orig_orientation_indice);
		}
	}
	
    
    
}
