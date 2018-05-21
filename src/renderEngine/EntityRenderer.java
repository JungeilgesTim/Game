package renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class EntityRenderer {
	
	private StaticShader shader; //this is a shader :D
	
	/*
	 * constructor for a new EntityRenderer
	 */
	
	public EntityRenderer(StaticShader shader,Matrix4f projectionMatrix)
	{
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	/*
	 * render method takes in the entities 
	 * with the corresponding TexturedModel
	 */
	
	
	public void render(Map<TexturedModel,List<Entity>> entities)
	{
		for(TexturedModel model:entities.keySet())
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				
				
			}
			unbindTexturedModel();
		}
	}
	
	/*
	 * prepares the texturedmodel to be rendered by 
	 * loading it to a VAO and filling its VBOs 
	 */
	
	private void prepareTexturedModel(TexturedModel model) 
	{
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		if(texture.isHasTransparency())
		{
			MasterRenderer.disableCulling();
		}
		shader.loadFakeLightingVariable(texture.isUseFakeLighting());
		shader.loadShineVariable(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	/*
	 * disable all by the entityRenderer used VAOs
	 */
	
	
	private void unbindTexturedModel ()
	{                                                                                                                                                            
		MasterRenderer.enableCulling();                                                                       
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	/*
	 * loads a transformationmatrix for the staticShader
	 */
	
	
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);	
	}
	
	
	

}
