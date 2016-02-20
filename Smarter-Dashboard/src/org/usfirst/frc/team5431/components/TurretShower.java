package org.usfirst.frc.team5431.components;

import java.awt.Color;
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
	public TurretShower(JFrame f, Executor exe) {
		

		final JLabel boulder = new JLabel();
		boulder.setBounds(0, 0, 1000, 361);
		boulder.setVisible(false);
		boulder.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "ball.png")));
		f.add(boulder);
		
		final JLabel intake = new JLabel();
		intake.setBounds(0, 560, 1000, 361);
		f.add(intake);

		final JLabel turret = new JLabel();
		turret.setBounds(0, 200, 1000, 360);
		f.add(turret);
		
		final JProgressBar turretspeed = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
		turretspeed.setStringPainted(true);
		turretspeed.setToolTipText("Turret Speed");
		turretspeed.setBounds(1000, 200, 100, 360);
		turretspeed.setVisible(true);
		f.add(turretspeed);

		final JProgressBar leftwheel = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
		leftwheel.setStringPainted(true);
		leftwheel.setToolTipText("Left Wheel Speed");
		leftwheel.setBounds(1500, 200, 100, 360);
		leftwheel.setVisible(true);
		f.add(leftwheel);

		final JProgressBar rightwheel = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
		rightwheel.setStringPainted(true);
		rightwheel.setToolTipText("Right Wheel Speed");
		rightwheel.setBounds(1800, 200, 100, 360);
		rightwheel.setVisible(true);
		f.add(rightwheel);

		final JSpinner turretmax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		turretmax.setBounds(0, 150, 1000, 50);
		turretmax.setVisible(true);
		turretmax.setToolTipText("Turret Max");
		f.add(turretmax);

		final JSpinner intakemax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		intakemax.setBounds(0, 921, 1000, 50);
		intakemax.setVisible(true);
		intakemax.setToolTipText("Intake Max");
		f.add(intakemax);
		
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
							rightwheel.setValue((int) SmarterDashboard.table.getNumber("current right speed", 0.0));
							leftwheel.setValue((int) SmarterDashboard.table.getNumber("current left speed", 0.0));
							turretspeed.setValue((int) SmarterDashboard.table.getNumber("current turret speed", 0.0));
							turretmax.setValue(SmarterDashboard.table.getNumber("turret max", 0.7));
							intakemax.setValue(SmarterDashboard.table.getNumber("intake max", 0.7));
							boulder.setVisible(SmarterDashboard.table.getBoolean("boulder",false));
							init=true;
						}
						action();
						delta--;
					}
				}
			}

			private void action() {
				try {
					final boolean isIntaking = SmarterDashboard.table.getBoolean("intake", false);
					if (isIntaking) {
						intake.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "intake on.png")));
					} else {
						intake.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "intake off.png")));
					}

					final boolean isTurreting = SmarterDashboard.table.getBoolean("turret", false);
					final double turretSpeed = SmarterDashboard.table.getNumber("current turret speed", 0.0);
					if (isTurreting) {
						turret.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "turret on.png")));
						turret.setForeground(Color.GREEN);
					} else {
						turret.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "turret off.png")));
						turret.setForeground(Color.RED);
					}
					turretspeed.setValue((int) (turretSpeed * 100.0));

					leftwheel.setValue((int) (SmarterDashboard.table.getNumber("current left speed", 0.0)));
					rightwheel.setValue((int) (SmarterDashboard.table.getNumber("current right speed", 0.0)));

					boulder.setVisible(SmarterDashboard.table.getBoolean("boulder",false));
					
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
