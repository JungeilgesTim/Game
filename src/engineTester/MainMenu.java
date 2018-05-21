package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Display.DisplayMaster;
import guis.GUIRenderer;
import guis.GuiTexture;
import renderEngine.Loader;

public class MainMenu 
{
	
	public static void main(String[] args)
	{
		DisplayMaster.createDisplay(1920, 1080);
		Loader loader = new Loader();
		GUIRenderer guiRenderer = new GUIRenderer(loader);
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		
		while(!Display.isCloseRequested())
		{
			guiRenderer.render(guis);
			DisplayMaster.updateDisplay();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{
				DisplayMaster.closeDisplay();
				MainGameLoop.main(null);
			}
		}
	}
}
