package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int FPS = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("MRCEngine");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
		
	}
	
	public static void setDisplayTitle(String newTitle) {
		Display.setTitle(newTitle);
	}
	
	public static void updateDisplay() {
		Display.sync(FPS);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f; 
		lastFrameTime = currentFrameTime;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}
	
	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static int getWidth() {
		return WIDTH;
	}
	

	public static int getHeight() {
		return HEIGHT;
	}

	public static int getFps() {
		return FPS;
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

}
