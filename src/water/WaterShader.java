package water;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/water/waterVertexShader.glsl";
	//private static final String GEOMETRY_FILE = "src/water/waterGeometryShader.glsl";
	private final static String FRAGMENT_FILE = "src/water/waterFragmentShader.glsl";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_refractionTexture;
	private int location_reflectionTexture;
	private int location_UVMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColor;
	private int location_lightPos;
	private int location_depthMap;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_modelMatrix = super.getUniformLocation("modelMatrix");
		location_refractionTexture = super.getUniformLocation("refractionTexture");
		location_reflectionTexture = super.getUniformLocation("reflectionTexture");
		location_UVMap = super.getUniformLocation("UVMap");
		location_moveFactor = super.getUniformLocation("moveFactor");
		location_cameraPosition = super.getUniformLocation("cameraPosition");
		location_normalMap = super.getUniformLocation("normalMap");
		location_lightColor = super.getUniformLocation("lightColor");
		location_lightPos = super.getUniformLocation("lightPos");
		location_depthMap = super.getUniformLocation("depthMap");
		
	}
	
	public void loadMoveFactor(float factor)
	{
		super.loadFloat(location_moveFactor, factor);
	}
	
	public void loadLight(Light light)
	{
		super.loadVector(location_lightColor, light.getColor());
		super.loadVector(location_lightPos, light.getPosition());
	}
	
	
	public void connectTextureUnits()
	{
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_UVMap, 2);
		super.loadInt(location_normalMap, 3);
		super.loadInt(location_depthMap, 4);
	}
	
	
	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}
