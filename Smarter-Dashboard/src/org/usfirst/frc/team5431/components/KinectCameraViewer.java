package org.usfirst.frc.team5431.components;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NoRouteToHostException;
import java.net.Socket;
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

public class KinectCameraViewer {

	public KinectCameraViewer(JFrame f, Executor exe) {
		try {
			final Rectangle bounds = new Rectangle(0, 0, 1217, 943);

			final JLabel crosshair = new JLabel();
			crosshair.setBounds(bounds);
			crosshair.setIcon(ResourceHandler.getResource("crosshair"));
			f.add(crosshair);

			final JLabel feed = new JLabel();
			feed.setBounds(bounds);
			f.add(feed);

			exe.execute(new Thread() {// ticks per second

				@Override
				public void run() {
					// long lastTime = System.nanoTime();
					// double ns = 1000000000;
					// // checks immediately for connection
					// double delta = 1;
					// while (true) {
					// long now = System.nanoTime();
					// delta += (now - lastTime) / ns;
					// lastTime = now;
					// if (delta >= 1) {

					try {
						final InetAddress address = InetAddress.getByName("169.254.201.109");
						final int port = 80;

						Socket socket = null;
						InputStream stream = null;

						while (true) {
							sleep(67);
							while (stream == null || socket == null || !socket.isConnected() || !socket.isBound()) {
								try {
									if (socket != null)
										socket.close();
									socket = new Socket(address, port);
									stream = socket.getInputStream();
									sleep(1000);
								} catch (ConnectException | NoRouteToHostException e) {
									System.err.println(e.getMessage());
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							byte[] bytes;

							bytes = new byte[stream.available()];
							stream.read(bytes);

							if (bytes != null && bytes.length > 0) {
								System.out.println(new String(bytes));
								final BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
								if (img != null) {
									feed.setIcon(new ImageIcon(img));
								} else {
									System.err.println("Image recieved is null!");
								}
							} else {
								System.err.println("Recieved null bytes!");
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

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// delta--;
					// }
					// }
					catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
