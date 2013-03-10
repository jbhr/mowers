package com.karakoum.mowers.domain.unit.impl;

import java.io.Serializable;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * L'unité de type Tondeuse. 
 * Les mouvements et orientations possible correspondent à ceux définis par défaut dans la classe abstraite Unit:
 * N, E, S, O pour les orientations
 * G, D, A pour les mouvements 
 * 
 * @author Jean-Baptiste
 *
 */

@Document
public class Mower extends Unit implements Serializable {
	
	private static final long serialVersionUID = 354054054054L;
	
	
	public Mower(String name, int orig_x, int orig_y, char orig_orientation_as_char) {
		this(name);
		this.positionX = orig_x;
		this.positionY = orig_y;
		setOrientationAsChar(orig_orientation_as_char);
	}
	
	@PersistenceConstructor
	public Mower(String name) {
		super(name);
	}
	
	
	public int calculatePositionXForward() {
		
		Character orientation = getOrientationAsChar();
		
		int new_x = getPositionX();
		
		switch (orientation) {
		case 'E':
			new_x += 1;
			break;
		case 'W':
			new_x -= 1;
			break;
		}
		
		return new_x;
	}
	
	public int calculatePositionYForward() {
		
		Character orientation = getOrientationAsChar();
		
		int new_y = getPositionY();
		
		switch (orientation) {
		case 'N':
			new_y += 1;
			break;
		case 'S':
			new_y -= 1;
			break;
		}
		
		return new_y;
	}


	@Override
	public void executeMoveOrder(char moveOrder) {
		
		super.executeMoveOrder(moveOrder);
		
	}
	
	
	
	
	
}
