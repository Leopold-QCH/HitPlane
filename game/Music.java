package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Music extends Applet {
	private static final long serialVersionUID = 1L;
	AudioClip au;
	AudioClip[] aus=new AudioClip[3];
	Music(){
		
		try {
			URL SoundFile=new URL("file:music/bg.wav");
			au=Applet.newAudioClip(SoundFile);
			aus[0]=Applet.newAudioClip(new URL("file:music/hero_bullet.wav"));
			aus[1]=Applet.newAudioClip(new URL("file:music/hero_bomb.wav"));
			aus[2]=Applet.newAudioClip(new URL("file:music/enemy_bomb.wav"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start(){
	au.loop();
	}
	public void stop(){
	au.stop();
	}
}
