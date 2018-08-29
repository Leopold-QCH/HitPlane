package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyBullet extends FlyingObject{
	Image imgs[]=new Image[2];
	int img_index=0 ; //协助切换子弹图片，形成动画效果
	EnemyBullet(EnemyPlane ePlane){	//构造方法
		try {
			imgs[0]=ImageIO.read(new File("images/enemy_bullet_1.gif"));
			imgs[1]=ImageIO.read(new File("images/enemy_bullet_2.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化继承过来的父类的属性
		img=imgs[0];
		this.x=ePlane.x+
				ePlane.img.getWidth(null)/2-this.img.getWidth(null)/2;
		this.y=ePlane.y+
				ePlane.img.getHeight(null)+10;//10为敌机与子弹之间的微调距离
		this.xSpeed=ePlane.xSpeed+1; //子弹比敌机稍微快一些，下同
		this.ySpeed=ePlane.ySpeed+2;
	}
	public void move() {
		// TODO Auto-generated method stub
		this.y+=this.ySpeed;
		img=imgs[(int) (Math.random()*2)];
	}
}
