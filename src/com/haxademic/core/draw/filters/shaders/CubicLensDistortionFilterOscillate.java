package com.haxademic.core.draw.filters.shaders;

import processing.core.PApplet;

public class CubicLensDistortionFilterOscillate
extends BaseFilter {

	public static CubicLensDistortionFilterOscillate instance;
	
	public CubicLensDistortionFilterOscillate(PApplet p) {
		super(p, "shaders/filters/cubic-lens-distortion-oscillate.glsl");
	}
	
	public static CubicLensDistortionFilterOscillate instance(PApplet p) {
		if(instance != null) return instance;
		instance = new CubicLensDistortionFilterOscillate(p);
		return instance;
	}

}
