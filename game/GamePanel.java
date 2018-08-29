package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseMotionListener,MouseListener{
	private static final long serialVersionUID = 1L;
	public static final int PANEL_WIDTH=GameFrame.FRAME_WIDTH-7;
	public static final int PANEL_HEIGHT=GameFrame.FRAME_HEIGHT-30;
	public static final int GAME_READY=0; //游戏准备状态
	public static final int GAME_RUNNING=1 ; //游戏正在运行状态
	public static final int GAME_PAUSE=2; //游戏暂停状态
	public static final int GAME_OVER=3;  //游戏结束状态
	JLabel live_label,score_label,die_label,pause_label;
	int status=GAME_READY;	//status变量存放游戏的状态
	int max_ePlane_num=4;	//max_ePlane_num存放最大敌机数量
	int live=2;
	int score=0;
	Music m;
	Background back=new Background(); //背景对象
	ArrayList<EnemyPlane> ePlanes=new ArrayList<EnemyPlane>();
	HeroPlane hPlane; //hPlane未赋值时，值为空null
	ArrayList<EnemyBullet> eBullets=new ArrayList<EnemyBullet>();
	ArrayList<HeroBullet> hBullets=new ArrayList<HeroBullet>();
	ArrayList<isBoom> booms=new ArrayList<isBoom>();
	GamePanel(){ //构造方法
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		m=new Music();
		m.au.loop();
		live_label=new JLabel("生命："+live);
		live_label.setBounds(0,10 ,200 ,20 );
		Font font=new Font("黑体", Font.BOLD, 20);
		live_label.setFont(font);
		score_label=new JLabel("分数："+score);
		score_label.setBounds(0, 30, 200,20 );
		score_label.setFont(font);
		pause_label=new JLabel("休息一会儿~");
		pause_label.setBounds(150, 250, 400, 20);
		die_label=new JLabel("你好菜%坠毁啦！还有生命："+live);
		pause_label.setFont(font);
		die_label.setFont(font);
		die_label.setBounds(80, 300, 400, 20);
		this.add(live_label);
		this.add(score_label);
		this.add(pause_label);
		this.add(die_label);
		die_label.setVisible(false);
		pause_label.setVisible(false);
		live_label.setVisible(false);
		score_label.setVisible(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//画背景图
		back.draw(g);	
		//画所有敌机
		for(int i=0; i<ePlanes.size();i++){
			EnemyPlane ePlane=ePlanes.get(i);
			ePlane.draw(g);
		}
		//画英雄机
		if(hPlane!=null){
			hPlane.draw(g);
		}
		//画所有敌机子弹
		for(int i=0 ; i<eBullets.size(); i++){
			eBullets.get(i).draw(g);
		}
		//画英雄子弹
		for(int i=0;i<hBullets.size();i++) 
			hBullets.get(i).draw(g);
		//画爆炸
		for(int i=0;i<booms.size();i++) {
			if(!booms.get(i).ObjectDie)
				booms.get(i).drawdie(g);
			else
				booms.remove(i);
		}
	}
	
	void action(){
		while(true){
			if(status==GAME_RUNNING){
				//label显示
				live_label.setText("生命："+live);
				score_label.setText("分数是："+score);
				score_label.setVisible(true);
				live_label.setVisible(true);
				pause_label.setVisible(false);
				die_label.setVisible(false);
				//1. 背景移动
				back.move();
				//2.对各个敌机进行处理
				for(int i=0; i<ePlanes.size();i++){
					EnemyPlane ePlane=ePlanes.get(i);
					//2.1 敌机移动
					ePlane.move();
					//2.2 若越界，则删除该敌机
					if(ePlane.outofBounds() ){
						ePlanes.remove(i);
						i--;
					}else{ //若未越界，则试着发射子弹
						EnemyBullet eBullet=ePlane.shoot();
						if(eBullet!=null){ //如果子弹不为空，即发射了一颗子弹
							eBullets.add(eBullet); //则将该子弹保存到子弹列表中
						}
					}
				}
				for(int i=0;i<eBullets.size();i++) {//子弹坐标移动
					eBullets.get(i).move();
				}
				//往敌机列表中 补充 敌机 对象
				if(ePlanes.size()<max_ePlane_num){
					ePlanes.add(new EnemyPlane());
				}
				//子弹的移动
				for(int i=0;i<hBullets.size();i++) {
						hBullets.get(i).move();
				}
			for(int i=0;i<hBullets.size();i++) {//英雄子弹与敌机
				for(int j=0;j<ePlanes.size();j++) {
					if(isDie(hBullets.get(i),ePlanes.get(j))) {
						booms.add(new isBoom(ePlanes.get(j).x,ePlanes.get(j).y));
						score+=ePlanes.get(j).score;
						hBullets.remove(i);
						ePlanes.remove(j);
						m.aus[2].play();
						break;
					}
				}
			}
			for(int i=0;i<ePlanes.size();i++) {//敌机与英雄机
				if(isDie(ePlanes.get(i),hPlane)) {
					booms.add(new isBoom(ePlanes.get(i).x,hPlane.y));
					live--;
					m.aus[1].play();
					ePlanes.clear();
					eBullets.clear();
					hBullets.clear();
					hPlane=null;
					if(live>0) {
						die_label.setVisible(true);
						die_label.setText("你好菜%坠毁啦！还有生命："+live);
						status=GAME_PAUSE;
					}
					else {
					status=GAME_OVER;
					}
					break;
				}
			}
			for(int i=0;i<eBullets.size();i++) {//敌机子弹与英雄机
				if(isDie1(eBullets.get(i),hPlane)) {
					booms.add(new isBoom(eBullets.get(i).x,hPlane.y));
					live--;
					m.aus[1].play();
					ePlanes.clear();
					eBullets.clear();
					hBullets.clear();
					hPlane=null;
					if(live>0) {
						die_label.setVisible(true);
						die_label.setText("你好菜%坠毁啦！还有生命："+live);
						status=GAME_PAUSE;
					}
					else {
					status=GAME_OVER;
					}
					break;
				}
			}
		}
			if(status==GAME_PAUSE) {
				pause_label.setVisible(true);
				m.au.stop();
			}
			if(status==GAME_OVER) {
				back.change(GAME_OVER);
				m.au.stop();
			}
			repaint();
			//两次循环之间，间隔50毫秒
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean isDie1(EnemyBullet obj1,HeroPlane obj2) {
		// TODO Auto-generated method stub
		boolean Die=false;//判断死亡
		if((obj1.x<(obj2.x+80)&&obj1.x>(obj2.x-20)&&obj1.y>(obj2.y+40)&&obj1.y<(obj2.y+80))||
		(obj1.x<(obj2.x+60)&&obj1.x>(obj2.x+25)&&obj1.y>obj2.y&&obj1.y<(obj2.y+80))){
				Die=true;
			}
		return Die;
	}

	private boolean isDie(FlyingObject obj1,FlyingObject obj2) {
		// TODO Auto-generated method stub
		boolean Die=false;//判断死亡
		if(((obj1.x>obj2.x&&obj1.x<(obj2.x+obj2.img.getWidth(null)))||
				(obj2.x>obj1.x&&obj2.x<(obj1.x+obj1.img.getWidth(null))))&&
					((obj1.y<(obj2.y+obj2.img.getHeight(null))&&obj1.y>obj2.y)||
							(obj2.y>obj1.y&&obj2.y<(obj1.y+obj1.img.getHeight(null)) ))) {
				Die=true;
			}
		return Die;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(status==GAME_READY && e.getX()>=130 && e.getX()<=260 && 
		   e.getY()>=390 && e.getY()<=430){//若游戏是处于准备状态且鼠标移到“开始游戏”上面
			//则鼠标形状变成 “手势”形式
			this.setCursor(new Cursor(Cursor.HAND_CURSOR) );
		}else if(status==GAME_OVER&&e.getX()>=110 && e.getX()<=300 && 
				   e.getY()>=300 && e.getY()<=380) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR) );
		}
		else{ //否则
			//鼠标形状变成“默认”箭头形式
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		//程序处于运行状态时，鼠标移动，英雄机跟着移动
		if(status==GAME_RUNNING){
			//以下计算的x,y作为英雄机画图时左上角的坐标，
			//而且可以保证鼠标刚好在英雄机的正中间位置
			int x=e.getX()-hPlane.img.getWidth(null)/2;
			int y=e.getY()-hPlane.img.getHeight(null)/2;
			hPlane.moveTo(x,y);
		}
		
			
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//若游戏处于准备状态且在 “开始游戏”上单击左键
		if(e.getButton()==MouseEvent.BUTTON1 &&status==GAME_READY&& e.getX()>=130 && e.getX()<=260 && 
				   e.getY()>=390 && e.getY()<=430){
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ePlanes.add(new EnemyPlane()); //新建敌机对象添加到敌机列表集合中
			hPlane=new HeroPlane(); //创建英雄机
			status=GAME_RUNNING; //游戏变成正在运行状态 
			back.change(status); //背景图根据状态进行改变
		}else if(e.getButton()==MouseEvent.BUTTON1 &&status==GAME_OVER&&e.getX()>=110 && e.getX()<=300 && 
				   e.getY()>=300 && e.getY()<=380){
			live=2;
			score=0;
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ePlanes.add(new EnemyPlane()); //新建敌机对象添加到敌机列表集合中
			hPlane=new HeroPlane(); //创建英雄机
			status=GAME_RUNNING; //游戏变成正在运行状态 
			back.change(status); //背景图根据状态进行改变
			m.au.loop();
		}
		else if(e.getButton()==MouseEvent.BUTTON3){ //单击右键，进行游戏的暂停和继续
			if(hPlane==null)
				hPlane=new HeroPlane();
			if(status==GAME_RUNNING){ //若是运行状态
				status=GAME_PAUSE; //切换成暂停状态
			}else if(status==GAME_PAUSE){	//若是暂停状态
				status=GAME_RUNNING; 
				m.au.loop();//切换成运行状态
			}
		}else if(e.getButton()==MouseEvent.BUTTON1&&status==GAME_RUNNING) {
			HeroBullet hBullet=hPlane.shoot();
			hBullets.add(hBullet);
			m.aus[0].play();
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
