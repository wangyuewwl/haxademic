package com.haxademic.sketch.shader;

import com.haxademic.app.haxmapper.textures.BaseTexture;
import com.haxademic.app.haxmapper.textures.TextureEQConcentricCircles;
import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.OpenGLUtil;
import com.haxademic.core.draw.image.PerlinTexture;
import com.haxademic.core.draw.shapes.PShapeUtil;
import com.haxademic.core.file.FileUtil;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.PShader;

public class ShaderVertexSvgDeform
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }


	PImage texture;
	BaseTexture audioTexture;
	PerlinTexture perlinTexture;
	PShape shape;
	float angle;
	PShader texShader;
	float _frames = 60;
	boolean _is3d = true;
	boolean _isAudio = false;


	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.FILLS_SCREEN, "false" );
		p.appConfig.setProperty( AppSettings.WIDTH, "640" );
		p.appConfig.setProperty( AppSettings.HEIGHT, "640" );
		
		p.appConfig.setProperty( AppSettings.RENDERING_MOVIE, "false" );
		
		p.appConfig.setProperty( AppSettings.RENDERING_GIF, "false" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_FRAMERATE, "40" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_QUALITY, "15" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_START_FRAME, "3" );
		p.appConfig.setProperty( AppSettings.RENDERING_GIF_STOP_FRAME, ""+Math.round(_frames+2) );

	}

	public void setup() {
		super.setup();	
		p.smooth( OpenGLUtil.SMOOTH_HIGH );
		
		// create dynamic deformation texture
//		audioTexture = new TextureEQGrid(800, 800);
		perlinTexture = new PerlinTexture(p, 200, 200);
		audioTexture = new TextureEQConcentricCircles(200, 200);
		PGraphics displacementMap = (_isAudio == true) ? audioTexture.texture() : perlinTexture.texture();
		
		// create geometry
		shape = p.loadShape( FileUtil.getFile("svg/ello-centered.svg"));
		shape = PShapeUtil.clonePShape(this, shape.getTessellation());
		PShapeUtil.scaleShapeToExtent(shape, p.height * 0.3f);
		float modelExtent = PShapeUtil.getMaxExtent(shape);
		PShapeUtil.addTextureUVToShape(shape, displacementMap);
		shape.setTexture(displacementMap);

		texShader = loadShader(
				FileUtil.getFile("shaders/vertex/brightness-displace-frag-texture.glsl"), 
				FileUtil.getFile("shaders/vertex/brightness-displace-sphere-vert.glsl")
				);
		texShader.set("displacementMap", displacementMap);
		texShader.set("displaceStrength", 0.7f);
	}

	public void drawApp() {
		background(255);

		// rendering
		float percentComplete = ((float)(p.frameCount%_frames)/_frames);
		angle = P.TWO_PI * percentComplete;
		
		PGraphics displacementMap; 
		if(_isAudio == true) {
			audioTexture.update();
			displacementMap = audioTexture.texture();
		} else {
			perlinTexture.update(0.01f, 0.1f, P.sin(angle), P.cos(angle));
			displacementMap = perlinTexture.texture();
		}
		
		// read audio data into offscreen texture
//		OpenGLUtil.setWireframe(p.g, false);
//		OpenGLUtil.setWireframe(p.g, true);
		
		// set center screen & rotate
		translate(width/2, height/2, 0);
		// rotateX(0.3f * P.sin(percentComplete * P.TWO_PI));
//		rotateX(p.frameCount/20f);
//		rotateX(P.PI);
//		rotateY(P.PI);
		rotateY(p.mouseX / 100f);
		
		// set shader properties & set on processing context
		texShader.set("displacementMap", displacementMap);
		p.shader(texShader);  
		p.shape(shape);
		p.resetShader();
	}
}

