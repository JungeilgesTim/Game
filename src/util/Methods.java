package util;


public class Methods {
	
	
	
	
	
	
	
	
	
	/*
	 * THIS CLASS IS CURRENTLY NOT NEEDED!!!!!
	 */
	
	/**public static void fullscreen()
	{
	
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
	        try {
	            Display.setFullscreen(!Display.isFullscreen());
	            if (!Display.isFullscreen()) {
	                Display.setResizable(resizable);
	                Display.setDisplayMode(new DisplayMode(800, 600));
	                glViewport(0, 0, Display.getWidth(), Display.getHeight());
	                glMatrixMode(GL_PROJECTION);
	                glLoadIdentity();
	                gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
	                glMatrixMode(GL_MODELVIEW);
	                glLoadIdentity();
	            } else {
	                glViewport(0, 0, Display.getWidth(), Display.getHeight());
	                glMatrixMode(GL_PROJECTION);
	                glLoadIdentity();
	                gluPerspective(fov, (float) Display.getWidth() / (float) Display.getHeight(), zNear, zFar);
	                glMatrixMode(GL_MODELVIEW);
	                glLoadIdentity();
	            }
	        } catch (LWJGLException e) {
	            e.printStackTrace();
	        }
	    }

}*/
}
