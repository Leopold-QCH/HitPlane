package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ��������
 */
public class FlyingObject {
	Image img; //ͼƬ
	int x;	//��ͼƬʱ��x����
	int y;	//��ͼƬʱ��y����
	int xSpeed; //�������ƶ��ٶ�
	int ySpeed; //�������ƶ��ٶ�
	void draw(Graphics g){ //�滭����
		g.drawImage(this.img, this.x, this.y, null);
	}
}
