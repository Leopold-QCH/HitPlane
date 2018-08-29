package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class isBoom {//≈–∂œ «∑Ò±¨’®
	Image[] blasts=new Image[6];
	int die_x;
	int die_y;
	int d=0;
	boolean ObjectDie=false;
	isBoom(int x,int y) {
		die_x=x;
		die_y=y;
		for(int i=0;i<6;i++) {
			try {
				blasts[i]=ImageIO.read(new File("images/blast_"+(i+1)+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	void drawdie(Graphics g){ //±¨’®∑Ω∑®
		if(d<6) {
		g.drawImage(blasts[d],die_x,die_y,null);
		d++;
		}
		else
			ObjectDie=true;
	}

}
