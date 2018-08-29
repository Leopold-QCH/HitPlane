package game;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HeroPlane extends FlyingObject{
	HeroPlane(){
		try {
			img=ImageIO.read(new File("images/hero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		x=GamePanel.PANEL_WIDTH/2-img.getWidth(null)/2;
		y=GamePanel.PANEL_HEIGHT-img.getHeight(null)-50; //50为英雄机与面板底部的微调距离
		xSpeed=5; //键盘左右移动英雄机的速度（该功能暂时未实现）
		ySpeed=5; //键盘上下移动英雄机的速度（该功能暂时未实现）	
	}

	public void moveTo(int x, int y) {
		this.x=x;
		this.y=y;
	}
	HeroBullet shoot() {
		return new HeroBullet(this);
	}
}
