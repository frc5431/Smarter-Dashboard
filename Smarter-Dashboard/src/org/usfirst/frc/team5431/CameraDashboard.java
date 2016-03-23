package org.usfirst.frc.team5431;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.usfirst.frc.team5431.components.BallDisplay;
import org.usfirst.frc.team5431.components.FrontCameraViewer;
import org.usfirst.frc.team5431.components.MotorRPM;
import org.usfirst.frc.team5431.components.RobotDisplay;
import org.usfirst.frc.team5431.components.TurretDisplay;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraDashboard {
	public static boolean recievedMessage = false;
	private static final Executor exe = Executors.newCachedThreadPool();

	public static void main(String[] args) {

		// final JFrame shooting = new JFrame("Vision - Smarter Dashboard");
		// shooting.setSize(2160, 1080);
		// shooting.setIconImage(getImage("res" + File.separator + "logo.png"));
		// shooting.setResizable(false);
		// shooting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// shooting.setLayout(null);
		// shooting.setVisible(true);

		final JFrame frame = new JFrame("Team 5431 - Smarter Dashboard");
		frame.setSize(1000, 1000);//1080
		frame.setIconImage(ResourceHandler.getResource("logo").getImage());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		
		new MotorRPM(new Point(0,500),true, frame,exe);
			
		// connection thread, updates once per second
		exe.execute(new Thread() {
			@Override
			public void run() {
				while (true) {
					action();
				}
			}

			private void action() {
				try{
				sleep(1);
				frame.repaint();
				CameraHandler.refreshImage();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});

		// new AxisCameraViewer(turret,exe);
		// new LEDShower(shooting, exe);
		// new MotorSettingser(settings,exe);
		frame.add(new BallDisplay(frame.getSize()));
		frame.add(new FrontCameraViewer(frame.getSize(),frame));

		//new FrontCameraViewer(exe,frame);
		//new KinectCameraViewer(frame,exe);
		// exe.execute(()->{
		// new AxisCameraViewer(turret,exe);
		// });

		// exe.execute(new USBCameraViewer(turret));
	}
}
