package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.usfirst.frc.team5431.ResourceHandler;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.github.sarxos.webcam.ds.ipcam.impl.IpCamMJPEGStream;

public class FrontCameraViewer extends JPanel{
	final Rectangle r = new Rectangle(0, 0, 2160, 1080);
	Dimension windowsize;
	Webcam cam;

	static {
		try{
	    Webcam.setDriver(new IpCamDriver());
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
	private void createCamera(){
		try{
		cam = Webcam.getDefault();
		System.out.println(cam.getName());
		}catch(Throwable t){
			t.printStackTrace();
		}
		}
	
	public FrontCameraViewer(Dimension windowsize,JFrame f){	
		try{
	    IpCamDeviceRegistry.register(new IpCamDevice("AXIS M1004-W Network Camera", new URL("http://10.54.31.59/mjpg/video.mjpg"), IpCamMode.PUSH));
		createCamera();
		setBounds(r);
	    f.add(new WebcamPanel(Webcam.getDefault()));
		this.windowsize=windowsize;
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	final Font f = new Font(Font.MONOSPACED,Font.PLAIN,16);

	private long startTime=System.currentTimeMillis();
	private int fps=0,outfps=0;
	
	@Override
	public void paint(Graphics g){
		try{
			g.setFont(f);
			final Rectangle bounds = getParent().getBounds();
		try {
			fps++;
			if(System.currentTimeMillis()>startTime+1000){
				outfps=fps;
				fps=0;
				startTime=System.currentTimeMillis();
			}
			if(cam==null){
				throw new IOException("Cam not open");
			}
			final BufferedImage img =  cam.getImage();
			if(img==null)throw new IOException("Image is null!");
			g.drawImage(img, 0, 0,bounds.width,bounds.height, null);
			g.setColor(new Color(0,0,0,100));
			g.fillRect((bounds.width/2)-(25+28), 0, 25, bounds.height);
			g.setColor(new Color(255,0,0,100));
			g.fillRect((bounds.width/2)-28, 0, 25, bounds.height);
			g.setColor(Color.WHITE);
			final Dimension viewsize = cam.getViewSize();
			g.drawString(cam.getName()+" recording at "+cam.getFPS()+" FPS ("+outfps+" UPS) at "+viewsize.width+"x"+viewsize.height, 0, 16);
			g.dispose();
		} catch (IOException|WebcamException e) {
			createCamera();
			g.setColor(Color.RED);
			g.fillRect(0, 0, r.width, r.height);
			g.setColor(Color.WHITE);
			g.drawString(e.getMessage(), 0, 16);
			g.dispose();
		}
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}
