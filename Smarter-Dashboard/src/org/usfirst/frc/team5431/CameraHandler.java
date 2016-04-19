package org.usfirst.frc.team5431;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Executor;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class CameraHandler {

	private static volatile BufferedImage img;
	
	private enum CAMERA_TYPE{
		IP,KINECT;
	}
	public static CAMERA_TYPE type = CAMERA_TYPE.IP;
	
	public static void refreshImage(){
		//while(cam==null)initCamera();
		//img =cam.getImage();
	}
	
    /**
     * Return an array big enough to hold least at least "capacity" elements.  If the supplied buffer is big enough,
     * it will be reused to avoid unnecessary allocations.
     */
    private static byte[] growIfNecessary(final byte[] buffer,final  int capacity) {
        if (capacity > buffer.length) {
            int newCapacity = buffer.length;
            while (newCapacity < capacity) {
                newCapacity *= 1.5;
            }
            return new byte[newCapacity];
        }

        return buffer;
    }
	
    private final static int PORT = 1180;
    private final static byte[] MAGIC_NUMBERS = {0x01, 0x00, 0x00, 0x00};
    private final static int HW_COMPRESSION = -1;
    private final static int SIZE_640x480 = 0;
    
    private static boolean shutdownThread = false;
    private final static Thread thread = new Thread(() -> {
        byte[] magic = new byte[4];
        byte[] imageBuffer = new byte[64 * 1024];

        // Loop until the widget is removed or SmartDashboard is closed.  The outer loop only completes an
        // iteration when the thread is interrupted or an exception happens.
        while (!shutdownThread) {
            try {
                try (Socket socket = new Socket(InetAddress.getLoopbackAddress(), PORT);
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                	
                    // In the FRC dashboard protocol, the client (us) starts out by sending three 32-bit integers
                    // (FPS, compression level, and a size enum).  FPS is the only one actually recognized by
                    // GRIP.
                    outputStream.writeInt(30);
                    outputStream.writeInt(HW_COMPRESSION);
                    outputStream.writeInt(SIZE_640x480);
                    long starttime = System.currentTimeMillis();
                    int fps = 0;

                    while (!Thread.currentThread().isInterrupted()) {
                        // Each frame in the FRC dashboard image protocol starts with a 4 magic numbers.  If we
                        // don't get those four numbers, something's wrong.

                    	inputStream.readFully(magic);
                        if (!Arrays.equals(magic, MAGIC_NUMBERS)) {
                            throw new IOException("Invalid stream (wrong magic numbers)");
                        }

                        // Next, the server sends a 32-bit number indicating the number of bytes in this frame,
                        // then the raw bytes.
                        final int imageSize = inputStream.readInt();
                        imageBuffer = growIfNecessary(imageBuffer, imageSize);
                        inputStream.readFully(imageBuffer, 0, imageSize);

                        fps++;
                        if(System.currentTimeMillis()>starttime+1000){
                        	starttime=System.currentTimeMillis();
                        	fps=0;
                        }
                        
                        // Decode the image and redraw
                        img = ImageIO.read(new ByteArrayInputStream(imageBuffer, 0, imageSize));
                    }
                } catch (IOException e) {
                  e.printStackTrace();
                } finally {
                    Thread.sleep(1000); // Wait a second before trying again
                }
            } catch (Throwable e) {
                // The main thread will interrupt the capture thread to indicate that properties have changed, or
                // possibly that the thread should shut down.
                e.printStackTrace();
            }
        }
    }, "Capture");
	
	public static BufferedImage getImage(){
		return img;
	}

	public static void initCamera(Executor exe) {
		if(type==CAMERA_TYPE.IP){
		try {
			exe.execute(thread);;;;;;;//ba'al, lord of semicolons. incur its wrath, and it will execute you.
		} catch (Throwable t) {
			t.printStackTrace();
		}
		}else{
			exe.execute(()->{
				try{
				while(!Thread.currentThread().isInterrupted()){
					Thread.sleep(10);
//					final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "Team 5431", " ");
//					img = ImageIO.read(new BufferedInputStream(new SmbFileInputStream(new SmbFile("smb:\\\\Academytitans\\IMAGEUPDATE\\IR.png"))));
					final String path = "C:\\Users\\AcademyHS Robotics\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\IMAGEUPDATE (Academytitans (titans))\\COLOR.png";
					img = ImageIO.read(new File(path));
				}
				}catch(Throwable t){
					t.printStackTrace();
					
				}
			});
		}
	}
}
