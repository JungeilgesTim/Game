package entities;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import textures.ModelTexture;

public class Camera {
	
	private float distanceFromPlayer = 50;
	float angleAroundPlayer = 0;
	public boolean isFirstPerson = false;
	public boolean shouldRenderModel = true;
	
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 45;
	private float yaw = 0;
	private float roll = 0;
	public boolean enableZoom = true;
	private Player player;
	private float prevDist; 
	private float keyCoolDown = 2;
	public float minPitch = 0;
	private float maxPitch = 90;
	private float heightAdd = 4;
	Loader loader =  new Loader();
	ModelData data10 = OBJFileLoader.loadOBJ("lowPolyAstronaut");
	RawModel playerModel = loader.loadToVao(data10.getVertices(), data10.getTextureCoords(), data10.getNormals(), data10.getIndices());
	
	
	
	public Camera(Player player)
	{
		this.player = player;
	}
	
	
		/*
		 * this method is responsible for moving 
		 * the player entity when certain keys 
		 * are pressed
		*/
	
	
	public void move(List<Entity> entities,Player player)
	{
		if(enableZoom == true)
		{
		calculateZoom();
		}
		calculatePitch();
		calculateAngleAroundPlayer();
		
		
		if(this.pitch < minPitch)
		{
			this.pitch = minPitch;
		}
		
		if(this.pitch > maxPitch)
		{
			this.pitch = maxPitch;
		}
		
		if(distanceFromPlayer < 8f)
		{
			if(isFirstPerson == false)
			{
			this.distanceFromPlayer = 8;
			}
		}
		
		if(distanceFromPlayer > 100f)
		{
			this.distanceFromPlayer = 100;
		}
		
		
			if(Keyboard.isKeyDown(Keyboard.KEY_G))
			{
				if(isFirstPerson == false)
				{
					
					if(keyCoolDown >= 2)
					{
						shouldRenderModel = false;
						keyCoolDown = 0;
						heightAdd = 8;
						prevDist = this.distanceFromPlayer;
						isFirstPerson = true;
						enableZoom = false;
						this.distanceFromPlayer = 0f;
						minPitch = -90;
						maxPitch = 90;
						TexturedModel playerinvis = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("texture_astronaut_invisible")));
						player.setModel(playerinvis);
					}
					
				}
				else if(isFirstPerson == true)
				{
					
					if(keyCoolDown >= 2)
					{
						shouldRenderModel = true;
						heightAdd = 4;
						keyCoolDown = 0;
						isFirstPerson = false;
						enableZoom = true;
						minPitch = 0;
						maxPitch = 90;
						this.distanceFromPlayer = prevDist;
						TexturedModel playervis = new TexturedModel(playerModel, new ModelTexture(loader.loadTexture("texture_Astronaut")));
						player.setModel(playervis);
					}
				}
				
			}
			
				
			
				
			
		
		
		keyCoolDown+=0.01f;
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		
		Mouse.setGrabbed(true);
		
			Mouse.setGrabbed(true);
		calculatePitch();
		
			player.increaseRotation(0, angleAroundPlayer, 0);
			angleAroundPlayer = 0;
		
	
	}
	
	public void shake(Player player, Camera camera)
	{
		if(player.getPosition().y + 4 < 10)
		{
			camera.setPosition(new Vector3f());
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	/*
	 * this method is called every 
	 * frame to calculate the
	 * cameras position relative 
	 * to the player
	 */
	
	private void calculateCameraPosition(float hDistance, float vDistance)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.y = player.getPosition().y + vDistance + heightAdd;
		position.z = player.getPosition().z - offsetZ;
				}
	
	/*
	 * just some getters and setters
	 */
	
	public void invertPitch()
	{
		this.pitch = -pitch;
	}
	
	public void invertYaw()
	{
		this.yaw = - yaw;
	}
	
	public void invertRoll() 
	{
		this.roll = -roll;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	private float calculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	/*
	 * this method gets called to calculate
	 * the zoomlevel ( the distance
	 * from the camera to the player)
	 */
	
	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		distanceFromPlayer -= zoomLevel;
		//System.out.println(distanceFromPlayer);
	}
	
	/*
	 * calculates the cameras pitch
	 * based on the mouses y position
	 */
	
	private void calculatePitch()
	{
		float pitchChange = Mouse.getDY() * 0.1f;
		pitch -= pitchChange;
	}
	
	/*
	 * calculates the cameras roll 
	 * according to the mouses x movement
	 */
	
	private void calculateAngleAroundPlayer()
	{
		float angleChange = Mouse.getDX() * 0.07f;
		angleAroundPlayer -= angleChange;
	}
	
	
	
	
	
	
	
}
