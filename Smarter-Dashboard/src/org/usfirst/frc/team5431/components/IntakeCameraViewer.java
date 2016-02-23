package org.usfirst.frc.team5431.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.usfirst.frc.team5431.SmarterDashboard;

public class IntakeCameraViewer {

	public IntakeCameraViewer(JFrame f, Executor exe){
		try{
			JLabel feed = new JLabel();
			feed.setBounds(981,125,1000,750);
			f.add(feed);
			exe.execute(new Thread(){
				final double tps = 30d;// ticks per second

				@Override
				public void run() {

					long lastTime = System.nanoTime();
					double ns = 1000000000 / tps;// 10 times per second
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

				public void action(){
				try {
					feed.setIcon(new ImageIcon(ImageIO.read(new URL("http://10.54.31.51/axis-cgi/jpg/image.cgi")).getScaledInstance(1000, 750, BufferedImage.SCALE_SMOOTH)));
					feed.repaint();
					
					
				}catch(IOException e){
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}}
			});
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
