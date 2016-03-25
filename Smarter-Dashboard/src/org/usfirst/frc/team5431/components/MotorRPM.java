package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.Executor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.usfirst.frc.team5431.SmarterDashboard;

public class MotorRPM {
	
	private final boolean seperate;

	public MotorRPM(Point p, boolean seperated, JFrame f, Executor exe) {
		final JLabel flylefttitle = new JLabel("Left Flywheel RPM");
		flylefttitle.setBounds(p.x, p.y, 200, 50);
		flylefttitle.setVisible(true);
		f.add(flylefttitle);
		final JProgressBar flyleftspeed = new JProgressBar(0, 4500);
		flyleftspeed.setBounds(p.x + 200, p.y, 500, 50);
		flyleftspeed.setStringPainted(true);
		flyleftspeed.setVisible(true);
		f.add(flyleftspeed);

		final JLabel flyrighttitle = new JLabel("Right Flywheel RPM");
		flyrighttitle.setBounds(p.x, p.y + 50, 200, 50);
		flyrighttitle.setVisible(true);
		f.add(flyrighttitle);
		final JProgressBar flyrightspeed = new JProgressBar(0, 4500);
		flyrightspeed.setBounds(p.x + 200, p.y + 50, 500, 50);
		flyrightspeed.setStringPainted(true);
		flyrightspeed.setVisible(true);
		f.add(flyrightspeed);
		
		final JLabel aimlefttitle = new JLabel("Calculated Speed");
		aimlefttitle.setBounds(p.x, p.y+50, 200, 50);
		aimlefttitle.setVisible(true);
		f.add(aimlefttitle);
		final JProgressBar aimleftspeed = new JProgressBar(0, 100);
		aimleftspeed.setBounds(p.x+200, p.y+50, 500, 50);
		aimleftspeed.setVisible(true);
		f.add(aimleftspeed);
		
		final JLabel aimrighttitle = new JLabel("Calculated Speed");
		aimrighttitle.setBounds(1217, 150, 200, 50);
		aimrighttitle.setVisible(true);
		f.add(aimrighttitle);
		final JProgressBar aimrightspeed = new JProgressBar(0, 100);
		aimrightspeed.setBounds(1417, 150, 500, 50);
		aimrightspeed.setVisible(true);
		f.add(aimrightspeed);
		final Color defaultcolor = aimleftspeed.getForeground();
		
		seperate = seperated;
		if(seperate){
			flyleftspeed.setBounds(0,p.y,500,50);
			flylefttitle.setBounds(500,p.y,200,50);
			aimleftspeed.setBounds(0, p.y+50, 500, 50);
			aimlefttitle.setBounds(500,p.y+50,200,50);
		}

		exe.execute(new Thread() {
			public void run() {
				try{
				while (true) {
					sleep(1);
					final int leftspeed = (int) SmarterDashboard.table.getNumber("FLY-RIGHT", 0.0),//the encoders are reversed
							rightspeed = (int) SmarterDashboard.table.getNumber("FLY-LEFT", 0.0);
					
					final double autospeed = SmarterDashboard.table.getNumber("AUTO-AIM-SPEED", 0.0);
					aimleftspeed.setValue(leftspeed);
					aimrightspeed.setValue(rightspeed);
					aimleftspeed.setString(leftspeed+" RPM");
					aimrightspeed.setString(rightspeed+" RPM");
					
					flyleftspeed.setValue(leftspeed);
					flyleftspeed.setString(leftspeed + " RPM");
					flyrightspeed.setValue(rightspeed);
					flyrightspeed.setString(rightspeed + " RPM");
					
					if(seperate){
						final Dimension size = flyrightspeed.getParent().getSize();
						flyrightspeed.setBounds(size.width-500, p.y, 500, 50);
						flyrighttitle.setBounds(size.width-700, p.y, 200, 50);
						aimrightspeed.setBounds(size.width-500, p.y+50, 500, 50);
						aimrighttitle.setBounds(size.width-700, p.y+50, 200, 50);
					}
				}
				}catch(Throwable t){
					t.printStackTrace();
				}
			}
		});
	}
}
