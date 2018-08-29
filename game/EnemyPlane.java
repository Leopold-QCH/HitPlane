package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyPlane extends FlyingObject{
	public static final int SUICIDE=0 ; //自杀式敌机（特点：敌机中速度最快，不发子弹，走直线，直接撞英雄机）
	public static final int SHOOTER=1 ; //普通发射式敌机（特点：敌机中速度最慢，发子弹，走直线）
	public static final int CLEVER=2 ; //智能式敌机（特点：敌机中速度适中，实时朝着英雄机移动，发射子弹）
	Image imgs[]=new Image[6];
	int type;  //敌机的类型
	int score; //敌机的分数
	
	EnemyPlane(){ //构造方法
		//对专有属性初始化
		for(int i=0; i<imgs.length;i++){ //imgs.length求出数组的长度，本例中结果为6
			try {
				imgs[i]=ImageIO.read(new File("images/enemy_plane"+(i+1)+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// img_index 存放敌机图片数组范围内的任意下标值，以辅助后面随机选择图片、确定机型、分数。
		int img_index=(int)(Math.random()* imgs.length);
		if(img_index==0){ //约定下标为0的机型是自杀式机型
			type=SUICIDE;
			score=10;
		}else if(img_index==imgs.length-1){ //约定下标为最后一个下标值的机型是智能机型
			type=CLEVER;
			score=15;
		}else{	//普通射击式机型
			type=SHOOTER;
			score=5;
		}
		
		//对父类继承过来的属性初始化
		img=imgs[img_index];
		// img.getWidth(null)：得到图片的宽度，()号里面的参数为
		// 图片的观察者对象，若该条语句写在面板类中，则通常参数写成this
		// 若该条语句写在非面板类中，则通常参数写成null
		x=(int)(Math.random()*(GamePanel.PANEL_WIDTH-img.getWidth(null)));
		y=-img.getHeight(null); 
		xSpeed=0;
		//机型不同，速度不同
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
		//若敌机的y坐标 大于 面板的高度
		return this.y>GamePanel.PANEL_HEIGHT;
	}

	public EnemyBullet shoot() {
		if(this.type==SUICIDE){ //若敌机类型为自杀式敌机
			return null;	//则返回空，即不发射子弹
		}else{//否则
			if((int)(Math.random()*1000 )>990  ){ //满足概率条件才发子弹
				return new EnemyBullet(this); //this代表当前敌机对象本身
			}else{
				return null;
			}
		}
	}
}
