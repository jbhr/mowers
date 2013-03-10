package com.karakoum.mowers.service;

import java.util.List;

import com.karakoum.mowers.domain.roundorders.RoundOrders;

public interface RoundOrdersService {
	
	public RoundOrders findById(String objectId);

    public List<RoundOrders> findAll();

    public void saveOrUpdate(RoundOrders roundorders);

    public void deleteById(String objectId);
    
    public void deleteAll();
    
    
}
