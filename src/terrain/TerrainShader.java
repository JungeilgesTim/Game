package terrain;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Light;
import shaders.ShaderProgram;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram{
	
	private static final int MAX_LIGHTS = 4;
	private static final String VERTEX_FILE = "src/terrain/terrainVertexShader.glsl";
	//private static final String GEOMETRY_FILE = "src/shaders/terrainGeometryShader.glsl";
	private static final String FRAGMENT_FILE = "src/terrain/terrainFragmentShader.glsl";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_attenuation[];
	private int location_lightColor[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_plane;
	private int location_terrainHeight;
	private int location_r;
	//private int location_g;
	//private int location_b;
	
	
	
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
	}

	@Override
	protected void bindAttributes() {
		
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix= super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_plane = super.getUniformLocation("plane");
		location_terrainHeight = super.getUniformLocation("terrainHeight");
		location_r = super.getUniformLocation("r");
		//location_g = super.getUniformLocation("g");
		//location_b = super.getUniformLocation("b");
		
		
		
		
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	
	
	
	
	
	public void loadTerrainHeight(float height)
	{
		super.loadFloat(location_terrainHeight, height);
	}
	
	public void loadR(float height)
	{
		float rValue = (1 * height) / 15;
		System.out.println(rValue);
		super.loadFloat(location_r, rValue);
	}
	
	
	public void connectTextureUnits()
	{
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
		super.loadInt(location_shadowMap, 5);
		
	}
	
	public void loadPlane(Vector4f plane)
	{
		super.loadVector(location_plane, plane);
	}
	
	public void loadToShadowMapSpace(Matrix4f matrix)
	{
		super.loadMatrix(location_toShadowMapSpace, matrix);
	}
	
	public void loadSkyColor(float r, float g, float b)
	{
		super.loadVector(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void loadShineVariable(float shineDamper, float reflectivity)
	{
		super.loadFloat(location_shineDamper, shineDamper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights)
	{
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			if(i<lights.size())
			{
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
			}
			else
			{
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
			
		}
		
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	
	
	

	
	
	
	
	

}
