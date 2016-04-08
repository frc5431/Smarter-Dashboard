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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.usfirst.frc.team5431.components.USBCameraViewer;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import org.usfirst.frc.team5431.components.RobotDisplay;

//import com.ni.vision.NIVision.GetClassifierSampleInfoResult;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmarterDashboard {
	public static NetworkTable table;
	public static final int CONNECTION_TPS = 1;

	public static void main(String... args) throws IOException{		
		CameraDashboard.main(null);
		//DriverDashboard.main(null);
		//RobotDashboard.main(null);
	}
	
	static{
//		try {
//			GRIP.init();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-5431-frc.local");
		table = NetworkTable.getTable("5431");
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
	}

	public static boolean getConnectionStatus() {
		return table.getBoolean("connection", false);
	}

	public static final void updateConnectionStatus(boolean b) {
		table.putBoolean("connection", b);
	}
}
