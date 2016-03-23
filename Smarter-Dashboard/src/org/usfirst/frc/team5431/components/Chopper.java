package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.usfirst.frc.team5431.SmarterDashboard;

public class Chopper extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static Font f = new Font(Font.DIALOG,Font.PLAIN,25);

	public void paint(Graphics g){
		final Dimension size = getSize();
		final boolean choppers = SmarterDashboard.table.getBoolean("CHOPPERS",false);
		if(choppers){
			g.setColor(Color.GREEN);
		}else{
			g.setColor(Color.RED);
		}
		g.fillRect(0, 0, size.width, size.height);
		g.setColor(Color.BLACK);
		g.drawString("Choppers", size.width/2, size.height/2);
	}
}
