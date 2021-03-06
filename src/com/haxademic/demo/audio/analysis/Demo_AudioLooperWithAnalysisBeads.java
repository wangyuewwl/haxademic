package com.haxademic.demo.audio.analysis;

import com.haxademic.core.app.P;
import com.haxademic.core.app.PAppletHax;
import com.haxademic.core.audio.analysis.AudioPlayerBeads;
import com.haxademic.core.audio.analysis.AudioStreamData;
import com.haxademic.core.constants.AppSettings;

import beads.AudioContext;

public class Demo_AudioLooperWithAnalysisBeads
extends PAppletHax { public static void main(String args[]) { PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected AudioContext ac;
	protected AudioPlayerBeads[] loops;
	
	protected void overridePropsFile() {
		p.appConfig.setProperty(AppSettings.WIDTH, 900);
		p.appConfig.setProperty(AppSettings.HEIGHT, 600);
	}
	
	public void setupFirstFrame() {
		ac = new AudioContext();
		ac.start();
	
		// oad samples
		loops = new AudioPlayerBeads[] {
				new AudioPlayerBeads(ac, "audio/crusher-loops/kicks.wav"),
				new AudioPlayerBeads(ac, "audio/crusher-loops/snares.wav"),
				new AudioPlayerBeads(ac, "audio/crusher-loops/bass-selekta.wav"),
				new AudioPlayerBeads(ac, "audio/crusher-loops/fnc-01.wav"),
				new AudioPlayerBeads(ac, "audio/crusher-loops/fx05.wav"),
				new AudioPlayerBeads(ac, "audio/crusher-loops/contender.wav"),
		};
		
	}
	
	public void keyPressed() {
		super.keyPressed();
		if(p.key == ' ') {
			for(int i=0; i < loops.length; i++) {
				loops[i].start();
			}
		}
	}
	
	public void drawApp() {
		background(0);
		stroke(255);
		
		// update analysis
		for(int i=0; i < loops.length; i++) {
			loops[i].update();
			if(loops[i].looped()) P.println("LOOPED:", i);
		}
		
		// draw debug
		loops[0].audioData().drawDebug(p.g);
		p.translate(AudioStreamData.debugW, 0);
		loops[1].audioData().drawDebug(p.g);
		p.translate(AudioStreamData.debugW, 0);
		loops[2].audioData().drawDebug(p.g);
		p.translate(-AudioStreamData.debugW * 2, AudioStreamData.debugH);
		loops[3].audioData().drawDebug(p.g);
		p.translate(AudioStreamData.debugW, 0);
		loops[4].audioData().drawDebug(p.g);
		p.translate(AudioStreamData.debugW, 0);
		loops[5].audioData().drawDebug(p.g);
	}

}

