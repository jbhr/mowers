package com.karakoum.mowers.domain.surface.impl;



public abstract class SurfaceRectangulaire extends Surface {
	
	
	public SurfaceRectangulaire(String name, int mWidth, int mHeight) {
		super(name, mWidth, mHeight);
	}
	
	@Override
	public boolean isCoordinateAccessible(int x, int y) {
		return (x >= 0 && y >= 0 && x <= getmWidth() && y <= getmHeight());
	}
	
	
	@Override
	public boolean isMovementPossible(int x_orig, int y_orig, int x_dest,
			int y_dest) {
		
		if (!isCoordinateAccessible(x_dest, y_dest)) {
			return false;
		}
		return true;
	}
	
	
	
	
}
