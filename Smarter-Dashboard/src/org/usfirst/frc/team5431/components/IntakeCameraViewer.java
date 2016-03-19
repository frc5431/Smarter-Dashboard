package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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

	public IntakeCameraViewer(JFrame f, Executor exe) {
		try {
			final Rectangle r = new Rectangle(981, 125, 1000, 750);
			final JLabel feed = new JLabel();
			feed.setBounds(r);
			f.add(feed);
			exe.execute(new Thread() {
				final double tps = 30d;// ticks per second

				@Override
				public void run() {
					while (true) {
						action();
					}
				}

				final URL url = new URL("http://10.54.31.59/axis-cgi/jpg/image.cgi");
				
				public void action() {					
					try {
						sleep(67);
						final Graphics g = feed.getGraphics();
		//	g.setFont(Font.getFont(Font.MONOSPACED));
						try {
							final Image icon = ImageIO.read(url)
									.getScaledInstance(r.width, r.height, BufferedImage.SCALE_SMOOTH);
							g.drawImage(icon, 0, 0, r.width, r.height, null);
							g.setColor(Color.GREEN);
							g.drawRect(0, 0, r.width, 50);
							g.drawString("CAMERA - CONNECTED - "+url.getFile(), 0, 0);
						} catch (IOException e) {
							System.err.println(e.getMessage());
							if(g!=null){
							g.setColor(Color.RED);
							g.drawRect(0, 0, r.width, 50);
							g.drawString("CAMERA - "+e.getMessage()+" "+url.getFile(), 0, 0);
							g.setFont(Font.getFont(Font.SERIF).deriveFont(Font.BOLD, 64));
							}
							//g.drawString("X", r.width/2, r.height/2);
						}
						g.dispose();
						feed.repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
