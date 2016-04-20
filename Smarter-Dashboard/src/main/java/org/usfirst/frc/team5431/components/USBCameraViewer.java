package org.usfirst.frc.team5431.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;

public class USBCameraViewer extends JPanel{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Webcam cam;
	final Rectangle rect = new Rectangle(0,0,2160, 1080);
	private long startTime=System.currentTimeMillis();
	private int fps=0,outfps=0;

	public USBCameraViewer(){
		Webcam.resetDriver();
		final List<Webcam> cams = Webcam.getWebcams();
		for(Webcam w : cams){
			System.out.println(w.getName());

			if(w.getName().substring(0,w.getName().length()-2).equals("Microsoft® LifeCam HD-3000")){
				cam=w;
			}
		}
		if(cam==null)cam=Webcam.getDefault();
		cam.setViewSize(new Dimension(640,480));
		name = cam.getName();
		cam.open();
		viewsize = cam.getViewSize();
	   setBounds(rect);
   }

	final Dimension viewsize;
	final Font f = new Font(Font.MONOSPACED,Font.PLAIN,16);
	final String name;
	
	@Override
	public void paint(Graphics g) {
		fps++;
		if(System.currentTimeMillis()>startTime+1000){
			outfps=fps;
			fps=0;
			startTime=System.currentTimeMillis();
		}
	//	super.paint(g);
		//,rect.width,rect.height
		final Rectangle bounds = getParent().getBounds();
		g.drawImage(cam.getImage(),0,0,bounds.width,bounds.height,null);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString(name+" recording at "+cam.getFPS()+" FPS ("+outfps+" UPS) at "+viewsize.width+"x"+viewsize.height, 0, 16);
		g.dispose();
	}
}
