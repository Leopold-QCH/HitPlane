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
		y=GamePanel.PANEL_HEIGHT-img.getHeight(null)-50; //50ΪӢ�ۻ������ײ���΢������
		xSpeed=5; //���������ƶ�Ӣ�ۻ����ٶȣ��ù�����ʱδʵ�֣�
		ySpeed=5; //���������ƶ�Ӣ�ۻ����ٶȣ��ù�����ʱδʵ�֣�	
	}

	public void moveTo(int x, int y) {
		this.x=x;
		this.y=y;
	}
	HeroBullet shoot() {
		return new HeroBullet(this);
	}
}
