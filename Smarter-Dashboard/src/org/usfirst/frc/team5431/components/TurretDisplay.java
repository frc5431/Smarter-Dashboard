package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.concurrent.Executor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import org.usfirst.frc.team5431.SmarterDashboard;

public class TurretDisplay {

	public TurretDisplay(JFrame f, Executor exe) {

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

		final JLabel turrettitle = new JLabel("Manual Turret Speed");
		turrettitle.setBounds(961, 400, 150, 50);
		f.add(turrettitle);
		final JSpinner turretmax = new JSpinner(new SpinnerNumberModel(0.7, 0.0, 1.0, 0.05));
		turretmax.setBounds(1160, 400, 500, 50);
		turretmax.setVisible(true);
		turretmax.setToolTipText("Turret Max");
		f.add(turretmax);

		final JLabel intaketitle = new JLabel("Manual Intake Speed");
		intaketitle.setBounds(961, 450, 150, 50);
		f.add(intaketitle);
		final JSpinner intakemax = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.05));
		intakemax.setBounds(1160, 450, 500, 50);
		intakemax.setVisible(true);
		intakemax.setToolTipText("Intake Max");
		f.add(intakemax);

		final JProgressBar overdrive = new JProgressBar(-100, 100);
		overdrive.setBounds(960, 0, 700, 50);
		overdrive.setStringPainted(true);
		overdrive.setVisible(true);
		f.add(overdrive);

		// 2160
		final JTextArea debug = new JTextArea("Starting..");
		debug.setBounds(1660, 50, 500, 900);
		debug.setVisible(true);
		debug.setOpaque(true);
		debug.setBackground(Color.LIGHT_GRAY);
		f.add(debug);

		final JLabel title = new JLabel("Debug");
		title.setVisible(true);
		title.setBounds(1660, 0, 500, 50);
		title.setFont(title.getFont().deriveFont(32));
		title.setOpaque(true);
		title.setForeground(Color.WHITE);
		title.setBackground(Color.DARK_GRAY);
		f.add(title);

		final JLabel aimtitle = new JLabel("Starting...");
		aimtitle.setBounds(961, 150, 200, 50);
		aimtitle.setVisible(true);
		f.add(aimtitle);
		final JProgressBar aimspeed = new JProgressBar(0, 100);
		aimspeed.setBounds(1160, 150, 500, 50);
		aimspeed.setStringPainted(true);
		aimspeed.setVisible(true);
		f.add(aimspeed);

		final JLabel distancetitle = new JLabel("Starting...");
		distancetitle.setBounds(961, 100, 200, 50);
		distancetitle.setVisible(true);
		f.add(distancetitle);
		final JProgressBar distancebar = new JProgressBar(70, 140);
		distancebar.setBounds(1160, 100, 500, 50);
		distancebar.setVisible(true);
		f.add(distancebar);
		
		final JLabel flylefttitle = new JLabel("Left Flywheel RPM");
		flylefttitle.setBounds(961, 250, 200, 50);
		flylefttitle.setVisible(true);
		f.add(flylefttitle);
		final JProgressBar flyleftspeed = new JProgressBar(0, 4000);
		flyleftspeed.setBounds(1160, 250, 500, 50);
		flyleftspeed.setStringPainted(true);
		flyleftspeed.setVisible(true);
		f.add(flyleftspeed);
		
		final JLabel flyrighttitle = new JLabel("Right Flywheel RPM");
		flyrighttitle.setBounds(961, 300, 200, 50);
		flyrighttitle.setVisible(true);
		f.add(flyrighttitle);
		final JProgressBar flyrightspeed = new JProgressBar(0, 4000);
		flyrightspeed.setBounds(1160, 300, 500, 50);
		flyrightspeed.setStringPainted(true);
		flyrightspeed.setVisible(true);
		f.add(flyrightspeed);

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
							intakemax.setValue(SmarterDashboard.table.getNumber("intake max", 1.0));
							init = true;
						}
						action();
						delta--;
					}
				}
			}

			private void action() {
				try {

					final int overdrivevalue = (int) (SmarterDashboard.table.getNumber("OVERDRIVE", 0) * 100.0);
					overdrive.setValue(overdrivevalue);
					overdrive.setString("Auto Aim Overdrive: " + overdrivevalue);
					// leftwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current left speed",
					// 0.0)));
					// rightwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current right speed",
					// 0.0)));

					SmarterDashboard.table.putNumber("turret max", (double) turretmax.getValue());
					SmarterDashboard.table.putNumber("intake max", (double) intakemax.getValue());

					final int leftspeed = (int)SmarterDashboard.table.getNumber("FLY-LEFT",0.0),rightspeed=(int)SmarterDashboard.table.getNumber("FLY-RIGHT",0.0);
					flyleftspeed.setValue(leftspeed);
					flyleftspeed.setString(leftspeed+" RPM");
					flyrightspeed.setValue(rightspeed);
					flyrightspeed.setString(rightspeed+" RPM");
					
					debug.setText("Hole #: " + SmarterDashboard.table.getNumber("HOLE-NUM", 0.0)
							+ System.lineSeparator() + "Hole area:" + SmarterDashboard.table.getNumber("HOLE-AREA", 0.0)
							+ System.lineSeparator() + "Hole solidity: "
							+ SmarterDashboard.table.getNumber("HOLE-SOLIDITY", 0.0) + System.lineSeparator()
							+ System.lineSeparator() + "Drive distance: "
							+ SmarterDashboard.table.getNumber("DISTANCE-DRIVE", 0.0));

					final double autospeed = SmarterDashboard.table.getNumber("AUTO-AIM-SPEED", 0.0);
					aimtitle.setText("Calculated Flywheel Speed: " + autospeed);
					aimspeed.setValue((int) (autospeed * 100.0));
					final double distance = SmarterDashboard.table.getNumber("HOLE-DISTANCE", 0);
					if (distance < 90) {
						distancebar.setForeground(Color.GREEN);
					} else if (distance >= 90 && distance < 110) {
						distancebar.setForeground(Color.YELLOW);
					} else if (distance >= 110 && distance < 135) {
						distancebar.setForeground(Color.RED);
					} else if (distance >= 135) {
						distancebar.setForeground(Color.MAGENTA);
					}
					distancetitle.setText("Hole Distance: " + distance);

					distancebar.setValue(distance > 140 ? 140 : (distance < 70 ? 70 : (int) distance));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		};
		exe.execute(colorthread);
	}

}
