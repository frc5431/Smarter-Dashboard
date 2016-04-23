package main.java.org.usfirst.frc.team5431;

import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


//import com.ni.vision.NIVision.GetClassifierSampleInfoResult;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import main.java.org.usfirst.frc.team5431.components.BallDisplay;
import main.java.org.usfirst.frc.team5431.components.FrontCameraViewer;
import main.java.org.usfirst.frc.team5431.components.MotorRPM;

public class SmarterDashboard {
	public static NetworkTable table;
	public static final int CONNECTION_TPS = 1;

	private static final Executor exe = Executors.newCachedThreadPool();

	public static void main(String... args) throws IOException{		

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
		
	
		final JComboBox<?> autochooser = new JComboBox<Object>(new String[]{
				"CrossPortcullisAndShoot","Spybox","CrossRockWallAndStop", "TouchOuterWork", "CrossOuter", "CrossLowbarAndShoot", "DoNothing","CrossRockwallAndShoot","CrossMoatAndStop",
		});
		autochooser.setBounds(0,650,300,50);
		autochooser.setSelectedItem("CrossLowbarAndShoot");
		frame.add(autochooser);
		
		CameraHandler.initCamera(exe);
			
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
				sleep(66);
				frame.repaint();
				SmarterDashboard.table.putString("AUTO-SELECTED", (String)autochooser.getSelectedItem());
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

		frame.setSize(frame.getWidth()+1, frame.getHeight()+1);
		
		//new FrontCameraViewer(exe,frame);
		//new KinectCameraViewer(frame,exe);
		// exe.execute(()->{
		// new AxisCameraViewer(turret,exe);
		// });

		// exe.execute(new USBCameraViewer(turret));
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
