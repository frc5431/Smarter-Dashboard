package main.java.org.usfirst.frc.team5431;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

	private static volatile BufferedImage img,ir,rear;
	
	private static final String url ="http://10.54.31.20";
	
	public enum CAMERA_TYPE{
		IP,KINECT,DUAL;
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

	public static BufferedImage getIR(){
		return ir;
	}
	
	/**
	 * re rawr
	 * @return re-rawr
	 */
	public static BufferedImage getRear(){
		return rear;
	}
	
	private static void invertImage(final BufferedImage i){
		final Graphics2D g = i.createGraphics();
		g.drawImage(i, 0, -i.getHeight(), i.getWidth(), -i.getHeight(), null);
		g.dispose();
	}
	
	public static void initCamera(Executor exe) {
		switch(type){
		case IP:
		try {
			exe.execute(thread);;;;;;;//ba'al, lord of semicolons. incur its wrath, and it will execute you.
		} catch (Throwable t) {
			t.printStackTrace();
		}
		break;
		case DUAL:
			exe.execute(()->{
				try{
					while(!Thread.currentThread().isInterrupted()){
						Thread.sleep(200);
//						final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "Team 5431", " ");
//						img = ImageIO.read(new BufferedInputStream(new SmbFileInputStream(new SmbFile("smb:\\\\Academytitans\\IMAGEUPDATE\\IR.png"))));
						final BufferedImage temprear = ImageIO.read(new URL(url+":8001/cam_1.jpeg"));
						if(temprear!=null){
							rear = temprear.getSubimage(0, 0, temprear.getWidth(), (int) (temprear.getHeight()*0.91));//get rid of the watermark. no one likes it
							invertImage(rear);
						}
					}
					}catch(Throwable t){
						System.err.println("Ignoring rear "+t);
					}
			});
		case KINECT:
			exe.execute(()->{
				try{
					while(!Thread.currentThread().isInterrupted()){
						try{
						Thread.sleep(1000);
//						final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "Team 5431", " ");
//						img = ImageIO.read(new BufferedInputStream(new SmbFileInputStream(new SmbFile("smb:\\\\Academytitans\\IMAGEUPDATE\\IR.png"))));
						final BufferedImage tempir=ImageIO.read(new URL(url+":8000/IR.jpeg"));

						if(tempir!=null){
							ir=tempir;
							invertImage(ir);
						}
						}catch(IOException e){
							System.err.println("Ignoring IR Image "+e);
						}
					}
					}catch(Throwable t){
						t.printStackTrace();
						
					}
			});
			exe.execute(()->{
				try{
				long startTime = System.currentTimeMillis();
				int delta = 0;
				int fps = 0;
				boolean lastNull=false;
				while(!Thread.currentThread().isInterrupted()){
					try{
					delta++;
					if(System.currentTimeMillis()>=startTime+1000){
						fps=delta;
						delta=0;
						startTime=System.currentTimeMillis();
						System.out.println("FPS: "+fps);
					}
//					final NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, "Team 5431", " ");
//					img = ImageIO.read(new BufferedInputStream(new SmbFileInputStream(new SmbFile("smb:\\\\Academytitans\\IMAGEUPDATE\\IR.png"))));
					final BufferedImage tempimg = ImageIO.read(new URL(url+":8000/Color.jpeg"));
					if(tempimg!=null){
						img = tempimg;
						invertImage(img);
						lastNull=false;
					}else{
						if(!lastNull){
							lastNull=true;
							continue;
						}
					}
					}catch(IOException e){
						System.err.println("Ignoring image "+e);
					}
					Thread.sleep(150);
				}
				}catch(Throwable t){
					t.printStackTrace();
					
				}
			});

			
	}
}
}
