package main.java.org.usfirst.frc.team5431;

import javax.swing.ImageIcon;

public class Resource {
	private final ImageIcon icon;
	private final String name;
	
	public Resource(ImageIcon icon,String name){
		this.icon=icon;
		this.name=name;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}
	
}
