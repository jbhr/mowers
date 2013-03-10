package com.karakoum.mowers.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karakoum.mowers.dao.RoundOrdersDao;
import com.karakoum.mowers.domain.roundorders.RoundOrders;
import com.karakoum.mowers.service.RoundOrdersService;

@Service
public class RoundOrdersServiceImpl implements RoundOrdersService {
	
	@Autowired
    RoundOrdersDao roundOrdersDao;
	
	public RoundOrders findById(String objectId) {
        return roundOrdersDao.findById(objectId);
    }
	
	@Override
    public List<RoundOrders> findAll() {
        return roundOrdersDao.findAll();
    }
    
    @Override
    public void saveOrUpdate(RoundOrders roundorders) {
    	roundOrdersDao.saveOrUpdate(roundorders);
    }
    
    @Override
    public void deleteById(String objectId) {
    	roundOrdersDao.deleteById(objectId);
    }
    
    @Override
    public void deleteAll() {
    	List<RoundOrders> roundorders_list = findAll();
    	for(RoundOrders roundorders:roundorders_list) {
    		deleteById(roundorders.getId());
    	}
    }
    
    
    
}
