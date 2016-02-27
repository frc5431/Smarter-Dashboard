package org.usfirst.frc.team5431;

import java.awt.Color;
import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.usfirst.frc.team5431.components.IntakeCameraViewer;
import org.usfirst.frc.team5431.components.RobotDisplay;
import org.usfirst.frc.team5431.components.TurretDisplay;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RobotDashboard {
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
		frame.setSize(2160, 784);
		frame.setIconImage(ResourceHandler.getResource("logo").getImage());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);

		final JLabel connection = new JLabel("Starting up...", SwingConstants.CENTER);
		connection.setBackground(Color.YELLOW);
		connection.setBounds(750, 650, 1410, 50);
		connection.setOpaque(true);
		frame.add(connection);

		final JLabel error = new JLabel("Connecting network tables...", SwingConstants.CENTER);
		error.setBackground(Color.YELLOW);
		error.setBounds(750, 700, 1410, 50);
		error.setOpaque(true);
		frame.add(error);
		
		SmarterDashboard.init();

		// connection thread, updates once per second
		exe.execute(new Thread() {
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				double ns = 1000000000/SmarterDashboard.CONNECTION_TPS;
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
				if (!SmarterDashboard.getConnectionStatus()) {
					connection.setText("NO CONNECTION");
					connection.setBackground(Color.RED);
				} else {
					connection.setText("Connected to Robot");
					connection.setBackground(Color.GREEN);
				}
				error.setText(SmarterDashboard.table.getString("ERROR", "No error"));
				SmarterDashboard.updateConnectionStatus(false);
			}
		});

		// new AxisCameraViewer(turret,exe);
		// new LEDShower(shooting, exe);
		// new MotorSettingser(settings,exe);
		new RobotDisplay(frame, exe);		// exe.execute(()->{
		new IntakeCameraViewer(frame,exe);
		// new AxisCameraViewer(turret,exe);
		// });

		// exe.execute(new USBCameraViewer(turret));
	}

}
