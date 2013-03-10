package com.karakoum.mowers.domain.unit.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.karakoum.mowers.domain.surface.impl.Surface;
import com.karakoum.mowers.exception.MowersException;

/**
 * Classe abstraite pour une unit�, c�d un �l�ment qui peut se d�placer dans une surface.
 * 
 * L'orientation est sp�cifi�e par un indice correspondan � une liste des orientations possibles.
 * Le traitement du mouvement 'pivoter' est simple: pour pivoter � gauche, on d�cr�mente l'indice,
 * et on l'incr�mente pour pivoter � droite.   
 * 
 * @author Jean-Baptiste
 *
 */
public abstract class Unit  {
	@Id
	protected String id;
	
	protected String name;
	protected int positionX;
	protected int positionY;
	protected int orientationIndice;
	
	
	protected List<Character> orientationsAvailable;
	protected List<Character> moveOrdersAvailable;
	
	protected Surface surface;
	
	public Unit(String name) {
		surface = null;
		this.name = name;
		this.positionX = 0;
		this.positionY = 0;
		this.orientationIndice = 0;
		
		orientationsAvailable = new ArrayList<Character>();
		orientationsAvailable.add('N');
		orientationsAvailable.add('E');
		orientationsAvailable.add('S');
		orientationsAvailable.add('W');
		
		moveOrdersAvailable = new ArrayList<Character>();
		moveOrdersAvailable.add('G');
		moveOrdersAvailable.add('D');
		moveOrdersAvailable.add('A');
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPositionX() {
		return positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}
	
	public int getOrientationIndice() {
		return orientationIndice;
	}
	
	public Surface getSurface() {
		return surface;
	}
	public void setSurface(Surface surface, int orig_x, int orig_y, int orig_orientation_indice) {
		this.surface = surface;
		this.positionX = orig_x;
		this.positionY = orig_y;
		this.orientationIndice = orig_orientation_indice;
	}
	
	
	public char getOrientationAsChar() {
		return orientationsAvailable.get(orientationIndice);
	}
	public void setOrientationAsChar(char orientation) {
		int tmp_orientation_indice = orientationsAvailable.indexOf(orientation);
		if (tmp_orientation_indice >= 0) {
			orientationIndice  = tmp_orientation_indice;
		}
	}
	
	public void rotateLeft() throws MowersException {
		// On utilise le modulo pour pivoter � gauche. Attention aux modulo n�gatifs en java
		orientationIndice = (orientationIndice - 1) % (orientationsAvailable.size());
		if (orientationIndice < 0) orientationIndice += orientationsAvailable.size();
	}
	
	public void rotateRight() throws MowersException {
		// On utilise le modulo pour pivoter � droite
		orientationIndice = (orientationIndice + 1) % (orientationsAvailable.size());
	}
	
	public void moveForward() throws MowersException {
		
		if (surface == null) {
			throw new MowersException("Impossible de d�placer l'unit� "+toString()+" car elle n'est pas d�ploy�e sur une surface.");
		}
		
		int new_x = calculatePositionXForward();
		int new_y = calculatePositionYForward();
		
		// mouvement en avant uniquement si la 'case' est disponible
		if (surface.isCoordinateAccessible(new_x, new_y)) {
			if (surface.isMovementPossible(getPositionX(), getPositionY(), new_x, new_y)) {
				positionX = new_x;
				positionY = new_y;
			} else {
				throw new MowersException("Impossible de d�placer l'unit� "+toString()+" depuis ("+getPositionX()+","+getPositionY()+") vers ("+new_x+","+new_y+": mouvement impossible dans la surface "+surface.toString());
			}
		} else {
			throw new MowersException("Impossible de d�placer l'unit� "+toString()+" sur les coordonn�es: ("+new_x+","+new_y+"): emplacement indisponible sur la surface "+surface.toString());
		}
	}
	
	
	
	
	public List<Character> getMoveOrdersAvailable() {
		return moveOrdersAvailable;
	}
	
	public List<Character> getOrientationsAvailable() {
		return orientationsAvailable; 
	}
	
	
	public abstract int calculatePositionXForward();
	
	public abstract int calculatePositionYForward();
	
	public void executeMoveOrder(char movementCode) throws MowersException {
		
		if (!moveOrdersAvailable.contains(movementCode)) {
			throw new MowersException("Impossible d'ex�cuter l'ordre '"+movementCode+"': ce dernier n'est pas autoris� pour l'unit� "+toString());
		}
		
		// Comportements par d�faut: pivot gauche / droit, avancer
		switch (movementCode) { 
		case 'G':
			rotateLeft();
			break;
		case 'D':
			rotateRight();
			break;
		case 'A':
			moveForward();
		}
	}
	
	
	
	@Override
	public String toString() {
		return "Unit� "+getName()+": coordonn�es=(" +getPositionX()+","+getPositionY()+") orientation='"+getOrientationAsChar()+"'";
	}
	
	
	
}
