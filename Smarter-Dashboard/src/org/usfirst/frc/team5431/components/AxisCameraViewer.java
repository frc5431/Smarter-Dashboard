package org.usfirst.frc.team5431.components;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class AxisCameraViewer {

	public AxisCameraViewer(JFrame f, Executor exe){
		try{
			final SimpleSwingBrowser brow = new SimpleSwingBrowser(f);
			brow.loadURL("10.54.31.50/mjpg/video.mjpg");
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
