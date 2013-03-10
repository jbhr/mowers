package com.karakoum.mowers.domain.surface.impl;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Jardin extends SurfaceRectangulaire implements Serializable {
	
	private static final long serialVersionUID = 354054054066L;
	
	
	public Jardin(String name, int mWidth, int mHeight) {
		super(name, mWidth, mHeight);
	}
	
	
}
