package com.karakoum.mowers.dao;

import java.util.List;

import com.karakoum.mowers.domain.roundorders.RoundOrders;

public interface RoundOrdersDao {
	
	public RoundOrders findById(String objectId);
	public List<RoundOrders> findAll();
	public void saveOrUpdate(RoundOrders unit);
	public void deleteById(String objectId);
}
