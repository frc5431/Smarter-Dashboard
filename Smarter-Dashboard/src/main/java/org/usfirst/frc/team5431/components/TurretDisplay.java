package main.java.org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import main.java.org.usfirst.frc.team5431.SmarterDashboard;

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

//		final JLabel turrettitle = new JLabel("Manual Turret Speed");
//		turrettitle.setBounds(1217, 400, 150, 50);
//		f.add(turrettitle);
//		final JSpinner turretmax = new JSpinner(new SpinnerNumberModel(0.7, 0.0, 1.0, 0.05));
//		turretmax.setBounds(1417, 400, 500, 50);
//		turretmax.setVisible(true);
//		turretmax.setToolTipText("Turret Max");
//		f.add(turretmax);

		final JLabel intaketitle = new JLabel("Manual Intake Speed");
		intaketitle.setBounds(1217, 450, 150, 50);
		f.add(intaketitle);
		final JSpinner intakemax = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.05));
		intakemax.setBounds(1417, 450, 500, 50);
		intakemax.setVisible(true);
		intakemax.setToolTipText("Intake Max");
		f.add(intakemax);
		
		final JLabel PSItitle = new JLabel("Tank PSI");
		PSItitle.setBounds(1217, 400, 150, 50);
		f.add(PSItitle);
		final JProgressBar psi = new JProgressBar(0,60);
		psi.setBounds(1417, 400, 500, 50);
		psi.setVisible(true);
		psi.setToolTipText("Intake Max");
		f.add(psi);

		final JProgressBar overdrive = new JProgressBar(75, 100);
		overdrive.setBounds(1217, 550, 700, 50);//2160 1660
		overdrive.setStringPainted(true);
		overdrive.setVisible(true);
		f.add(overdrive);
		
		final JLabel autotitle = new JLabel("Autonomous Choice");
		autotitle.setBounds(1217, 650, 150, 50);
		f.add(autotitle);
		final JComboBox<?> autochooser = new JComboBox<Object>(new String[]{
				"Moat", "TouchOuterWork", "Lowbar", "AutoShoot", "StandStill", "CrossOuter"
		});
		autochooser.setBounds(1417,650,700,50);
		f.add(autochooser);

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
		final JProgressBar aimspeed = new JProgressBar(0, 4500);
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
		
		new MotorRPM(new Point(1217, 250),false,f, exe);

		f.repaint();

		final Thread colorthread = new Thread() {
			final double tps = 10d;// ticks per second

			@Override
			public void run() {
				while(true){
					action();
				}
			}

			private void action() {
				try {
					sleep(100);
					final double overdrivevalue =  (SmarterDashboard.table.getNumber("OVERDRIVE", 0));
					overdrive.setValue((int)(overdrivevalue*100.0));
					overdrive.setString("Overdrive: " + overdrivevalue);
					//SmarterDashboard.table.putNumber("OVERDRIVE", (double) turretmax.getValue());
					// leftwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current left speed",
					// 0.0)));
					// rightwheel.setValue((int)
					// (SmarterDashboard.table.getNumber("current right speed",
					// 0.0)));

					//SmarterDashboard.table.putNumber("turret max", (double) turretmax.getValue());
					SmarterDashboard.table.putNumber("intake max", (double) intakemax.getValue());
					
					final double pressure = SmarterDashboard.table.getNumber("PRESSURE",0.0);
					psi.setString(pressure+" PSI");
					psi.setValue((int)(pressure*10.0));
					
					debug.setText("Hole #: " + SmarterDashboard.table.getNumber("HOLE-NUM", 0.0)
							+ System.lineSeparator() + "Hole area:" + SmarterDashboard.table.getNumber("HOLE-AREA", 0.0)
							+ System.lineSeparator() + "Hole solidity: "
							+ SmarterDashboard.table.getNumber("HOLE-SOLIDITY", 0.0) + System.lineSeparator()
							+ "\n" + "Drive distance \n LEFT: "
							+ SmarterDashboard.table.getNumber("DRIVE-DISTANCE-LEFT", 0.0)+"\nRIGHT: "+SmarterDashboard.table.getNumber("DRIVE-DISTANCE-RIGHT", 0.0)
							+"\n"+SmarterDashboard.table.getString("DEBUG","No connection to debug stream"));
					

					final double autospeed = SmarterDashboard.table.getNumber("AUTO-AIM-SPEED", 0.0);
					aimtitle.setText("Calculated Flywheel RPM: " + autospeed);
					if(autospeed>1.0){
						aimspeed.setForeground(Color.RED);
					}else{
						aimspeed.setForeground(defaultcolor);
					}
					aimspeed.setValue((int) (autospeed));
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

					SmarterDashboard.table.putString("AUTO-SELECTED", (String)autochooser.getSelectedItem());
					
					distancebar.setValue(distance > 140 ? 140 : (distance < 70 ? 70 : (int) distance));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		};
		exe.execute(colorthread);
	}

}
