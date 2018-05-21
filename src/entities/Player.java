package entities;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import Display.DisplayMaster;
import models.TexturedModel;
import terrain.Terrain;

public class Player extends Entity{
	
	
	//____________________________________________________________________________\\
	
	private static final float CROUCH_SPEED = 20;
	private static final float WALK_SPEED = 40;
	private static final float RUN_SPEED = 80;
	private static final float SIDE_SPEED = 40;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private float currentSpeed = 0;
	private float currentSideSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	
	
	//____________________________________________________________________________\\
	
	/*
	 * constructor for creating a playerobject
	 */
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
		{
			super(model, position, rotX, rotY, rotZ, scale);
		}

	/*
	 * getters and setters
	 */
	
	public float getUpwardsSpeed() 
	{
		return upwardsSpeed;
	}
	

	public void setUpwardsSpeed(float upwardsSpeed) 
	{
		this.upwardsSpeed = upwardsSpeed;
	}
	
	
	public int getGridX(float x)
	{
		float gridX = x / 1024;
		int gridXReturn = 0;
		if(gridX < 0)
		{
			gridXReturn = (int) gridX - 1;
		}
		else if(gridX > 0)
		{
			gridXReturn = (int) gridX;
		}
		return gridXReturn;
	}
	
	
	public int getGridZ(float z)
	{
		float gridZ = z / 1024;
		int gridZReturn = 0;
		if(gridZ < 0)
		{
			gridZReturn = (int) gridZ - 1;
		}
		else if(gridZ > 0)
		{
			gridZReturn = (int) gridZ;
		}
		return gridZReturn;
	}
	
	
	/*
	 * methods to control 
	 * the playerentities movement
	 */
	
	

	public void move(Camera camera, Terrain terrain, List<Entity> entities)
	{
		if(upwardsSpeed < -100)
			{
				upwardsSpeed = -100;
			}
		
		if(super.getRotY() > 360)
			{
				super.setRotY(0);
			}
						
		if(super.getRotY() < 0)
			{
				super.setRotY(360);
			}
		
		
		checkInputs(camera);
		
		float distance = currentSpeed * (DisplayMaster.getFrameTimeMillis() / 1000);
		float sideDistancex = currentSideSpeed * (DisplayMaster.getFrameTimeMillis() / 1000);
		float sideDistancez = -currentSideSpeed * (DisplayMaster.getFrameTimeMillis() / 1000);
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		
		float dx2 = (float) (sideDistancex * Math.cos(Math.toRadians(super.getRotY())));
		float dz2 = (float) (sideDistancez * Math.sin(Math.toRadians(super.getRotY())));
				
		super.increasePosition(dx, 0, dz);
		super.increasePosition(dx2, 0, dz2);
		upwardsSpeed += GRAVITY *(DisplayMaster.getFrameTimeMillis() / 1000);
		super.increasePosition(0, upwardsSpeed * (DisplayMaster.getFrameTimeMillis() / 1000), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		
		if(super.getPosition().y < terrainHeight)
		{
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
		
		if(super.getPosition().y > 16384)
		{
			super.getPosition().y = 16384;
		}
		
		for(Entity entity:entities) 
		{
			if(super.getPosition().x > entity.getPosition().x - 3 && super.getPosition().x < entity.getPosition().x + 3 &&
					super.getPosition().z > entity.getPosition().z - 3 && super.getPosition().z < entity.getPosition().z + 3 &&
					super.getPosition().y < terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z) + 2)
			{
				
						super.getPosition().y = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z) + 2;
					
			}
		}
		
		if(super.getPosition().x > 2048)
		{
			super.getPosition().x = 1;
		}
		
		if(super.getPosition().x < 0)
		{
			super.getPosition().x = 2047;
		}
		
		if(super.getPosition().z > 2048)
		{
			super.getPosition().z = 1;
		}
		
		if(super.getPosition().z < 0)
		{
			super.getPosition().z = 2047;
		}
		
		
	}
	
		
	
	
	
	public int getFacing()
	{
		int facing = 0;
		if(super.getRotY() > 315 && super.getRotY() < 360 || super.getRotY() < 45 && super.getRotY() > 0)
						{
							//System.out.println("player is facing z+");
							facing = 0;
						}
						
						if(super.getRotY() > 45 && super.getRotY() < 135)
						{
							//System.out.println("player is facing x+");
							facing = 1;
						}
						
						if(super.getRotY() > 135 && super.getRotY() < 225)
						{
							//System.out.println("player is facing z-");
							facing = 2;
						}
						
						if(super.getRotY() > 225 && super.getRotY() < 315)
						{
							//System.out.println("player is facing x-");
							facing =3;
						}
		
		return facing;
	}
	
	public String getFacingCoords()
	{
		String facing = "undefined";
		if(super.getRotY() > 315 && super.getRotY() < 360 || super.getRotY() < 45 && super.getRotY() > 0)
						{
							//System.out.println("player is facing z+");
							facing = "z+";
						}
						
						if(super.getRotY() > 45 && super.getRotY() < 135)
						{
							//System.out.println("player is facing x+");
							facing = "x+";
						}
						
						if(super.getRotY() > 135 && super.getRotY() < 225)
						{
							//System.out.println("player is facing z-");
							facing = "z-";
						}
						
						if(super.getRotY() > 225 && super.getRotY() < 315)
						{
							//System.out.println("player is facing x-");
							facing = "x-";
						}
		
		return facing;
	}
	
	
		
	/*public  void fly()
	{
		if(fuel > 0)
		{
			if(upwardsSpeed < 150f)
			{
				 this.upwardsSpeed+=THRUST_POWER;
				 //System.out.println(upwardsSpeed);
			}
		fuel-= 0.007f;
		//System.out.println(fuel);
		}
	}*/
		
	public void jump()
	{
		this.upwardsSpeed = JUMP_POWER;
		isInAir = true;
	}
	
	private void checkInputs(Camera camera)
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			{
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			{
				
				if(currentSpeed < CROUCH_SPEED)
				{
					this.currentSpeed+=(0.25f * DisplayMaster.getFrameTimeMillis());
				
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				if(currentSpeed < RUN_SPEED) 
				{
					this.currentSpeed+=(0.5f * DisplayMaster.getFrameTimeMillis());
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				if(currentSpeed < WALK_SPEED) 
				{
					
					this.currentSpeed+=(0.5f * DisplayMaster.getFrameTimeMillis());
				}
			}
			}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
			{
			if(currentSpeed > -WALK_SPEED) 
			{
				this.currentSpeed-=(0.5f * DisplayMaster.getFrameTimeMillis());
			}
			}
		else if(currentSpeed > 0)
		{
			this.currentSpeed-=(0.5f * DisplayMaster.getFrameTimeMillis());
		}
		else if (currentSpeed < 0)
		{
			this.currentSpeed+=(0.5f * DisplayMaster.getFrameTimeMillis());

		}
		
			
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) 
		{
			if(currentSideSpeed > -SIDE_SPEED)
			{
				this.currentSideSpeed-=(0.5f * DisplayMaster.getFrameTimeMillis());
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			if(currentSideSpeed < SIDE_SPEED)
			{
				this.currentSideSpeed+=(0.5f * DisplayMaster.getFrameTimeMillis());
			}
		}
		else if(currentSideSpeed > 0)
		{
			this.currentSideSpeed-=(0.5f * DisplayMaster.getFrameTimeMillis());

		}
		else if(currentSideSpeed < 0)
		{
			this.currentSideSpeed+=(0.5f * DisplayMaster.getFrameTimeMillis());

		}
		
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir)
		{
			
			jump();
			
		}
		
	}
	
	
	

}
