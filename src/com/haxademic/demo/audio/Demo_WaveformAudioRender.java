package com.haxademic.demo.audio;

import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.constants.AppSettings;
import com.haxademic.core.file.FileUtil;

public class Demo_WaveformAudioRender
extends PAppletHax {
	public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected void overridePropsFile() {
		p.appConfig.setProperty( AppSettings.FPS, 30 );
		p.appConfig.setProperty( AppSettings.WIDTH, 1280 );
		p.appConfig.setProperty( AppSettings.HEIGHT, 720 );
		p.appConfig.setProperty( AppSettings.RENDERING_MOVIE, true );
		p.appConfig.setProperty( AppSettings.RENDER_AUDIO, true );
		p.appConfig.setProperty( AppSettings.RENDER_AUDIO_FILE, FileUtil.getFile("audio/cacheflowe_bigger_loop.wav") );
	}
	
	public void drawApp() {
		p.background(0);

		// draw waveform
		p.stroke(255);
		p.strokeWeight(3);
		p.noFill();
		float startX = 0;
		float spacing = p.width / 512f;
		p.beginShape();
		for (int i = 0; i < _waveformData._waveform.length; i++ ) {
			float curY = p.height * 0.5f + _waveformData._waveform[i] * 300f;
			p.vertex(startX + i * spacing, curY);
		}
		p.endShape();
	}
}
