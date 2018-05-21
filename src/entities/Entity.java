package entities;


import org.lwjgl.util.vector.Vector3f;

import Display.DisplayMaster;
import models.TexturedModel;
import terrain.Terrain;
import water.WaterTile;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;
	
	/*
	 * constructer to create an entity
	 * in the mainGameLoop
	 */
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void fishMove(Entity fish, float speed,float rotY, WaterTile water, Terrain terrain)
	{
		float distance = speed * (DisplayMaster.getFrameTimeMillis() / 1000);
		float dx = (float) (distance * Math.cos(Math.toRadians(fish.getRotY())));
		float dz = (float) (distance * Math.sin(Math.toRadians(fish.getRotY())));
		
			fish.increasePosition(-dx, 0, dz);
			
			if(fish.getPosition().y < terrain.getHeightOfTerrain(fish.getPosition().x, fish.getPosition().z) + 2)
				{
					fish.rotY += rotY;
				}
		
	}
	
	/*
	 * method to move an entities in the world
	 * while mainGameLoop is running
	 */
	
	public void increasePosition(float dx, float dy, float dz)
	{
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	/*
	 * method to change entities size (scale)
	 */
	
	public void increaseScale(float dscale)
	{
		this.scale+=dscale;
	}
	/*
	 * method to change one entitites rotation
	 */
	
	public void increaseRotation(float drx, float dry, float drz)
	{
		this.rotX+=drx;
		this.rotY+=dry;
		this.rotZ+=drz;
	}
	
	/*
	 * just some getters and setters
	 */
	
	
	public TexturedModel getModel() {
		return model;
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	public float getRotZ() {
		return rotZ;
	}
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
	
	
	

}
