package game;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	public static final int FRAME_WIDTH=407;
	public static final int FRAME_HEIGHT=630;
	
	GameFrame(){ //构造方法
		this.setTitle("飞机大战");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false); //不允许改变窗体大小
		this.setAlwaysOnTop(true);//窗体永远在最上面显示
		this.setLocationRelativeTo(null); //窗体居中显示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//添加游戏面板
		GamePanel panel=new GamePanel();
		this.add(panel);
		
		this.setVisible(true);
		panel.action();
	}

	public static void main(String[] args) {
		new GameFrame();
	}

}
