package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.util.concurrent.Executor;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.usfirst.frc.team5431.SmarterDashboard;

public class MotorSettingser {
	public MotorSettingser(JFrame f,Executor exe){
		final JLabel color = new JLabel();
		color.setBounds(0,50,2160,1030);
		color.setOpaque(true);
		color.setForeground(Color.WHITE);
		f.add(color);
		final Thread colorthread = new Thread(){
			@Override
			public void run(){
				color.setBackground(SmarterDashboard.getLEDColor());
			}
		};
		exe.execute(colorthread);
	}
}
