package com.haxademic.sketch.pshape;

import com.haxademic.core.app.AppSettings;
import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.draw.shapes.PShapeSolid;
import com.haxademic.core.draw.util.DrawUtil;
import com.haxademic.core.draw.util.OpenGLUtil;
import com.haxademic.core.draw.util.PShapeUtil;
import com.haxademic.core.system.FileUtil;

import processing.core.PImage;
import processing.core.PShape;

public class PShapeObjDeformTest2 
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected PShape obj;
	protected PShapeSolid objSolid;
	protected PImage img;
	protected float _frames = 60;

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.WIDTH, 800 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 800 );
		p.appConfig.setProperty( AppSettings.SMOOTHING, AppSettings.SMOOTH_HIGH );
		p.appConfig.setProperty( AppSettings.FILLS_SCREEN, "false" );
		p.appConfig.setProperty( AppSettings.RENDERING_MOVIE, "false" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF, "false" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_FRAMERATE, "45" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_QUALITY, "15" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_START_FRAME, "2" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_STOP_FRAME, ""+Math.round(_frames+1) );
	}

	public void setup() {
		super.setup();	

		// load texture
		img = p.loadImage(FileUtil.getFile("images/justin-spike-portrait-02-smaller.png"));
		
		// build obj PShape and scale to window
		obj = p.loadShape( FileUtil.getFile("models/unicorn-head-lowpoly.obj"));	
		PShapeUtil.scaleObjToExtent(obj, p.height * 0.4f);
		
		// add UV coordinates to OBJ
		float modelExtent = PShapeUtil.getObjMaxExtent(obj);
		PShapeUtil.addTextureUVToObj(obj, img, modelExtent);
//		obj.setTexture(img);
		
		// build solid, deformable PShape object
		objSolid = new PShapeSolid(obj);
	}

	public void drawApp() {
		background(255);
		
		float percentComplete = ((float)(p.frameCount%_frames)/_frames);
		
		// setup lights
//		p.lightSpecular(230, 230, 230); 
//		p.directionalLight(200, 200, 200, -0.0f, -0.0f, 1); 
//		p.directionalLight(200, 200, 200, 0.0f, 0.0f, -1); 
//		p.specular(color(255)); 
//		p.shininess(5.0f);
		
		DrawUtil.setBetterLights(p);

		// rotate
		p.translate(p.width/2f, p.height * 0.6f);
		p.rotateY(P.PI/2f + P.sin(p.frameCount / 40f));
		p.rotateZ(P.PI);
		p.rotateX(-P.PI/2f);

		
		// draw!
//		objSolid.updateWithTrig(true, percentComplete * 2f, 0.04f, 17.4f);
		objSolid.updateWithAudio(true);
		p.noStroke();
		p.shape(objSolid.shape());
	}
		
}