package com.karakoum.mowers.domain.surface.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.karakoum.mowers.domain.unit.impl.Unit;


public abstract class Surface {
	@Id
	protected String id;
	
	protected String name;
	protected int mWidth;
	protected int mHeight;
	
	
	// Observers
	@DBRef
	protected List<Unit> units;
	
	public String getId() {
		return id;
	}
	public void setId(String objectId) {
		this.id = objectId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getmWidth() {
		return mWidth;
	}
	public void setmWidth(int maxCoordinateX) {
		this.mWidth = maxCoordinateX;
	}
	public int getmHeight() {
		return mHeight;
	}
	public void setmHeight(int maxCoordinateY) {
		this.mHeight = maxCoordinateY;
	}
	
	
	public List<Unit> getUnits() {
		return units;
	}
	
	public Surface(String name, int mWidth, int mHeight) {
		units = new ArrayList<Unit>();
		this.name = name;
		this.mWidth = Math.max(0, mWidth);
		this.mHeight = Math.max(0, mHeight);
	}
	
	public void attachUnit(Unit unit, int orig_x, int orig_y, int orig_orientation_indice) {
		if (isCoordinateAccessible(orig_x, orig_y)) {
			unit.setSurface(this, orig_x, orig_y, orig_orientation_indice);
			if (!units.contains(unit)) {
				units.add(unit);
			}
		}
	}
	
	public boolean containsUnit(Unit unit) {
		return units.contains(unit);
	}
	

	public abstract boolean isCoordinateAccessible(int x, int y);
	public abstract boolean isMovementPossible(int x_orig, int y_orig, int x_dest,
			int y_dest);
	
	
	public String toString() {
		return "Surface "+getName()+" (0,0) -> ("+mWidth+","+mHeight+")";
	}
	
	
	
}
