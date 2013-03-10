package com.karakoum.mowers.common.util;

import java.util.ArrayList;

import org.springframework.data.annotation.PersistenceConstructor;

import com.karakoum.mowers.domain.unit.impl.Unit;

public class UnitOrders {
	Unit unit;
	ArrayList<Character> orders;
	
	public UnitOrders(Unit unit) {
		this.unit = unit;
		orders = new ArrayList<Character>();
	}
	
	@PersistenceConstructor
	public UnitOrders(Unit unit, ArrayList<Character> orders) {
		this(unit);
		this.orders = orders;
	}
	
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	
	public ArrayList<Character> getOrders() {
		return orders;
	}
	
	public void setOrders(ArrayList<Character> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Character order) {
		this.orders.add(order);
	}
	
	
}
