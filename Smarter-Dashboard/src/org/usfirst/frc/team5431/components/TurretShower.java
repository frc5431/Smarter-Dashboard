package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.Executor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.usfirst.frc.team5431.SmarterDashboard;

public class TurretShower {
	//AUTO-AIM-SPEED - double Calculated Ball Speed
	//FIRE - String turn left or right
	//PULL - String go back or left
	//HOLE-NUM - Which hole does auto go for
	//HOLE-DISTANCE - Distance from the hole
	//HOLE-SOLITITY - whether it is a square
	//HOLE-AREA - area of hole
	//DISTANCE-DRIVE - Distance the drive has gone
	
	public TurretShower(JFrame f, Executor exe) {

		final Rectangle bounds = new Rectangle(0,0,960,960);
		
		final JLabel ball = new JLabel();
		ball.setVisible(true);
		ball.setBounds(bounds);
		ball.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"ball.png")));
		f.add(ball);
		
		final JLabel turret = new JLabel();
		turret.setVisible(true);
		turret.setBounds(bounds);
		turret.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"turret on.png")));
		f.add(turret);

		final JLabel intake = new JLabel();
		intake.setVisible(true);
		intake.setBounds(bounds);
		intake.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"intake on.png")));
		f.add(intake);
		
		final JLabel driveleft = new JLabel();
		driveleft.setVisible(true);
		driveleft.setBounds(bounds);
		driveleft.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"left forward.png")));
		f.add(driveleft);
		
		final JLabel driveright = new JLabel();
		driveright.setVisible(true);
		driveright.setBounds(bounds);
		driveright.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"right forward.png")));
		f.add(driveright);
		
		final JLabel robot = new JLabel();
		robot.setVisible(true);
		robot.setBounds(bounds);
		robot.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"robot off.png")));
		f.add(robot);

		// final JProgressBar leftwheel = new
		// JProgressBar(SwingConstants.VERTICAL, 0, 100);
		// leftwheel.setStringPainted(true);
		// leftwheel.setToolTipText("Left Wheel Speed");
		// leftwheel.setBounds(1500, 200, 100, 360);
		// leftwheel.setVisible(true);
		// f.add(leftwheel);
		//
		// final JProgressBar rightwheel = new
		// JProgressBar(SwingConstants.VERTICAL, 0, 100);
		// rightwheel.setStringPainted(true);
		// rightwheel.setToolTipText("Right Wheel Speed");
		// rightwheel.setBounds(1800, 200, 100, 360);
		// rightwheel.setVisible(true);
		// f.add(rightwheel);

		final JSpinner turretmax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		turretmax.setBounds(961, 150, 500, 50);
		turretmax.setVisible(true);
		turretmax.setToolTipText("Turret Max");
		f.add(turretmax);

		final JSpinner intakemax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		intakemax.setBounds(961, 921, 500, 50);
		intakemax.setVisible(true);
		intakemax.setToolTipText("Intake Max");
		f.add(intakemax);
		
		final JSpinner overdrive = new JSpinner(new SpinnerNumberModel(0.0, -1.0, 1.0, 0.05));
		overdrive.setBounds(961, 0, 500, 50);
		overdrive.setVisible(true);
		overdrive.setToolTipText("Overdrive");
		f.add(overdrive);

		f.repaint();

		final Thread colorthread = new Thread() {
			final double tps = 10d;// ticks per second

			@Override
			public void run() {
				// set the values inside the input boxes to the correct one.
				// otherwise, it will raise errors
				boolean init = false;
				long lastTime = System.nanoTime();
				double ns = 1000000000 / tps;// 10 times per second
				// checks immediately for connection
				double delta = 1;
				while (true) {
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					if (delta >= 1) {
						if (!init) {
							// rightwheel.setValue((int)
							// SmarterDashboard.table.getNumber("current right
							// speed", 0.0));
							// leftwheel.setValue((int)
							// SmarterDashboard.table.getNumber("current left
							// speed", 0.0));
							turretmax.setValue(SmarterDashboard.table.getNumber("turret max", 0.7));
							intakemax.setValue(SmarterDashboard.table.getNumber("intake max", 0.7));
							ball.setVisible(SmarterDashboard.table.getBoolean("boulder", false));
							robot.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"robot.png")));
							init = true;
						}
						action();
						delta--;
					}
				}
			}

			private void action() {
				try {
					final boolean isIntaking = SmarterDashboard.table.getBoolean("intake", false);
					intake.setVisible(isIntaking);

					final boolean isTurreting = SmarterDashboard.table.getBoolean("turret", false);
					final double turretSpeed = SmarterDashboard.table.getNumber("current turret speed", 0.0);
					turret.setVisible(isTurreting);
					
					final double leftvalue =SmarterDashboard.table.getNumber("LEFT-DRIVE",0.0);
					if(leftvalue<0.1&&leftvalue>-0.1){
						driveleft.setVisible(false);
					}else{
						driveleft.setVisible(true);
					}
					if(leftvalue<0){
						driveleft.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"left backward.png")));
					}
					if(leftvalue>0){
						driveleft.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"left forward.png")));
					}
					
					final double rightvalue =SmarterDashboard.table.getNumber("RIGHT-DRIVE",0.0);
					if(rightvalue<0.1&&rightvalue>-0.1){
						driveright.setVisible(false);
					}else{
						driveright.setVisible(true);
					}
					if(rightvalue<0){
						driveright.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"right backward.png")));
					}
					if(rightvalue>0){
						driveright.setIcon(new ImageIcon(SmarterDashboard.getImage("res"+File.separator+"right forward.png")));
					}
					
					SmarterDashboard.table.putNumber("OVERDRIVE", (double)overdrive.getValue());

					// leftwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current left speed",
					// 0.0)));
					// rightwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current right speed",
					// 0.0)));

					ball.setVisible(SmarterDashboard.table.getBoolean("boulder", false));

					SmarterDashboard.table.putNumber("turret max", (double) turretmax.getValue());
					SmarterDashboard.table.putNumber("intake max", (double) intakemax.getValue());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		};
		exe.execute(colorthread);
	}

}
