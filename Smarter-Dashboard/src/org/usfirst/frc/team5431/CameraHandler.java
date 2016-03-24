package org.usfirst.frc.team5431;

import java.awt.image.BufferedImage;
import java.net.URL;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

public class CameraHandler {
	private static Webcam cam;

	static {
		try {
		    IpCamDeviceRegistry.register(new IpCamDevice("AXIS M1004-W Network Camera", new URL("http://axis-camera.local/mjpg/video.mjpg"), IpCamMode.PUSH));
			Webcam.setDriver(new IpCamDriver());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private static BufferedImage img;
	
	public static void refreshImage(){
		while(cam==null)initCamera();
		img =cam.getImage();
	}
	
	public static BufferedImage getImage(){
		return img;
	}

	public static void initCamera() {
		try {
			cam = Webcam.getDefault();
			System.out.println(cam.getName());
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	public static Webcam getCamera() {
		while (cam == null) {
			initCamera();
		}
		return cam;
	}
}
