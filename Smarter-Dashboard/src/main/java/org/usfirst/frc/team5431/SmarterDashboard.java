package main.java.org.usfirst.frc.team5431;

import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


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
		
		//AutonChooser.create();

		
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
