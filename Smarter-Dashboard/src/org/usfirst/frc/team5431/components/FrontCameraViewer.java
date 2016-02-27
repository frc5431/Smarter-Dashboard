package org.usfirst.frc.team5431.components;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.usfirst.frc.team5431.ResourceHandler;
import org.usfirst.frc.team5431.SmarterDashboard;

public class FrontCameraViewer {

	private MulticastSocket socket;

	public FrontCameraViewer(JFrame f, Executor exe) {
		try {
			final Rectangle bounds = new Rectangle(0, 0, 1217, 943);

			final JLabel crosshair = new JLabel();
			crosshair.setBounds(bounds);
			crosshair.setIcon(ResourceHandler.getResource("crosshair"));
			f.add(crosshair);

			final JLabel feed = new JLabel();
			feed.setBounds(bounds);
			f.add(feed);

			socket = new MulticastSocket(5431);

			exe.execute(new Thread() {// ticks per second

				@Override
				public void run() {
					try {
						socket.joinGroup(InetAddress.getByName("239.255.42.99"));
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// long lastTime = System.nanoTime();
					// double ns = 1000000000;
					// // checks immediately for connection
					// double delta = 1;
					// while (true) {
					// long now = System.nanoTime();
					// delta += (now - lastTime) / ns;
					// lastTime = now;
					// if (delta >= 1) {

					DatagramPacket pack;

					final int PACKET_SIZE = 1000;

					while (true){
						try {
							pack = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
							socket.receive(pack);

							final byte[] data = pack.getData();
							// check header
							if (data[0] == 54 && data[1] == 31 && data[2] == 42 && data[3] == 10) {
								System.arraycopy(data, 3, data, 0, pack.getLength());
								feed.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(data))));
							}

						} catch (IOException e) {
							e.printStackTrace();
						}

						// try {
						// feed.setIcon(
						// new ImageIcon(ImageIO.read(new
						// URL("http://10.54.31.50/axis-cgi/jpg/image.cgi"))
						// .getScaledInstance(1217, 943,
						// BufferedImage.SCALE_SMOOTH)));
						// //feed.repaint();
						// } catch (IOException e) {
						// System.err.println(e.getMessage());
						// } catch (Exception e) {
						// e.printStackTrace();
						// }
					}
					// delta--;
					// }
					// }
				}

			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
