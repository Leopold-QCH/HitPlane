package game;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	public static final int FRAME_WIDTH=407;
	public static final int FRAME_HEIGHT=630;
	
	GameFrame(){ //���췽��
		this.setTitle("�ɻ���ս");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false); //������ı䴰���С
		this.setAlwaysOnTop(true);//������Զ����������ʾ
		this.setLocationRelativeTo(null); //���������ʾ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�����Ϸ���
		GamePanel panel=new GamePanel();
		this.add(panel);
		
		this.setVisible(true);
		panel.action();
	}

	public static void main(String[] args) {
		new GameFrame();
	}

}
