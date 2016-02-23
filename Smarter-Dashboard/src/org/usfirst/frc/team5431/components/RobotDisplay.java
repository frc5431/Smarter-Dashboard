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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.usfirst.frc.team5431.SmarterDashboard;

public class RobotDisplay {
	// AUTO-AIM-ON - whether it is auto aiming PUT

	public RobotDisplay(JFrame f, Executor exe) {

		final Rectangle bounds = new Rectangle(0, 0, 960, 960);

		final JLabel aimright = new JLabel();
		aimright.setVisible(true);
		aimright.setBounds(bounds);
		aimright.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "aimright.png")));
		f.add(aimright);
		final JLabel aimleft = new JLabel();
		aimleft.setVisible(true);
		aimleft.setBounds(bounds);
		aimleft.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "aimleft.png")));
		f.add(aimleft);
		final JLabel aimback = new JLabel();
		aimback.setVisible(true);
		aimback.setBounds(bounds);
		aimback.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "aimback.png")));
		f.add(aimback);
		final JLabel aimfront = new JLabel();
		aimfront.setVisible(true);
		aimfront.setBounds(bounds);
		aimfront.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "aimfront.png")));
		f.add(aimfront);
		final JLabel aimgood = new JLabel();
		aimgood.setVisible(true);
		aimgood.setBounds(bounds);
		aimgood.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "aimgood.png")));
		f.add(aimgood);

		final JLabel ball = new JLabel();
		ball.setVisible(true);
		ball.setBounds(bounds);
		ball.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "ball.png")));
		f.add(ball);

		final JLabel turret = new JLabel();
		turret.setVisible(true);
		turret.setBounds(bounds);
		turret.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "turret on.png")));
		f.add(turret);

		final JLabel intake = new JLabel();
		intake.setVisible(true);
		intake.setBounds(bounds);
		intake.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "intake on.png")));
		f.add(intake);

		final JLabel driveleft = new JLabel();
		driveleft.setVisible(true);
		driveleft.setBounds(bounds);
		driveleft.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "left forward.png")));
		f.add(driveleft);

		final JLabel driveright = new JLabel();

		driveright.setVisible(true);
		driveright.setBounds(bounds);
		driveright.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "right forward.png")));
		f.add(driveright);

		final JLabel robot = new JLabel();
		robot.setVisible(true);
		robot.setBounds(bounds);
		robot.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "robot off.png")));
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

		f.repaint();

		final Thread colorthread = new Thread() {
			final double tps = 10d;// ticks per second

			@Override
			public void run() {
				// set the values inside the input boxes to the correct one.
				// otherwise, it will raise errors
				long lastTime = System.nanoTime();
				double ns = 1000000000 / tps;// 10 times per second
				// checks immediately for connection
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

			private void action() {
				try {
					final boolean isIntaking = SmarterDashboard.table.getBoolean("intake", false);
					intake.setVisible(isIntaking);

					if (SmarterDashboard.table.getBoolean("INTAKE-REVERSE", false)) {
						intake.setIcon(new ImageIcon(
								SmarterDashboard.getImage("res" + File.separator + "intake reverse.png")));
					} else {
						intake.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "intake on.png")));
					}

					final boolean isTurreting = SmarterDashboard.table.getBoolean("turret", false);
					// final double turretSpeed =
					// SmarterDashboard.table.getNumber("current turret speed",
					// 0.0);
					turret.setVisible(isTurreting);

					final double leftvalue = SmarterDashboard.table.getNumber("LEFT-DRIVE", 0.0);
					if (leftvalue < 0.11 && leftvalue > -0.11) {
						driveleft.setVisible(false);
					} else {
						driveleft.setVisible(true);
					}
					if (leftvalue < 0) {
						driveleft.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "left backward.png")));
					}
					if (leftvalue > 0) {
						driveleft.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "left forward.png")));
					}

					final double rightvalue = SmarterDashboard.table.getNumber("RIGHT-DRIVE", 0.0);
					if (rightvalue < 0.1 && rightvalue > -0.1) {
						driveright.setVisible(false);
					} else {
						driveright.setVisible(true);
					}
					if (rightvalue < 0) {
						driveright.setIcon(new ImageIcon(
								SmarterDashboard.getImage("res" + File.separator + "right backward.png")));
					}
					if (rightvalue > 0) {
						driveright.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "right forward.png")));
					}

					// leftwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current left speed",
					// 0.0)));
					// rightwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current right speed",
					// 0.0)));

					ball.setVisible(SmarterDashboard.table.getBoolean("boulder", false));

					if (SmarterDashboard.table.getBoolean("AUTO", false)) {
						robot.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "robot auto.png")));
					} else if (!SmarterDashboard.table.getBoolean("ENABLED", false)) {
						robot.setIcon(
								new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "robot off.png")));

					} else {
						robot.setIcon(new ImageIcon(SmarterDashboard.getImage("res" + File.separator + "robot.png")));
					}

					
					// aimassist
					final String driveval = SmarterDashboard.table.getString("PULL", "NA");
					final String turnval = SmarterDashboard.table.getString("FIRE", "NA");
					aimback.setVisible(false);
					aimfront.setVisible(false);
					aimright.setVisible(false);
					aimleft.setVisible(false);
					if (driveval.equals("NA") || turnval.equals("NA")) {
						// no ball found
						aimgood.setVisible(true);
						aimgood.setIcon(new ImageIcon("res" + File.separator + "aimbad.png"));
					} else if (driveval.equals("F") && turnval.equals("F")) {
						// ready to fire
						aimgood.setVisible(true);
						aimgood.setIcon(new ImageIcon("res" + File.separator + "aimgood.png"));
					} else {
						aimgood.setVisible(false);

						// aimassist indicators
						if (driveval.equals("DF")) {
							aimfront.setVisible(true);
						} else if (driveval.equals("DB")) {
							aimback.setVisible(true);
						}
						if (turnval.equals("TR")) {
							aimright.setVisible(true);
						} else if (turnval.equals("TL")) {
							aimleft.setVisible(true);
						}
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		};
		exe.execute(colorthread);
	}

}
