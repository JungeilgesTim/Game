package renderEngine;

import org.lwjgl.opengl.Display;

import Display.DisplayMaster;

public class ErrorMessage {
	
	public static void main(String[] args) 
	{
		DisplayMaster.createDisplay(200, 100);
		DisplayMaster.setTitle("Error");
		
		while(!Display.isCloseRequested())
		{
			DisplayMaster.updateDisplay();
			
		}
		
	}



}
