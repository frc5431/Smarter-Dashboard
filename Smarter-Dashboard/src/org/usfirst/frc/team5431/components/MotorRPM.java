package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.Executor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import org.usfirst.frc.team5431.SmarterDashboard;

public class MotorRPM {
	
	private final static Font font = new Font(Font.DIALOG,Font.PLAIN,32);

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
		aimlefttitle.setBounds(p.x, p.y + 50, 200, 50);
		aimlefttitle.setVisible(true);
		f.add(aimlefttitle);
		final JProgressBar aimleftspeed = new JProgressBar(0, 4500);
		aimleftspeed.setBounds(p.x + 200, p.y + 50, 500, 50);
		aimleftspeed.setVisible(true);
		aimleftspeed.setStringPainted(true);
		f.add(aimleftspeed);

		final JLabel aimrighttitle = new JLabel("Calculated Speed");
		aimrighttitle.setBounds(1217, 150, 200, 50);
		aimrighttitle.setVisible(true);
		f.add(aimrighttitle);
		final JProgressBar aimrightspeed = new JProgressBar(0, 4500);
		aimrightspeed.setBounds(1417, 150, 500, 50);
		aimrightspeed.setVisible(true);
		aimrightspeed.setStringPainted(true);
		f.add(aimrightspeed);
		
		final JLabel shooterlefttitle = new JLabel("Power");
		shooterlefttitle.setBounds(p.x, p.y + 50, 200, 50);
		shooterlefttitle.setVisible(true);
		f.add(shooterlefttitle);
		final JProgressBar shooterleftspeed = new JProgressBar(0, 100);
		shooterleftspeed.setBounds(p.x + 200, p.y + 50, 500, 50);
		shooterleftspeed.setVisible(true);
		shooterleftspeed.setStringPainted(true);
		f.add(shooterleftspeed);

		final JLabel shooterrighttitle = new JLabel("Power");
		shooterrighttitle.setBounds(1217, 150, 200, 50);
		shooterrighttitle.setVisible(true);
		f.add(shooterrighttitle);
		final JProgressBar shooterrightspeed = new JProgressBar(0, 100);
		shooterrightspeed.setBounds(1417, 150, 500, 50);
		shooterrightspeed.setVisible(true);
		shooterrightspeed.setStringPainted(true);
		f.add(shooterrightspeed);
		
		final JSpinner speed = new JSpinner(new SpinnerNumberModel(0, 0, 4500, 100)) ;
		speed.setBounds(0, 900, 100, 50);
		//f.add(speed);

		aimrightspeed.setFont(font);
		aimleftspeed.setFont(font);
		shooterleftspeed.setFont(font);
		shooterrightspeed.setFont(font);
		flyleftspeed.setFont(font);
		flyrightspeed.setFont(font);
		
		seperate = seperated;
		if (seperate) {
			flyleftspeed.setBounds(0, p.y, 500, 50);
			flylefttitle.setBounds(500, p.y, 200, 50);
			aimleftspeed.setBounds(0, p.y +100, 500, 50);
			aimlefttitle.setBounds(500, p.y +100, 200, 50);
			shooterleftspeed.setBounds(0, p.y + 50, 500, 50);
			shooterlefttitle.setBounds(500, p.y + 50, 200, 50);
		}
		f.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				if (seperate) {
					final Dimension size = flyrightspeed.getParent().getSize();
					flyrightspeed.setBounds(size.width - 500, p.y, 500, 50);
					flyrighttitle.setBounds(size.width - 700, p.y, 200, 50);
					aimrightspeed.setBounds(size.width - 500, p.y +100, 500, 50);
					aimrighttitle.setBounds(size.width - 700, p.y +100, 200, 50);
					shooterrightspeed.setBounds(size.width - 500, p.y + 50, 500, 50);
					shooterrighttitle.setBounds(size.width - 700, p.y + 50, 200, 50);
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

		});

		exe.execute(new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						sleep(1);
						final int leftspeed = (int) SmarterDashboard.table.getNumber("FLY-RIGHT", 0.0),//encoders are reversed 
								rightspeed = (int) SmarterDashboard.table.getNumber("FLY-LEFT", 0.0);

						final double autospeed = SmarterDashboard.table.getNumber("AUTO-AIM-SPEED", 0.0);
						final double volts = SmarterDashboard.table.getNumber("POWER",0.0)/4500.0;
						aimleftspeed.setValue((int)(autospeed));
						aimrightspeed.setValue((int)(autospeed));
						aimleftspeed.setString((int)autospeed + "");
						aimrightspeed.setString((int)autospeed + "");
						
						shooterrightspeed.setValue((int)(volts*100.0));
						shooterleftspeed.setValue((int)(volts*100.0));
						shooterleftspeed.setString((int)(volts*4500.0)+"");
						shooterrightspeed.setString((int)(volts*4500.0)+"");

						flyleftspeed.setValue(leftspeed);
						flyleftspeed.setString(leftspeed + "");
						flyrightspeed.setValue(rightspeed);
						flyrightspeed.setString(rightspeed + "");

						//SmarterDashboard.table.putNumber("MANUAL-SPEED",(int)speed.getValue());
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
	}
}
