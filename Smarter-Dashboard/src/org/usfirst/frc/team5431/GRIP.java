package org.usfirst.frc.team5431;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GRIP {
	private enum GRIP_FILE{
		ACADEMY(new File("academy.grip").getAbsolutePath());
		
		final String s;
		
		GRIP_FILE(String path){
			s=path;
		}
		
		private String getPath(){
			return s;
		}
	};
	
	public static void init() throws IOException {
		// create grip
			final JFrame f = new JFrame();
			f.setBounds(new Rectangle(0,0,200,200));
			f.setVisible(true);
			final JComboBox<?> chooser = new JComboBox<GRIP_FILE>(GRIP_FILE.values());
			chooser.setBounds(new Rectangle(0,0,100,25));
			f.add(chooser);
			final JButton submit = new JButton(new Action(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try{
					final GRIP_FILE chosen = (GRIP_FILE) chooser.getSelectedItem();
					System.out.println("Opening GRIP file " + chosen+"("+chosen.getPath()+")");
					final ProcessBuilder pb = new ProcessBuilder("GRIP\\grip.exe",
							"\"" + chosen.getPath() + "\"").inheritIO();
//					System.out.println("\"C:" + File.separator + "Users" + File.separator + "AcademyHS Robotics"
//									+ File.separator + "AppData" + File.separator + "Local" + File.separator
//									+ "GRIP" + File.separator + "app" + File.separator
//									+ "core-1.3.0-rc1-all.jar" + File.separator + "\"");
					final Process p = pb.start();
					
					f.dispose();
					}catch(Throwable t){
						t.printStackTrace();
					}
					}

				@Override
				public void addPropertyChangeListener(PropertyChangeListener arg0) {
					
				}

				@Override
				public Object getValue(String arg0) {
					return null;
				}

				@Override
				public boolean isEnabled() {
					return false;
				}

				@Override
				public void putValue(String arg0, Object arg1) {
				
				}

				@Override
				public void removePropertyChangeListener(PropertyChangeListener arg0) {
				
				}

				@Override
				public void setEnabled(boolean arg0) {
				
				}
				
			});
			submit.setText("Submit");
			submit.setEnabled(true);
			submit.setBounds(new Rectangle(0,25,100,25));
			f.add(submit);
			
			f.repaint();
	}

}
