package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class TargetingLine extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g){
		final Rectangle bounds = getParent().getBounds();
		g.setColor(new Color(0,0,0,100));
		g.fillRect((bounds.width/2)-(25+28), 0, 25, bounds.height);
		g.setColor(new Color(255,0,0,100));
		g.fillRect((bounds.width/2)-28, 0, 25, bounds.height);
	}

}
