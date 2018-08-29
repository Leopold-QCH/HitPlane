package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 飞行物类
 */
public class FlyingObject {
	Image img; //图片
	int x;	//画图片时的x坐标
	int y;	//画图片时的y坐标
	int xSpeed; //横坐标移动速度
	int ySpeed; //纵坐标移动速度
	void draw(Graphics g){ //绘画方法
		g.drawImage(this.img, this.x, this.y, null);
	}
}
