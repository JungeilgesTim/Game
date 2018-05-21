package guis;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class GUIRenderer {
	
	private final RawModel quad;
	private GUIShader shader;
	
	/*
	 * constructor to create a new GUIRenderer
	 */
	
	public GUIRenderer(Loader loader)
	{
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1}; //defines vertices
		quad = loader.loadToVAO(positions, 2); //loads the quad to a vao
		shader = new GUIShader(); //creates new shader for the render() method
	}
	
	/*
	 * main GUIRender method
	 */
	
	public void render(List<GuiTexture> guis) //takes in a list of textures so multiple guis can get rendered more easily
	{
		shader.start(); //starts the guiShader
		GL30.glBindVertexArray(quad.getVaoID()); //binds the qoad object to a vao
		GL20.glEnableVertexAttribArray(0); //enables first(0) vbo in the vao
		GL11.glEnable(GL11.GL_BLEND); 												//these two lines
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);			//enable alphablending
		GL11.glDisable(GL11.GL_DEPTH_TEST); //makes the gui render above everything
		for(GuiTexture gui:guis)					  //for loop to render each texture in the list(see above) of textures					
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		} 
		GL11.glEnable(GL11.GL_DEPTH_TEST); //after rendering the depth test gets disabled
		GL11.glDisable(GL11.GL_BLEND);     //so does the aplhablending
		GL20.glDisableVertexAttribArray(0); //also the vbo gets disabled
		GL30.glBindVertexArray(0); //now all vaos get unbound
		shader.stop(); //lastly the shader gets stopped
	}
	
	/*
	 * method to free the ram after ending the game
	 */
	public void cleanUp()
	{
		shader.cleanUp();
	}
	

}
