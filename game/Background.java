package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 游戏背景类
 */
public class Background extends FlyingObject{
	Image img_ready; //游戏准备就绪图片
	Image imgs[]=new Image[3]; //3关的背景图片
	Image img_over; //游戏结束图片
	
	Background(){ //构造方法
		try {
			//本类专有属性初始化
			img_ready=ImageIO.read(new File("images/background_start.png") );
			imgs[0]=ImageIO.read(new File("images/background_1.png") );
			imgs[1]=ImageIO.read(new File("images/background_2.png") );
			imgs[2]=ImageIO.read(new File("images/background_3.png") );
			img_over=ImageIO.read(new File("images/background_end.png") );
			
			//对父类继承过来的属性初始化
			img=img_ready;
			x=0;
			y=0;
			xSpeed=0;
			ySpeed=2;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void move(){	//移动
		this.y+=this.ySpeed;
		if(this.y>0){
			this.y=GamePanel.PANEL_HEIGHT-img.getHeight(null);
		}
	}

	public void change(int status) {
		if(status==GamePanel.GAME_RUNNING){
			img=imgs[0];
			y=GamePanel.PANEL_HEIGHT-img.getHeight(null);
		}else if(status==GamePanel.GAME_OVER) {
			img=img_over;
			y=0;
		}
		
	}
	
}
