package toolbox;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import models.TexturedModel;

public class Methods 																																			//class to store various methods
{
	
	public static void spawnEntity(float x, float y, float z, float rotX, float rotY, float rotZ, float scale, List<Entity> list, TexturedModel model)			//method to spawn an Entity will be moved later
	{
		list.add(new Entity(model, new Vector3f(x, y, z), rotX, rotY, rotZ, scale));
	}
	
}
