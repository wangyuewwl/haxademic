package com.haxademic.core.draw.filters.shaders;

import processing.core.PApplet;

public class ContrastFilter
extends BaseFilter {

	public static ContrastFilter instance;
	
	public ContrastFilter(PApplet p) {
		super(p, "shaders/filters/contrast.glsl");
		setContrast(1f);
	}
	
	public static ContrastFilter instance(PApplet p) {
		if(instance != null) return instance;
		instance = new ContrastFilter(p);
		return instance;
	}
	
	public void setContrast(float contrast) {
		shader.set("contrast", contrast);
	}
	
}
