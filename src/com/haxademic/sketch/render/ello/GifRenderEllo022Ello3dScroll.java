package com.haxademic.sketch.render.ello;

import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.draw.context.OpenGLUtil;
import com.haxademic.core.file.FileUtil;

import controlP5.ControlP5;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;

public class GifRenderEllo022Ello3dScroll
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }
	
	public float speed = 0;
	public float angle = 0;
	protected ControlP5 _cp5;
	
	float _frames = 20;

	
	protected float _x = 0;
	protected float _y = 0;
	protected float _z = 0;
	protected float _tileSize = 450;
	
	PShape _logo;
	PImage _logoImg;
	PGraphics _logoG;

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.FPS, "30" );
		p.appConfig.setProperty( AppSettings.FILLS_SCREEN, "false" );
		
		p.appConfig.setProperty( AppSettings.WIDTH, "640" );
		p.appConfig.setProperty( AppSettings.HEIGHT, "480" );

		p.appConfig.setProperty( AppSettings.RENDERING_MOVIE, "false" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF, "true" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_FRAMERATE, "60" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_QUALITY, "15" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_START_FRAME, "2" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_STOP_FRAME, ""+Math.round(_frames+1) );
	}

	public void setup() {
		super.setup();	
		p.smooth( OpenGLUtil.SMOOTH_HIGH );

		_logo = p.loadShape(FileUtil.getHaxademicDataPath()+"svg/ello-filled.svg");
		_logoImg = p.loadImage(FileUtil.getHaxademicDataPath()+"images/ello.png");
		
		_logoG = p.createGraphics((int)_tileSize, (int)_tileSize);
		_logoG.beginDraw();
		_logoG.image(_logoImg, 0, 0, (int)_tileSize, (int)_tileSize);
		_logoG.endDraw();

//		_cp5 = new ControlP5(this);
//		_cp5.addSlider("speed").setPosition(20,20).setWidth(200).setRange(0,30);
//		_cp5.addSlider("angle").setPosition(20,50).setWidth(200).setRange(0.55f,1f);
		
		_x = p.width / 2;
		_z = 0;
	}

	public void drawApp() {
		background(255);
		DrawUtil.setDrawCenter(p);
		
		float frameRadians = P.TWO_PI / _frames;
		float percentComplete = ((float)(p.frameCount%_frames)/_frames);
		float radiansComplete = P.TWO_PI * percentComplete;

//		_y = p.height * 0.65f + (p.height * 0.05f * P.sin(radiansComplete));
		_y = p.height * 0.65f;
		_z = percentComplete * _tileSize * 1;

//		_y = p.height * angle;
//		_z += speed;
//		_z = _z % _tileSize;
		
		p.pushMatrix();
		
		DrawUtil.resetPImageAlpha(p);
		
		float curZ = _z + _tileSize * 4; // start a little behind the camera
		while (curZ > -4000) {
			curZ -= _tileSize;
			
			DrawUtil.setPImageAlpha(p, 1 + curZ/4000);
			
			// draw road tiles
			p.pushMatrix();
			p.translate(_x, _y, curZ);
			p.rotateX(P.PI/2f);
			p.image(_logoG, 0, 0, _tileSize, _tileSize);
			p.popMatrix();
			
			// draw extra elements coming at you
			p.pushMatrix();
			p.translate(_x + _tileSize * 0.8f, _y - _tileSize/2f, curZ);
			p.rotateY(P.PI/2f);
			p.image(_logoG, 0, 0, _tileSize, _tileSize);
			p.popMatrix();
			
			p.pushMatrix();
			p.translate(_x - _tileSize * 0.8f, _y - _tileSize/2f, curZ);
			p.rotateY(P.PI/-2f);
			p.image(_logoG, 0, 0, _tileSize, _tileSize);
			p.popMatrix();
			
		}
		
		
		p.popMatrix();
		
		
		if( p.frameCount == _frames * 2 + 2 ) {
			if(p.appConfig.getBoolean("rendering", false) ==  true) {				
				movieRenderer.stop();
				P.println("render done!");
			}
		}

	}

}
