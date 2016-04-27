package main.java.org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.org.usfirst.frc.team5431.CameraHandler;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;

public class RearView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final Rectangle r = new Rectangle(0, 0, 2160, 1080);
	Dimension windowsize;

	public RearView(Dimension windowsize, JFrame f) {
		try {
			setBounds(r);
			this.windowsize = windowsize;
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	final Font f = new Font(Font.MONOSPACED, Font.PLAIN, 16);

	private long startTime = System.currentTimeMillis();
	private int fps = 0, outfps = 0;

	@Override
	public void paint(Graphics g) {
		try {
			final Rectangle bounds = getParent().getBounds();
			getParent().repaint();
			try {
				fps++;
				if (System.currentTimeMillis() > startTime + 1000) {
					outfps = fps;
					fps = 0;
					startTime = System.currentTimeMillis();
				}

//				final Webcam cam = CameraHandler.getCamera();
//				if (cam == null) {
//					throw new IOException("Cam not open");
//				}
				final BufferedImage img = CameraHandler.getRear();
				if (img == null) {
					throw new IOException("Image is null! Check if mini computer is publishing video.");
				}
				g.drawImage(img, 0, 0, bounds.width, bounds.height, null);
				g.dispose();
			} catch (Exception e) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, r.width, r.height);
				g.setColor(Color.WHITE);
				g.drawString(e.toString(), bounds.width/2, bounds.height/2);
				g.dispose();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
