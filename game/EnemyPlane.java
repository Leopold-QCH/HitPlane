package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyPlane extends FlyingObject{
	public static final int SUICIDE=0 ; //��ɱʽ�л����ص㣺�л����ٶ���죬�����ӵ�����ֱ�ߣ�ֱ��ײӢ�ۻ���
	public static final int SHOOTER=1 ; //��ͨ����ʽ�л����ص㣺�л����ٶ����������ӵ�����ֱ�ߣ�
	public static final int CLEVER=2 ; //����ʽ�л����ص㣺�л����ٶ����У�ʵʱ����Ӣ�ۻ��ƶ��������ӵ���
	Image imgs[]=new Image[6];
	int type;  //�л�������
	int score; //�л��ķ���
	
	EnemyPlane(){ //���췽��
		//��ר�����Գ�ʼ��
		for(int i=0; i<imgs.length;i++){ //imgs.length�������ĳ��ȣ������н��Ϊ6
			try {
				imgs[i]=ImageIO.read(new File("images/enemy_plane"+(i+1)+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// img_index ��ŵл�ͼƬ���鷶Χ�ڵ������±�ֵ���Ը����������ѡ��ͼƬ��ȷ�����͡�������
		int img_index=(int)(Math.random()* imgs.length);
		if(img_index==0){ //Լ���±�Ϊ0�Ļ�������ɱʽ����
			type=SUICIDE;
			score=10;
		}else if(img_index==imgs.length-1){ //Լ���±�Ϊ���һ���±�ֵ�Ļ��������ܻ���
			type=CLEVER;
			score=15;
		}else{	//��ͨ���ʽ����
			type=SHOOTER;
			score=5;
		}
		
		//�Ը���̳й��������Գ�ʼ��
		img=imgs[img_index];
		// img.getWidth(null)���õ�ͼƬ�Ŀ�ȣ�()������Ĳ���Ϊ
		// ͼƬ�Ĺ۲��߶������������д��������У���ͨ������д��this
		// ���������д�ڷ�������У���ͨ������д��null
		x=(int)(Math.random()*(GamePanel.PANEL_WIDTH-img.getWidth(null)));
		y=-img.getHeight(null); 
		xSpeed=0;
		//���Ͳ�ͬ���ٶȲ�ͬ
		if(type==SUICIDE){
			ySpeed=20;
		}else if(type==CLEVER){
			ySpeed=6;
		}else{
			ySpeed=4;
		}
	}

	public void move() {
		this.y+=this.ySpeed;
	}

	public boolean outofBounds() {
		//���л���y���� ���� ���ĸ߶�
		return this.y>GamePanel.PANEL_HEIGHT;
	}

	public EnemyBullet shoot() {
		if(this.type==SUICIDE){ //���л�����Ϊ��ɱʽ�л�
			return null;	//�򷵻ؿգ����������ӵ�
		}else{//����
			if((int)(Math.random()*1000 )>990  ){ //������������ŷ��ӵ�
				return new EnemyBullet(this); //this����ǰ�л�������
			}else{
				return null;
			}
		}
	}
}
