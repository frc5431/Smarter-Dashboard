package org.usfirst.frc.team5431;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourceHandler {
	private final static Resource[] icons;
	
	static{
		final String[] paths = { "aimbad.png", "aimgood.png", "aimleft.png", "aimright.png", "aimfront.png",
				"aimback.png", "ball.png", "crosshair.png", "intake on.png", "intake reverse.png", "left backward.png",
				 "logo.png", "right backward.png", "right forward.png", "robot.png", "robot off.png",
				"robot_auto.png", "turret on.png","left forward.png" };
		icons = new Resource[paths.length];
		
		for(int i =0;i<paths.length;i++){
			icons[i] = new Resource(new ImageIcon(getImage("res"+File.separator+paths[i])),paths[i].substring(0, paths[i].length()-4));
		}
		
	}
	
	public final static ImageIcon getResource(String i){
		for(Resource r : icons){
			if(r.getName().equals(i)){
				return r.getIcon();
			}
		}
		throw new NullPointerException("Resource "+i+" doesn't exist");
	}
	
	public static final BufferedImage getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println(path+" is not valid!");
			e.printStackTrace();
		}
		return null;
	}

}
