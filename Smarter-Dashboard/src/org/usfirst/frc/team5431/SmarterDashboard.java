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
import org.usfirst.frc.team5431.components.FrontCameraViewer;
import org.usfirst.frc.team5431.components.RobotDisplay;

//import com.ni.vision.NIVision.GetClassifierSampleInfoResult;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SmarterDashboard {
	public static NetworkTable table;
	public static final int CONNECTION_TPS=1;
	
	public static boolean getConnectionStatus() {
		return table.getBoolean("connection", false);
	}

	public static final void updateConnectionStatus(boolean b) {
		table.putBoolean("connection", b);
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
