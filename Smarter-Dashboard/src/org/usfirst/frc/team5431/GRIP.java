package org.usfirst.frc.team5431;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GRIP {
	public static void init(Executor exe, JFrame f) throws IOException {
		// create grip
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GRIP Configuration Files", "grip");
		chooser.setCurrentDirectory(new File("C:" + File.separator + "Users" + File.separator
				+ "AcademyHS Robotics" + File.separator + "Desktop"));
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(f);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			exe.execute(() -> {
				try {
					final File file = chooser.getSelectedFile();
					System.out.println("Opening GRIP file " + file.getPath());
					final ProcessBuilder pb = new ProcessBuilder("grip.exe",
							"\"" + file.getPath() + "\"").inheritIO();
					System.out.println("\"C:" + File.separator + "Users" + File.separator + "AcademyHS Robotics"
									+ File.separator + "AppData" + File.separator + "Local" + File.separator
									+ "GRIP" + File.separator + "app" + File.separator
									+ "core-1.3.0-rc1-all.jar" + File.separator + "\"");
					final Process p = pb.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}

}
