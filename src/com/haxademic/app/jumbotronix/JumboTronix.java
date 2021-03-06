package com.haxademic.app.jumbotronix;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.haxademic.app.haxmapper.textures.BaseTexture;
import com.haxademic.app.haxmapper.textures.TextureEQColumns;
import com.haxademic.app.haxmapper.textures.TextureEQConcentricCircles;
import com.haxademic.app.haxmapper.textures.TextureEQGrid;
import com.haxademic.app.haxmapper.textures.TextureImageTimeStepper;
import com.haxademic.app.haxmapper.textures.TextureScrollingColumns;
import com.haxademic.app.haxmapper.textures.TextureShaderTimeStepper;
import com.haxademic.app.haxmapper.textures.TextureSphereRotate;
import com.haxademic.app.haxmapper.textures.TextureTwistingSquares;
import com.haxademic.app.haxmapper.textures.TextureVideoPlayer;
import com.haxademic.app.haxmapper.textures.TextureWaveformSimple;
import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.filters.BlobOuterMeshFilter;
import com.haxademic.core.draw.image.ImageUtil;
import com.haxademic.core.draw.image.ScreenUtil;
import com.haxademic.core.file.FileUtil;

import processing.core.PGraphics;
import processing.opengl.PShader;
import processing.video.Capture;

public class JumboTronix
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected PGraphics pg;
	protected PGraphics _webcamTexture;
	protected Capture _webCam;
	protected Rectangle _webCamRect;
	
	protected PShader invert;
	protected PShader vignette;
	protected PShader kaleido;
	protected PShader edge;
	protected PShader dotScreen;
	protected PShader mirror;
	protected PShader pixelate;
	protected PShader badtv;
	protected PShader contrast;
	protected PShader brightness;
	protected PShader _chromaKeyFilter;

	protected BlobOuterMeshFilter _blobFilter;

	protected ArrayList<BaseTexture> _texturePool;
	protected Rectangle _textureRect;
	protected int _curTextureIndex = 0;
	

	public void settings() {
		customPropsFile = FileUtil.getHaxademicDataPath() + "properties/jumbotronix.properties";
		super.settings();
	}
	
	public void setup() {
		super.setup();
		pg = P.p.createGraphics( width, height, P.P3D );
		initWebcam();
		initShaders();
		initViz();
	}
	
	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.WIDTH, "1100" );
		p.appConfig.setProperty( AppSettings.HEIGHT, "462" );
		p.appConfig.setProperty( AppSettings.FULLSCREEN, "false" );
		p.appConfig.setProperty( AppSettings.FILLS_SCREEN, "true" );
	}

	protected void initWebcam() {
		// build texture and rectangle to fill screen
		_webcamTexture = P.p.createGraphics( 1280, 720, P.P3D );
		float[] cropFill = ImageUtil.getOffsetAndSizeToCrop( pg.width, pg.height, _webcamTexture.width, _webcamTexture.height, true );
		_webCamRect = new Rectangle( (int) cropFill[0], (int) cropFill[1], (int) cropFill[2], (int) cropFill[3] );
		
		// connect to camera
		String[] cameras = Capture.list();
		if (cameras.length == 0) {
			println("There are no cameras available for capture.");
			exit();
		} else {
			println("Available cameras:");
			for (int i = 0; i < cameras.length; i++) {
				println(cameras[i]);
			}
			String camera = cameras[15];
			_webCam = new Capture(this, camera);
			_webCam.start();
		}
	}
	
	protected void initViz() {
		// get sizing for crop fill textures
		int texW = 800;
		int texH = P.round( 800f * ( (float) p.height / (float) p.width ) );
		
		float[] cropFill = ImageUtil.getOffsetAndSizeToCrop( pg.width, pg.height, texW, texH, true );
		_textureRect = new Rectangle( (int) cropFill[0], (int) cropFill[1], (int) cropFill[2], (int) cropFill[3] );
		
		_texturePool = new ArrayList<BaseTexture>();
		_texturePool.add( new TextureVideoPlayer( texW, texH, "video/bikes/_bike-movies-desktop.m4v" ));
		_texturePool.add( new TextureEQConcentricCircles( texW, texH ) );
		_texturePool.add( new TextureWaveformSimple( texW, texH ) );
		_texturePool.add( new TextureEQGrid( texW, texH ) );
		_texturePool.add( new TextureEQColumns( texW, texH ));
		_texturePool.add( new TextureScrollingColumns( texW, texH ));
		_texturePool.add( new TextureTwistingSquares( texW, texH ));
		_texturePool.add( new TextureSphereRotate( texW, texH ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "wavy-checker-planes.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "bw-eye-jacker-01.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "dots-orbit.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "glowwave.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "bw-simple-sin.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "supershape-2d.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "star-field.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "sin-grey.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "swirl.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "bw-motion-illusion.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "sin-waves.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "lines-scroll-diag.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "iq-iterations-shiny.glsl" ));
		_texturePool.add( new TextureShaderTimeStepper( texW, texH, "bw-kaleido.glsl" ));
		_texturePool.add( new TextureImageTimeStepper( texW, texH ));
//		_texturePool.add( new TextureColorAudioFade( texW, texH ));
//		_texturePool.add( new TextureColorAudioSlide( texW, texH ));

		_blobFilter = new BlobOuterMeshFilter( P.round(1280f / 1f), P.round(720f / 1f) );

	}
	
	protected void initShaders() {
		invert = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/invert.glsl" ); 
		
		kaleido = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/kaleido.glsl" ); 
		kaleido.set("sides", 2.0f);
		kaleido.set("angle", 0.0f);
		
		vignette = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/vignette.glsl" );
		vignette.set("darkness", 0.85f);
		vignette.set("spread", 0.15f);

		edge = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/edges.glsl" ); 
		
		dotScreen = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/dotscreen.glsl" ); 
		dotScreen.set("tSize", 256f, 256f);
		dotScreen.set("center", 0.5f, 0.5f);
		dotScreen.set("angle", 1.57f);
		dotScreen.set("scale", 1f);

		mirror = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/mirror.glsl" ); 

		pixelate = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/pixelate.glsl" ); 
		pixelate.set("divider", p.width/20f, p.height/20f);
		
		brightness = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/brightness.glsl" ); 
		brightness.set("brightness", 2f);
		
		contrast = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/contrast.glsl" ); 
		contrast.set("contrast", 2f);
		
		_chromaKeyFilter = loadShader( FileUtil.getHaxademicDataPath()+"shaders/filters/chroma-gpu.glsl" );
		_chromaKeyFilter.set("thresholdSensitivity", 0.65f);
		_chromaKeyFilter.set("smoothing", 0.19f);
		_chromaKeyFilter.set("colorToReplace", 0.48f,0.8f,0.2f);
	}
	
	public void drawApp() {
		p.background( p.midiState.midiCCPercent(11, 7) * 255f, p.midiState.midiCCPercent(12, 7) * 255f, p.midiState.midiCCPercent(13, 7) * 255f );
//		p.shininess(1000f); 
//		p.lights();

		if( _webCam.available() ) _webCam.read(); 
		
		// choose bg texture with midi
		int curTextureIndex = (int) Math.floor( p.midiState.midiCCPercent(15, 7) * _texturePool.size() );
		curTextureIndex = Math.min( curTextureIndex, _texturePool.size() - 1 ); 
		
		if( curTextureIndex != _curTextureIndex ) {
			_texturePool.get(_curTextureIndex).setActive(false);
			_curTextureIndex = curTextureIndex;
			_texturePool.get(_curTextureIndex).setActive(true);
			_texturePool.get(_curTextureIndex).newLineMode();
			_texturePool.get(_curTextureIndex).newRotation();
			_texturePool.get(_curTextureIndex).updateTimingSection();
			_texturePool.get(_curTextureIndex).newMode();
		}
		
		// beat detection texture updates
		if( audioIn.isBeat() == true ) _texturePool.get(_curTextureIndex).updateTiming();
		_texturePool.get(_curTextureIndex).update();

		// update shaders with midi
		if( p.midiState.midiCCPercent(0, 7) != 0 ) brightness.set("brightness", p.midiState.midiCCPercent(0, 7) * 5 );
		if( p.midiState.midiCCPercent(1, 7) != 0 ) contrast.set("contrast", p.midiState.midiCCPercent(1, 7) * 5 );
		float kaleidoSides = P.round( p.midiState.midiCCPercent(2, 7) * 10f );
		kaleido.set("sides", kaleidoSides );
		boolean inverted = ( p.midiState.midiCCPercent(3, 7) > 0.5f );
		
		boolean halftone = ( p.midiState.midiCCPercent(4, 7) > 0.25f && p.midiState.midiCCPercent(4, 7) < 0.5f );
		boolean edged = ( p.midiState.midiCCPercent(4, 7) > 0.5f && p.midiState.midiCCPercent(4, 7) < 0.75f );
		boolean pixelated = ( p.midiState.midiCCPercent(4, 7) > 0.75f );
		if( pixelated ) {
			float pixAmout = P.round(p.midiState.midiCCPercent(14, 7) * 40f);
			pixelate.set("divider", p.width/pixAmout, p.height/pixAmout);
		}
		
		_chromaKeyFilter.set("thresholdSensitivity", p.midiState.midiCCPercent(5, 7));
		_chromaKeyFilter.set("smoothing", p.midiState.midiCCPercent(6, 7));
		_chromaKeyFilter.set("colorToReplace", p.midiState.midiCCPercent(7, 7), p.midiState.midiCCPercent(8, 7), p.midiState.midiCCPercent(9, 7));

		vignette.set("spread", p.midiState.midiCCPercent(10, 7));

		
		// send webcam to its own texture
		_webcamTexture.beginDraw();
		_webcamTexture.clear();
		_webcamTexture.image(_webCam, 0, 0);
		_webcamTexture.endDraw();
				
		// apply webcam filters
		if( inverted ) _webcamTexture.filter(invert);
		_webcamTexture.filter(brightness);
		_webcamTexture.filter(contrast);

		if( kaleidoSides > 0 ) {
			if( kaleidoSides == 3 ) {
				_webcamTexture.filter(mirror);
			} else {
				_webcamTexture.filter(kaleido);
			}
		}
		
		if( halftone ) _webcamTexture.filter(dotScreen);
		if( edged ) _webcamTexture.filter(edge);
		if( pixelated ) _webcamTexture.filter(pixelate);
		
		_webcamTexture.filter(_chromaKeyFilter);
		
		// draw main canvas
		pg.beginDraw();
		pg.background(0);
		pg.image(_texturePool.get(curTextureIndex).texture(), _textureRect.x, _textureRect.y, _textureRect.width, _textureRect.height);
		pg.image(_webcamTexture, _webCamRect.x, _webCamRect.y, _webCamRect.width, _webCamRect.height);
		
		// post effects
		pg.filter(vignette);
		
		pg.endDraw();
		
		// draw canvas to PApplet
		p.image(pg, 0, 0);
	}
	
	public void keyPressed() {
		super.keyPressed();
		
		// screenshot
		if ( key == ' ' ) { 
			String screenshotPath = FileUtil.getHaxademicOutputPath() + "_screenshots/";
			if( FileUtil.fileOrPathExists( screenshotPath ) == false ) FileUtil.createDir( screenshotPath );
			ScreenUtil.saveScreenshot( p, screenshotPath );
		}
	}

}
