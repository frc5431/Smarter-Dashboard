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

public class FrontCameraViewer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final Rectangle r = new Rectangle(0, 0, 2160, 1080);
	Dimension windowsize;

	 TargetingLine l = new TargetingLine();

	public FrontCameraViewer(Dimension windowsize, JFrame f) {
		try {
			setBounds(r);
			l.setBounds(r);
			f.add(l);
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
				final BufferedImage img = CameraHandler.getImage();
				if (img == null) {
					throw new IOException("Image is null! Check if GRIP/Kinect is publishing video.");
				}
				g.drawImage(img, 0, 0, bounds.width, bounds.height, null);
				final Rectangle subdim = new Rectangle((int)((img.getWidth()/2)-(img.getWidth()*0.20)), 0, (int)(img.getWidth()*0.40),(int)(img.getHeight()*0.50)) ;
				g.drawImage(img.getSubimage(subdim.x,subdim.y,subdim.width,subdim.height), bounds.width-500,bounds.height-300,500,300,null);
				g.setColor(new Color(0,0,0,100));
				g.fillRect((225+(bounds.width-500)),bounds.height-300,25,300);
				g.setColor(new Color(255,0,0,100));
				g.fillRect((250+(bounds.width-500)),bounds.height-300,25,300);
				//				final Dimension viewsize = cam.getViewSize();
//				g.drawString(cam.getName() + " recording at " + cam.getFPS() + " FPS (" + outfps + " UPS) at "
//						+ viewsize.width + "x" + viewsize.height, 0, 16);
				final BufferedImage ir = CameraHandler.getIR();
				if(ir!=null){
					g.drawImage(ir, 0, bounds.height-200, 250, 200, null);
				}
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
