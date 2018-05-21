package Display;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class DisplayMaster 
{
	static int FPS_CAP = -1; 		  																//cap for f/s
	public static long lastFrameTime;																//
	public static float delta; 		  																//values for calculating the frametime
	
	public static void createDisplay(int width, int height)
	{
		
		try 
		{
			Display.setDisplayMode(new DisplayMode(width, height)); 								//makes diplay the before mentioned resolution
			Display.setTitle("noch früher als aplha hat halt nicht mal nen vernünftigen namen");	//sets title of the window
			Display.create(new PixelFormat().withSamples(8).withDepthBits(24));						//pixel format properties for opengl
			GL11.glEnable(GL13.GL_MULTISAMPLE); 													//enables sampling
		} catch (LWJGLException e) 																	//catches any exception in the above code
		{ 
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, width, height);
		lastFrameTime = getCurrentTime();
		
		
	}
	
	
	public static void setTitle(String title)
	{
		Display.setTitle(title);
	}
	
	
	public static void updateDisplay()
	{
		Display.sync(FPS_CAP); 																		//sync screen every FPS_CAP seconds
		Display.update(); 																			//updates display... duh!
		
																									/*
																									* some calcs for the frametime
																									*/
		long currentFrameTime = getCurrentTime();
		delta = currentFrameTime - lastFrameTime;
		lastFrameTime = getCurrentTime();
	}
	
	
	public static float getFrameTimeMillis()
	{
		return delta;
	}
	
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	
	public static long getCurrentTime()
	{
		return Sys.getTime() * 1000/Sys.getTimerResolution();                                                                              
	}
	

}
