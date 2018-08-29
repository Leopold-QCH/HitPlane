package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyBullet extends FlyingObject{
	Image imgs[]=new Image[2];
	int img_index=0 ; //Э���л��ӵ�ͼƬ���γɶ���Ч��
	EnemyBullet(EnemyPlane ePlane){	//���췽��
		try {
			imgs[0]=ImageIO.read(new File("images/enemy_bullet_1.gif"));
			imgs[1]=ImageIO.read(new File("images/enemy_bullet_2.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//��ʼ���̳й����ĸ��������
		img=imgs[0];
		this.x=ePlane.x+
				ePlane.img.getWidth(null)/2-this.img.getWidth(null)/2;
		this.y=ePlane.y+
				ePlane.img.getHeight(null)+10;//10Ϊ�л����ӵ�֮���΢������
		this.xSpeed=ePlane.xSpeed+1; //�ӵ��ȵл���΢��һЩ����ͬ
		this.ySpeed=ePlane.ySpeed+2;
	}
	public void move() {
		// TODO Auto-generated method stub
		this.y+=this.ySpeed;
		img=imgs[(int) (Math.random()*2)];
	}
}
