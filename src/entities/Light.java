package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1, 0, 0);
	
	/*
	 * constructer for creating a 
	 * light without attenuation(sun)
	 */
	
	public Light(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
		
	}
	
	/*
	 * constructor to create a light 
	 * source with attenuation(pretty much any other
	 * light source)
	 */
	
	public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}
	
	/*
	 * just some getters and setters
	 */
	
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getColor() {
		return color;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public Vector3f getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(Vector3f attenuation) {
		this.attenuation = attenuation;
	}
	
	
	
	
	
}
