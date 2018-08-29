package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ��Ϸ������
 */
public class Background extends FlyingObject{
	Image img_ready; //��Ϸ׼������ͼƬ
	Image imgs[]=new Image[3]; //3�صı���ͼƬ
	Image img_over; //��Ϸ����ͼƬ
	
	Background(){ //���췽��
		try {
			//����ר�����Գ�ʼ��
			img_ready=ImageIO.read(new File("images/background_start.png") );
			imgs[0]=ImageIO.read(new File("images/background_1.png") );
			imgs[1]=ImageIO.read(new File("images/background_2.png") );
			imgs[2]=ImageIO.read(new File("images/background_3.png") );
			img_over=ImageIO.read(new File("images/background_end.png") );
			
			//�Ը���̳й��������Գ�ʼ��
			img=img_ready;
			x=0;
			y=0;
			xSpeed=0;
			ySpeed=2;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void move(){	//�ƶ�
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
