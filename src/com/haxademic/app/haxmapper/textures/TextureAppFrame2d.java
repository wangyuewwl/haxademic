package com.haxademic.app.haxmapper.textures;

import java.util.ArrayList;

import processing.core.PConstants;

import com.haxademic.core.app.P;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.math.easing.EasingFloat;

public class TextureAppFrame2d 
extends BaseTexture {

	protected ArrayList<EasingFloat> _radii;

	public TextureAppFrame2d( int width, int height ) {
		super();

		buildGraphics( width, height );
		
		_radii = new ArrayList<EasingFloat>();
		for( int i=0; i < 8; i++ ) {
			_radii.add( new EasingFloat( 1f, 4 ) );
		}
	}
	
	public void newLineMode() {
		float cornerMult = P.p.random( 0.8f, 1.3f );
		float sideMult = ( cornerMult >= 1f ) ? P.p.random( 0.8f, 1.0f ) : P.p.random( 1.0f, 1.3f );
		for( int i=0; i < 8; i++ ) {
			if( i % 2 == 0 )
				_radii.get( i ).setTarget( cornerMult );
			else
				_radii.get( i ).setTarget( sideMult );
		}
	}

	public void updateDraw() {
		_texture.clear();
		
		DrawUtil.resetGlobalProps( _texture );
		DrawUtil.setCenterScreen( _texture );
		_texture.pushMatrix();
		
		_texture.rectMode(PConstants.CENTER);
		_texture.noStroke();
				
		// ease vertex multipliers
		for( int i=0; i < 8; i++ ) {
			_radii.get( i ).update();
		}
		
		// start outer for wraparound
		float outerMult = 3;
		float halfW = _texture.width/2f;
		float halfH = _texture.height/2f;
		_texture.fill( 0 );
		_texture.noStroke();
		
		// draw in halves - drawing all at once wasn't filling properly
		// draw frame right side		
		_texture.beginShape();
		_texture.vertex( 0, -_texture.height * outerMult );
		_texture.vertex( 0, -halfH * _radii.get( 0 ).value() );
		_texture.vertex( halfW * _radii.get( 1 ).value(), -halfH * _radii.get( 1 ).value() );
		_texture.vertex( halfW * _radii.get( 2 ).value(), 0 );
		_texture.vertex( halfW * _radii.get( 3 ).value(), halfH * _radii.get( 3 ).value() );
		_texture.vertex( 0, halfH * _radii.get( 4 ).value() );
		_texture.vertex( 0, _texture.height * outerMult );
		_texture.vertex( _texture.width * outerMult, _texture.height * outerMult );
		_texture.vertex( _texture.width * outerMult, -_texture.height * outerMult );
		_texture.vertex( 0, -_texture.height * outerMult );
		_texture.endShape(P.CLOSE);

		// draw frame left side		
		_texture.beginShape();
		_texture.vertex( 0, -_texture.height * outerMult );
		_texture.vertex( 0, -halfH * _radii.get( 0 ).value() );
		_texture.vertex( -halfW * _radii.get( 7 ).value(), -halfH * _radii.get( 7 ).value() );
		_texture.vertex( -halfW * _radii.get( 6 ).value(), 0 );
		_texture.vertex( -halfW * _radii.get( 5 ).value(), halfH * _radii.get( 5 ).value() );
		_texture.vertex( 0, halfH * _radii.get( 4 ).value() );
		_texture.vertex( 0, _texture.height * outerMult );
		_texture.vertex( -_texture.width * outerMult, _texture.height * outerMult );
		_texture.vertex( -_texture.width * outerMult, -_texture.height * outerMult );
		_texture.vertex( 0, -_texture.height * outerMult );
		_texture.endShape(P.CLOSE);
		
		_texture.popMatrix();
		
//		float scaleVal = P.constrain( 0.1f * P.p.audioIn.getEqBand( P.floor(_spectrumInterval * spectrumIndex) ), 0, 1 );
	}
}
