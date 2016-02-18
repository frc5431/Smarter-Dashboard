package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.io.File;
import java.util.concurrent.Executor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.usfirst.frc.team5431.SmarterDashboard;

public class TurretShower {
	public TurretShower(JFrame f,Executor exe){
		JLabel intake = new JLabel("Creating threads...");
		intake.setBounds(200, 200, 500, 500);
		f.add(intake);
		JProgressBar intakespeed = new JProgressBar();
		intakespeed.setStringPainted(true);
		intakespeed.setToolTipText("Intake Speed");
		intakespeed.setBounds(200, 700, 500, 25);
		f.add(intakespeed);
		
		JLabel turret = new JLabel();
		turret.setBounds(800, 200, 500, 500);
		f.add(turret);
		JProgressBar turretspeed = new JProgressBar();
		turretspeed.setStringPainted(true);
		turretspeed.setToolTipText("Intake Speed");
		turretspeed.setBounds(800, 700, 500, 25);
		f.add(turretspeed);
		final Thread colorthread = new Thread(){
			final double tps = 10d;//ticks per second
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				double ns = 1000000000/tps;//10 times per second
				//checks immediately for connection
				double delta = 1;
				while (true) {
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					if (delta >= 1) {
						action();
						delta--;
					}
				}
			}
			private void action(){
				final boolean isIntaking = SmarterDashboard.table.getBoolean("intake",false);
				final double intakeSpeed = SmarterDashboard.table.getNumber("current intake speed",0.0);
				if(isIntaking){
					intake.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"intake on.png")));
					intake.setForeground(Color.GREEN);
				}
				else{
					intake.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"intake off.png")));
					intake.setForeground(Color.RED);
				}
				intakespeed.setValue((int)(intakeSpeed*100.0));
				
				final boolean isTurreting = SmarterDashboard.table.getBoolean("turret",false);
				final double turretSpeed = SmarterDashboard.table.getNumber("current turret speed",0.0);
				if(isTurreting){
					turret.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"turret on.png")));
					turret.setForeground(Color.GREEN);
				}
				else{
					turret.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"turret off.png")));
					turret.setForeground(Color.RED);
				}
				turretspeed.setValue((int)(turretSpeed*100.0));
			}
		};
		exe.execute(colorthread);
	}
	
}
