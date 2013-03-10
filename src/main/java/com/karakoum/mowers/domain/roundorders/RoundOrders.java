package com.karakoum.mowers.domain.roundorders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.karakoum.mowers.common.util.UnitOrders;
import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.domain.unit.impl.Unit;

@Document
public class RoundOrders implements Iterable<UnitOrders>{
	
	@Id
	protected String id;
	
	protected int roundNumber;
	
	Surface surface;
	List<UnitOrders> units_and_orders;
	
	public RoundOrders() {
		surface = null;
		units_and_orders = new ArrayList<UnitOrders>();
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public String getId() {
		return id;
	}
	
	public Surface getSurface() {
		return surface;
	}
	public void setSurface(Surface surface) {
		this.surface = surface;
	}
	public List<UnitOrders> getUnitsAndOrders() {
		return units_and_orders;
	}
	public void setUnitsAndOrders(List<UnitOrders> units_and_orders) {
		this.units_and_orders = units_and_orders;
	}
	public void addUnitsAndOrders(Unit unit, ArrayList<Character> orders) {
		UnitOrders unitorders = new UnitOrders(unit, orders);
		this.units_and_orders.add(unitorders);
	}
	
	
	public Iterator<UnitOrders> iterator() {        
        Iterator<UnitOrders> unitorders = units_and_orders.iterator();
        return unitorders; 
    }
	
	public ArrayList<Character> getOrdersForUnit(String unitID) {
		for(UnitOrders unitorders:units_and_orders) {
			if (unitorders.getUnit().getId() == unitID) {
				return unitorders.getOrders();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Ordres round "+roundNumber;
	}
	
	
}
