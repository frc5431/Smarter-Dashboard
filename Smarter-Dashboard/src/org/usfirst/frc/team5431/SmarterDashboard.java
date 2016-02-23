package org.usfirst.frc.team5431;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.usfirst.frc.team5431.components.USBCameraViewer;
import org.usfirst.frc.team5431.components.AxisCameraViewer;
import org.usfirst.frc.team5431.components.LEDShower;
import org.usfirst.frc.team5431.components.MotorSettingser;
import org.usfirst.frc.team5431.components.RobotDisplay;

//import com.ni.vision.NIVision.GetClassifierSampleInfoResult;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmarterDashboard {
	private static JFrame frame;
	public static NetworkTable table;
	public static boolean recievedMessage = false;
	private static final Executor exe = Executors.newCachedThreadPool();
	
	public static void main(String[] args) {
				
//		final JFrame shooting = new JFrame("Vision - Smarter Dashboard");
//		shooting.setSize(2160, 1080);
//		shooting.setIconImage(getImage("res" + File.separator + "logo.png"));
//		shooting.setResizable(false);
//		shooting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		shooting.setLayout(null);
//		shooting.setVisible(true);
		
		final JFrame frame = new JFrame("Team 5431 - Smarter Dashboard");
		frame.setSize(2160, 1080);
		frame.setIconImage(getImage("res" + File.separator + "logo.png"));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		
		final JLabel connection = new JLabel("Starting up...", SwingConstants.CENTER);
		connection.setBackground(Color.YELLOW);
		connection.setBounds(0, 950, 2160, 50);
		connection.setOpaque(true);
		frame.add(connection);

		final JLabel error = new JLabel("Connecting network tables...", SwingConstants.CENTER);
		error.setBackground(Color.YELLOW);
		error.setBounds(0, 1000, 2160, 50);
		error.setOpaque(true);
		frame.add(error);

		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-5431-frc.local");
		table = NetworkTable.getTable("5431");

		// connection thread, updates once per second
		exe.execute(new Thread() {
			@Override
			public void run() {
				long lastTime = System.nanoTime();
				double ns = 1000000000;
				//checks immediately for connection
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
			private void action(){
				if (!getConnectionStatus()) {
					connection.setText("NO CONNECTION");
					connection.setBackground(Color.RED);
				} else {
					connection.setText("Connected to Robot");
					connection.setBackground(Color.GREEN);
				}
				error.setText(table.getString("ERROR","No error"));
				updateConnectionStatus(false);
			}
		});

		//new AxisCameraViewer(turret,exe);
		//new LEDShower(shooting, exe);
		//new MotorSettingser(settings,exe);
		new RobotDisplay(frame,exe);
//		exe.execute(()->{
//		new AxisCameraViewer(turret,exe);
//		});
		
		//exe.execute(new USBCameraViewer(turret));
	}

	public static final Color getLEDColor() {
		final int red = (int) SmarterDashboard.table.getNumber("led-red", 0.0);
		final int green = (int) SmarterDashboard.table.getNumber("led-green", 0.0);
		final int blue = (int) SmarterDashboard.table.getNumber("led-blue", 0.0);
		return new Color(red, green, blue);
	}

	public static boolean getConnectionStatus() {
		return table.getBoolean("connection", false);
	}

	public static final void updateConnectionStatus(boolean b) {
		table.putBoolean("connection", b);
	}

	public static final void putLEDRed(int red) {
		table.putNumber("led-red", red);
	}

	public static final void putLEDGreen(int green) {
		table.putNumber("led-green", green);
	}

	public static final void putLEDBlue(int blue) {
		table.putNumber("led-blue", blue);
	}

	public static final BufferedImage getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
