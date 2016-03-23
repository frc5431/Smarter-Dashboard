package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import org.usfirst.frc.team5431.SmarterDashboard;

public class BallDisplay extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BallDisplay(final Dimension size){
		setBounds(0,0,size.width,100);
	}

	final static Font f = new Font(Font.DIALOG,Font.PLAIN,25);

	@Override
	public void paint(Graphics g){
		final boolean ball = SmarterDashboard.table.getBoolean("boulder",false);
		final Dimension size = getParent().getSize();
		setBounds(new Rectangle(0,0,size.width,size.height));
		if(ball){
			g.setColor(Color.green);
		}else{
			g.setColor(Color.red);
		}
		g.setFont(f);
		g.fillRect(0,0, size.width, 50);
		g.setColor(Color.BLACK);
		g.drawString((ball ? "BALL IN" : "BALL OUT")+"| Tower Distance: "+SmarterDashboard.table.getNumber("HOLE-DISTANCE",0), size.width/2-50, 25);//the meaning to everything
	}
}
