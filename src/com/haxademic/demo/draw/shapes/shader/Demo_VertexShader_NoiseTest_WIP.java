package com.haxademic.demo.draw.shapes.shader;

import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.draw.shapes.PShapeUtil;
import com.haxademic.core.draw.shapes.Shapes;
import com.haxademic.core.file.DemoAssets;
import com.haxademic.core.file.FileUtil;

import processing.core.PShape;
import processing.opengl.PShader;

public class Demo_VertexShader_NoiseTest_WIP 
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected PShape obj;
	protected PShape sheet;
	protected PShader shader;
	
	protected void overridePropsFile() {
		int FRAMES = 1000;
		p.appConfig.setProperty(AppSettings.LOOP_FRAMES, FRAMES);
		p.appConfig.setProperty(AppSettings.RENDERING_MOVIE, false);
		p.appConfig.setProperty(AppSettings.RENDERING_MOVIE_START_FRAME, 1 + FRAMES);
		p.appConfig.setProperty(AppSettings.RENDERING_MOVIE_STOP_FRAME, 1 + FRAMES * 2);
	}

	protected void setupFirstFrame() {
		// build obj PShape and scale to window
		obj = DemoAssets.objSkullRealistic();
		sheet = Shapes.createSheet(40, 1000, 1000);
		
		// normalize shape
		PShapeUtil.centerShape(obj);
		PShapeUtil.scaleShapeToHeight(obj, p.height * 0.60f);
		PShapeUtil.setRegistrationOffset(obj, 0, -0.04f, 0);
		
		// load shader
		shader = p.loadShader(
			FileUtil.getFile("shaders/vertex/noise-frag.glsl"), 
			FileUtil.getFile("shaders/vertex/noise-vert.glsl")
		);
		
		// Set UV coords & set texture on obj.
		PShapeUtil.addTextureUVSpherical(obj, null);
	}

	public void drawApp() {
		background(0);
		DrawUtil.setCenterScreen(p);

		// use shader
		shader.set("time", loop.progressRads());
		shader.set("lightDir", 0.5f, 0.5f * P.sin(loop.progressRads()), 0.9f);
		shader.set("lightsOn", 0);
		shader.set("lightAmbient", 0.1f, 0.1f * P.sin(loop.progressRads()), 0.5f);
		p.shader(shader);
		p.shape(sheet);
		p.rotateY(0.3f * P.sin(loop.progressRads()));
		shader.set("lightsOn", 1);
		p.shape(obj);
		p.resetShader();
	}
		
}