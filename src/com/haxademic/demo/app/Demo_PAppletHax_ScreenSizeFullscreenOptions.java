package com.haxademic.demo.app;

import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.draw.context.DrawUtil;
import com.haxademic.core.math.easing.EasingFloat;

public class Demo_PAppletHax_ScreenSizeFullscreenOptions
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }
	
	public float easeFactor = 6f;
	protected EasingFloat _easingX = new EasingFloat(0, 6f);
	protected EasingFloat _easingY = new EasingFloat(0, 6f);

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.FPS, 90 );
		// setScreenSize();
		// setFullscreen();
		// setFullscreenSpecificMonitor();
		// setFillAllScreens();
		setUndecoratedWithScreenPosition(true);
	}

	protected void setScreenSize() {
		p.appConfig.setProperty( AppSettings.WIDTH, 540 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 320 );
		p.appConfig.setProperty( AppSettings.APP_NAME, "Screen Size Tests" );
	}
	
	protected void setFullscreen() {
		p.appConfig.setProperty( AppSettings.FULLSCREEN, true );
	}
	
	protected void setFullscreenSpecificMonitor() {
		p.appConfig.setProperty( AppSettings.FULLSCREEN, true );
		p.appConfig.setProperty( AppSettings.FULLSCREEN_SCREEN_NUMBER, 2 );
	}
	
	protected void setFillAllScreens() {
		p.appConfig.setProperty( AppSettings.SPAN_SCREENS, true );
	}
	
	protected void setUndecoratedWithScreenPosition(boolean alwaysOnTop) {
		p.appConfig.setProperty( AppSettings.FULLSCREEN, true );
		p.appConfig.setProperty( AppSettings.SCREEN_X, 100 );
		p.appConfig.setProperty( AppSettings.SCREEN_Y, 100 );
		p.appConfig.setProperty( AppSettings.WIDTH, 540 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 320 );
		p.appConfig.setProperty( AppSettings.ALWAYS_ON_TOP, alwaysOnTop );
	}
	
	

	public void setup() {
		super.setup();	
	}

	public void drawApp() {
		background(0);
		
		_easingX.setEaseFactor(easeFactor);
		_easingY.setEaseFactor(easeFactor);
		
		_easingX.setTarget(p.mouseX);
		_easingY.setTarget(p.mouseY);

		_easingX.update();
		_easingY.update();
		
		DrawUtil.setDrawCenter(p);
		p.fill(255);
		p.ellipse(_easingX.value(), _easingY.value(), 40, 40);
	}

}
