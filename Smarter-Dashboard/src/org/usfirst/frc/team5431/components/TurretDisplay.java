package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
		turrettitle.setBounds(1217, 400, 150, 50);
		f.add(turrettitle);
		final JSpinner turretmax = new JSpinner(new SpinnerNumberModel(0.7, 0.0, 1.0, 0.05));
		turretmax.setBounds(1417, 400, 500, 50);
		turretmax.setVisible(true);
		turretmax.setToolTipText("Turret Max");
		f.add(turretmax);

		final JLabel intaketitle = new JLabel("Manual Intake Speed");
		intaketitle.setBounds(1217, 450, 150, 50);
		f.add(intaketitle);
		final JSpinner intakemax = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.05));
		intakemax.setBounds(1417, 450, 500, 50);
		intakemax.setVisible(true);
		intakemax.setToolTipText("Intake Max");
		f.add(intakemax);

		final JProgressBar overdrive = new JProgressBar(-50, 50);
		overdrive.setBounds(1217, 550, 700, 50);//2160 1660
		overdrive.setStringPainted(true);
		overdrive.setVisible(true);
		f.add(overdrive);

		// 2160
		final JTextArea debug = new JTextArea("Starting..");
		debug.setBounds(1217, 50, 700, 900);
		debug.setVisible(false);
		debug.setOpaque(true);
		debug.setBackground(Color.LIGHT_GRAY);
		f.add(debug);

		final JLabel aimtitle = new JLabel("Starting...");
		aimtitle.setBounds(1217, 150, 200, 50);
		aimtitle.setVisible(true);
		f.add(aimtitle);
		final JProgressBar aimspeed = new JProgressBar(0, 100);
		aimspeed.setBounds(1417, 150, 500, 50);
		aimspeed.setStringPainted(true);
		aimspeed.setVisible(true);
		final Color defaultcolor = aimspeed.getForeground();
		f.add(aimspeed);

		final JLabel distancetitle = new JLabel("Starting...");
		distancetitle.setBounds(1217, 100, 200, 50);
		distancetitle.setVisible(true);
		f.add(distancetitle);
		final JProgressBar distancebar = new JProgressBar(60, 140);
		distancebar.setBounds(1417, 100, 500, 50);
		distancebar.setVisible(true);
		f.add(distancebar);
		
		final JLabel flylefttitle = new JLabel("Left Flywheel RPM");
		flylefttitle.setBounds(1217, 250, 200, 50);
		flylefttitle.setVisible(true);
		f.add(flylefttitle);
		final JProgressBar flyleftspeed = new JProgressBar(0, 4000);
		flyleftspeed.setBounds(1417, 250, 500, 50);
		flyleftspeed.setStringPainted(true);
		flyleftspeed.setVisible(true);
		f.add(flyleftspeed);
		
		final JLabel flyrighttitle = new JLabel("Right Flywheel RPM");
		flyrighttitle.setBounds(1217, 300, 200, 50);
		flyrighttitle.setVisible(true);
		f.add(flyrighttitle);
		final JProgressBar flyrightspeed = new JProgressBar(0, 4000);
		flyrightspeed.setBounds(1417, 300, 500, 50);
		flyrightspeed.setStringPainted(true);
		flyrightspeed.setVisible(true);
		f.add(flyrightspeed);
		
		final JButton debugbutton = new JButton();
		debugbutton.setAction(new Action(){

			@Override
			public void actionPerformed(ActionEvent e) {
				debug.setVisible(!debug.isVisible());
			}

			@Override
			public Object getValue(String key) {
				return null;
			}

			@Override
			public void putValue(String key, Object value) {
				
			}

			@Override
			public void setEnabled(boolean b) {
				
			}

			@Override
			public boolean isEnabled() {
				return true;
			}

			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				
			}

			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				
			}
			
		});
		debugbutton.setText("Debug");
		debugbutton.setBounds(1217, 0, 700, 50);
		debugbutton.setFont(debugbutton.getFont().deriveFont(32));
		debugbutton.setOpaque(true);
		debugbutton.setBackground(Color.DARK_GRAY);
		f.add(debugbutton);

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
					sleep(100);
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
					if(autospeed>1.0){
						aimspeed.setForeground(Color.RED);
					}else{
						aimspeed.setForeground(defaultcolor);
					}
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
