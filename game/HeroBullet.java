package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HeroBullet extends FlyingObject{
	Image[] imgs=new Image[4];
	HeroBullet(HeroPlane hPlane) {
		for(int i=0;i<4;i++) {
			try {
				imgs[i]=ImageIO.read(new File("images/hero_bullet_"+(i+1)+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		img=imgs[0];
		this.x=hPlane.x+hPlane.img.getWidth(null)/2;
		this.y=hPlane.y;
		this.xSpeed=0;
		this.ySpeed=-5;
	}
	void move() {
		this.y+=this.ySpeed;
	}	
}
