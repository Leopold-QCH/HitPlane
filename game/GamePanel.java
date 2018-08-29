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
	public static final int GAME_READY=0; //��Ϸ׼��״̬
	public static final int GAME_RUNNING=1 ; //��Ϸ��������״̬
	public static final int GAME_PAUSE=2; //��Ϸ��ͣ״̬
	public static final int GAME_OVER=3;  //��Ϸ����״̬
	JLabel live_label,score_label,die_label,pause_label;
	int status=GAME_READY;	//status���������Ϸ��״̬
	int max_ePlane_num=4;	//max_ePlane_num������л�����
	int live=2;
	int score=0;
	Music m;
	Background back=new Background(); //��������
	ArrayList<EnemyPlane> ePlanes=new ArrayList<EnemyPlane>();
	HeroPlane hPlane; //hPlaneδ��ֵʱ��ֵΪ��null
	ArrayList<EnemyBullet> eBullets=new ArrayList<EnemyBullet>();
	ArrayList<HeroBullet> hBullets=new ArrayList<HeroBullet>();
	ArrayList<isBoom> booms=new ArrayList<isBoom>();
	GamePanel(){ //���췽��
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		m=new Music();
		m.au.loop();
		live_label=new JLabel("������"+live);
		live_label.setBounds(0,10 ,200 ,20 );
		Font font=new Font("����", Font.BOLD, 20);
		live_label.setFont(font);
		score_label=new JLabel("������"+score);
		score_label.setBounds(0, 30, 200,20 );
		score_label.setFont(font);
		pause_label=new JLabel("��Ϣһ���~");
		pause_label.setBounds(150, 250, 400, 20);
		die_label=new JLabel("��ò�%׹����������������"+live);
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
		//������ͼ
		back.draw(g);	
		//�����ел�
		for(int i=0; i<ePlanes.size();i++){
			EnemyPlane ePlane=ePlanes.get(i);
			ePlane.draw(g);
		}
		//��Ӣ�ۻ�
		if(hPlane!=null){
			hPlane.draw(g);
		}
		//�����ел��ӵ�
		for(int i=0 ; i<eBullets.size(); i++){
			eBullets.get(i).draw(g);
		}
		//��Ӣ���ӵ�
		for(int i=0;i<hBullets.size();i++) 
			hBullets.get(i).draw(g);
		//����ը
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
				//label��ʾ
				live_label.setText("������"+live);
				score_label.setText("�����ǣ�"+score);
				score_label.setVisible(true);
				live_label.setVisible(true);
				pause_label.setVisible(false);
				die_label.setVisible(false);
				//1. �����ƶ�
				back.move();
				//2.�Ը����л����д���
				for(int i=0; i<ePlanes.size();i++){
					EnemyPlane ePlane=ePlanes.get(i);
					//2.1 �л��ƶ�
					ePlane.move();
					//2.2 ��Խ�磬��ɾ���õл�
					if(ePlane.outofBounds() ){
						ePlanes.remove(i);
						i--;
					}else{ //��δԽ�磬�����ŷ����ӵ�
						EnemyBullet eBullet=ePlane.shoot();
						if(eBullet!=null){ //����ӵ���Ϊ�գ���������һ���ӵ�
							eBullets.add(eBullet); //�򽫸��ӵ����浽�ӵ��б���
						}
					}
				}
				for(int i=0;i<eBullets.size();i++) {//�ӵ������ƶ�
					eBullets.get(i).move();
				}
				//���л��б��� ���� �л� ����
				if(ePlanes.size()<max_ePlane_num){
					ePlanes.add(new EnemyPlane());
				}
				//�ӵ����ƶ�
				for(int i=0;i<hBullets.size();i++) {
						hBullets.get(i).move();
				}
			for(int i=0;i<hBullets.size();i++) {//Ӣ���ӵ���л�
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
			for(int i=0;i<ePlanes.size();i++) {//�л���Ӣ�ۻ�
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
						die_label.setText("��ò�%׹����������������"+live);
						status=GAME_PAUSE;
					}
					else {
					status=GAME_OVER;
					}
					break;
				}
			}
			for(int i=0;i<eBullets.size();i++) {//�л��ӵ���Ӣ�ۻ�
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
						die_label.setText("��ò�%׹����������������"+live);
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
			//����ѭ��֮�䣬���50����
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean isDie1(EnemyBullet obj1,HeroPlane obj2) {
		// TODO Auto-generated method stub
		boolean Die=false;//�ж�����
		if((obj1.x<(obj2.x+80)&&obj1.x>(obj2.x-20)&&obj1.y>(obj2.y+40)&&obj1.y<(obj2.y+80))||
		(obj1.x<(obj2.x+60)&&obj1.x>(obj2.x+25)&&obj1.y>obj2.y&&obj1.y<(obj2.y+80))){
				Die=true;
			}
		return Die;
	}

	private boolean isDie(FlyingObject obj1,FlyingObject obj2) {
		// TODO Auto-generated method stub
		boolean Die=false;//�ж�����
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
		   e.getY()>=390 && e.getY()<=430){//����Ϸ�Ǵ���׼��״̬������Ƶ�����ʼ��Ϸ������
			//�������״��� �����ơ���ʽ
			this.setCursor(new Cursor(Cursor.HAND_CURSOR) );
		}else if(status==GAME_OVER&&e.getX()>=110 && e.getX()<=300 && 
				   e.getY()>=300 && e.getY()<=380) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR) );
		}
		else{ //����
			//�����״��ɡ�Ĭ�ϡ���ͷ��ʽ
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		//����������״̬ʱ������ƶ���Ӣ�ۻ������ƶ�
		if(status==GAME_RUNNING){
			//���¼����x,y��ΪӢ�ۻ���ͼʱ���Ͻǵ����꣬
			//���ҿ��Ա�֤���պ���Ӣ�ۻ������м�λ��
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
		//����Ϸ����׼��״̬���� ����ʼ��Ϸ���ϵ������
		if(e.getButton()==MouseEvent.BUTTON1 &&status==GAME_READY&& e.getX()>=130 && e.getX()<=260 && 
				   e.getY()>=390 && e.getY()<=430){
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ePlanes.add(new EnemyPlane()); //�½��л�������ӵ��л��б�����
			hPlane=new HeroPlane(); //����Ӣ�ۻ�
			status=GAME_RUNNING; //��Ϸ�����������״̬ 
			back.change(status); //����ͼ����״̬���иı�
		}else if(e.getButton()==MouseEvent.BUTTON1 &&status==GAME_OVER&&e.getX()>=110 && e.getX()<=300 && 
				   e.getY()>=300 && e.getY()<=380){
			live=2;
			score=0;
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			ePlanes.add(new EnemyPlane()); //�½��л�������ӵ��л��б�����
			hPlane=new HeroPlane(); //����Ӣ�ۻ�
			status=GAME_RUNNING; //��Ϸ�����������״̬ 
			back.change(status); //����ͼ����״̬���иı�
			m.au.loop();
		}
		else if(e.getButton()==MouseEvent.BUTTON3){ //�����Ҽ���������Ϸ����ͣ�ͼ���
			if(hPlane==null)
				hPlane=new HeroPlane();
			if(status==GAME_RUNNING){ //��������״̬
				status=GAME_PAUSE; //�л�����ͣ״̬
			}else if(status==GAME_PAUSE){	//������ͣ״̬
				status=GAME_RUNNING; 
				m.au.loop();//�л�������״̬
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
