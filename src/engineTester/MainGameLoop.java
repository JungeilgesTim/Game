package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Display.DisplayMaster;
import audio.AudioMaster;
import audio.Source;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GUIRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import skybox.SkyboxRenderer;
import stats.Health;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import toolbox.Methods;

public class MainGameLoop 
{
	
	
	public static float PLAYER_HEIGHT = 4f; 																										//will be added to cameras y for better visibility
	public static float rotY = 0;
	public static int gridX = 0;
	public static int gridZ = 0;
	public static int facing = 0;  
	
	

	public static void main(String[] args) {
		
		
		float health = 100; 																														//float to define health
		
		
		
		DisplayMaster.createDisplay(1920, 1080); 																									//create new Window
		Loader loader = new Loader();  																												//loader to load vaos and vbos
																																					//GUIShader guiShader = new GUIShader(); //shade for rendering GUI currently unused
		
		List<WaterTile> waters = new ArrayList<WaterTile>(); 																						// List for WaterTiles
		WaterTile water = new WaterTile(1024, 1024, 0); 																							// create one water tile at (x,z,y)
		waters.add(water); 																															// adds WaterTile to list
		
		AudioMaster.init();
		
																																					/*
																																					 * loads modeldata from .obj files
																																					 */
		
		ModelData data = OBJFileLoader.loadOBJ("small_tree");
		ModelData data2 = OBJFileLoader.loadOBJ("rock");
		ModelData data3 = OBJFileLoader.loadOBJ("bush1");
		ModelData data4 = OBJFileLoader.loadOBJ("berry_bush1");
		ModelData data5 = OBJFileLoader.loadOBJ("tree");
		ModelData data6 = OBJFileLoader.loadOBJ("bush2");
		ModelData data7 = OBJFileLoader.loadOBJ("rock2");
		ModelData data8 = OBJFileLoader.loadOBJ("lowPolySquirrel");
		ModelData data10 = OBJFileLoader.loadOBJ("lowPolyAstronaut");
		ModelData data11 = OBJFileLoader.loadOBJ("grass");
		ModelData data12 = OBJFileLoader.loadOBJ("lowPolyFish");
		
																																					/*
																																					 * terrain texturepacks
																																					 */
		
		TerrainTexture grass = new TerrainTexture(loader.loadTexture("texture_mars"));
		TerrainTexture grass1 = new TerrainTexture(loader.loadTexture("texture_grass1"));
		TerrainTexture stone = new TerrainTexture(loader.loadTexture("texture_stone"));
		TerrainTexture dirt = new TerrainTexture(loader.loadTexture("texture_dirt"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(grass,stone,dirt,grass1);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("texture_blendMap"));
		
		Terrain terrain1 = new Terrain(1, 1, loader, texturePack, blendMap, "heightMap");
		Terrain terrain2 = new Terrain(0, 0, loader, texturePack, blendMap, "heightMap");
		Terrain terrain3 = new Terrain(1, 0, loader, texturePack, blendMap, "heightMap");
		Terrain terrain4 = new Terrain(0, 1, loader, texturePack, blendMap, "heightMap");
			
		
																																					/*
																																					 * rawmodels loaded from modeldata
																										 											*/
		
		RawModel rockModel = loader.loadToVao(data2.getVertices(), data2.getTextureCoords(), data2.getNormals(), data2.getIndices());
		RawModel small_treeModel = loader.loadToVao(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		RawModel bush1Model = loader.loadToVao(data3.getVertices(), data3.getTextureCoords(), data3.getNormals(), data3.getIndices());
		RawModel berry_bush1Model = loader.loadToVao(data4.getVertices(), data4.getTextureCoords(), data4.getNormals(), data4.getIndices());
		RawModel treeModel = loader.loadToVao(data5.getVertices(), data5.getTextureCoords(), data5.getNormals(), data5.getIndices());
		RawModel bush2Model = loader.loadToVao(data6.getVertices(), data6.getTextureCoords(), data6.getNormals(), data6.getIndices());
		RawModel rock2Model = loader.loadToVao(data7.getVertices(), data7.getTextureCoords(), data7.getNormals(), data7.getIndices());
		RawModel squirrelModel = loader.loadToVao(data8.getVertices(), data8.getTextureCoords(), data8.getNormals(), data8.getIndices());
		RawModel astronautModel = loader.loadToVao(data10.getVertices(), data10.getTextureCoords(), data10.getNormals(), data10.getIndices());
		RawModel grassModel = loader.loadToVao(data11.getVertices(), data11.getTextureCoords(), data11.getNormals(), data11.getIndices());
		RawModel fishModel = loader.loadToVao(data12.getVertices(), data12.getTextureCoords(), data12.getNormals(), data12.getIndices());
		
																																					/*
																																					 * models with textures applied to them
																																					 */
		
		TexturedModel small_tree = new TexturedModel(small_treeModel,new ModelTexture(loader.loadTexture("texture_small_tree")));
		TexturedModel rock = new TexturedModel(rockModel, new ModelTexture(loader.loadTexture("texture_rock")));
		TexturedModel bush1 = new TexturedModel(bush1Model, new ModelTexture(loader.loadTexture("texture_bush1")));
		TexturedModel berry_bush1 = new TexturedModel(berry_bush1Model, new ModelTexture(loader.loadTexture("texture_berry_bush1")));
		TexturedModel tree = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("texture_tree"))); 
		TexturedModel bush2 = new TexturedModel(bush2Model, new ModelTexture(loader.loadTexture("texture_bush2")));
		TexturedModel rock2 = new TexturedModel(rock2Model, new ModelTexture(loader.loadTexture("texture_rock2")));
		TexturedModel squirrel = new TexturedModel(squirrelModel, new ModelTexture(loader.loadTexture("texture_lowPolySquirrel")));
		TexturedModel grass2 = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("texture_grass_entity")));
		TexturedModel fish = new TexturedModel(fishModel, new ModelTexture(loader.loadTexture("lowPolyFish")));
		
		
		TexturedModel astronaut = new TexturedModel(astronautModel, new ModelTexture(loader.loadTexture("texture_astronaut")));
		TexturedModel noastronaut = new TexturedModel(astronautModel, new ModelTexture(loader.loadTexture("texture_astronaut_invisible")));
		
		
		
		
		
		Player playerEntity = new Player(astronaut, new Vector3f(0, 0, 0), 0, 0, 0, 1); 															//creates playerEntity used for navigating world
		
		
																																					/*
																																					 * getting textures to (l.164)
																																					 */
		
		ModelTexture rockTexture = rock.getTexture();
		ModelTexture bush2Texture = bush2.getTexture();
		ModelTexture small_treeTexture = small_tree.getTexture();
		ModelTexture bush1Texture = bush1.getTexture();
		ModelTexture berry_bush1Texture = berry_bush1.getTexture();
		ModelTexture treeTexture = tree.getTexture();
		ModelTexture squirrelTexture = squirrel.getTexture();
		ModelTexture grassTexture = grass2.getTexture();
		ModelTexture fishTexture = fish.getTexture();
		
		
		Source source = new Source();
		source.setLooping(true);
		
		
		
																																					
		Health healthHelper = new Health();
																																					//Entity grassEntity = new Entity(grass2, new Vector3f(15, terrain1.getHeightOfTerrain(15, -15), -15), 0, 0, 0, 7f);
		Entity rockEntity = new Entity(rock2, new Vector3f(100, terrain1.getHeightOfTerrain(100, 100), 100), 0, 0, 0, 4f);
		
																																					/*
																																					 * set their properties
																																					 */
		
		squirrelTexture.setHasTransparency(true);
		grassTexture.setHasTransparency(true);
		
		fishTexture.setReflectivity(1);
		fishTexture.setShineDamper(10);
		
		treeTexture.setReflectivity(0);
		treeTexture.setShineDamper(10);
		
		bush2Texture.setReflectivity(0);
		bush2Texture.setShineDamper(10);
		
		bush1Texture.setReflectivity(0);
		bush1Texture.setShineDamper(10);
		
		berry_bush1Texture.setReflectivity(0);
		berry_bush1Texture.setShineDamper(10);
		
		rockTexture.setReflectivity(0);
		rockTexture.setShineDamper(10);
		
		small_treeTexture.setReflectivity(0);
		small_treeTexture.setShineDamper(0);
		
																																					/*
																																					 * list of terrains 
																																					 */
		
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		terrains.add(terrain1);
		terrains.add(terrain2);
		terrains.add(terrain3);
		terrains.add(terrain4);
		
																																					/*
																																					 * list of lights
																																					 */
		
		List<Light> lights = new ArrayList<Light>();
		Light light1 = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1,1,1));
		lights.add(light1);
		
																																					/*
																																					 * list of entities, player included
																																					 */
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> fishes = new ArrayList<Entity>();
		List<Entity> rocks = new ArrayList<Entity>();
		Random random = new Random();
		
		
																																					
		
		for(int i = 0; i < 100; i++)
		{
			float x = random.nextFloat() * 600;
			float z = random.nextFloat() * -800;
			float y = terrain2.getHeightOfTerrain(x, z);
			
				entities.add(new Entity(tree, new Vector3f(x, y, z), 0 ,0, 0, 1));
		
		}
		
		
		
		for(int i = 0; i < 100; i++)
		{
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * - 600;
			float terrainHeight = terrain1.getHeightOfTerrain(x, z);
			float roty = random.nextFloat() * 360;
			if(water.getHeight() - terrainHeight < 2)
			{
																																					//System.out.println("aaa");
				float rand = water.getHeight() - terrainHeight;
				float y = random.nextFloat() * rand;
				if (y < 0)
				{
					y = y * -1;
				}
				if(y > terrainHeight) {
					System.out.println("bbb");
				fishes.add(new Entity(fish, new Vector3f(x, y, z), 0, roty, 0 ,0.5f));
				}
			}
		}
		
		for(int i = 0; i < 200 ; i++)
		{
			float x = random.nextFloat() * 800;
			float z = random.nextFloat() * 600;
			float y = terrain2.getHeightOfTerrain(x, z);
																																					//if(y > water.getHeight() + 1)
			
				rocks.add(new Entity(rock2, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 5));
				entities.add(new Entity(rock2, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 5));
			

		}
		
		
		
		
		
																																					/*
																																					 * adding more entities to list
																																					 */
		
		
																																					//entities.add(grassEntity);
																																					//entities.add(rockEntity);
		entities.add(playerEntity);
	

		
		
		
		
		
		
		
		WaterFrameBuffers fbos = new WaterFrameBuffers(); 																							//framebuffers for watershading
		GUIRenderer guiRenderer = new GUIRenderer(loader);																							//renderer to render guis currently not included in masterrenderer.render function
		Camera camera = new Camera(playerEntity); 																									//new camera takes in player for position
		MasterRenderer renderer = new MasterRenderer(loader, camera); 																				//masterRenderer takes part of most of the rendering
		WaterShader waterShader = new WaterShader();																								//watershader for watereffects
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(),fbos); 									//watererenderer to render water currently not included in masterRenderer.render function
		SkyboxRenderer skyboxRenderer = new SkyboxRenderer(loader, renderer.getProjectionMatrix());													//renderer for skybox currently not included in masterRenderer.render function as to avoid bugs that are currently not fixable for masterRenderer
		
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>(); 																						//list for guis
		GuiTexture gui1 = new GuiTexture(loader.loadTexture("texture_gui_health"), new Vector2f(0.9f, -0.9f), new Vector2f(0.2f,0.2f)); 			//creating new gui
		guis.add(gui1);																																// adding gui to list
		
		
		
		source.setPosition(0, playerEntity.getPosition().y, 0);
		source.setPitch(0.5f);
		source.setVolume(2);
																																					//source.play(buffer2);
		
		
	
				while(!Display.isCloseRequested()) {																								 //is run while the window is opened
					
					
					
					
						
					
					AudioMaster.setListenerData(playerEntity.getPosition().x, playerEntity.getPosition().y, playerEntity.getPosition().z);
					
						gridX = playerEntity.getGridX(playerEntity.getPosition().x);
						gridZ = playerEntity.getGridZ(playerEntity.getPosition().z);
						
						if(gridX == 1 && gridZ == 1)
						{
							playerEntity.move(camera, terrain1, rocks); 																			//move function for player
																																					//System.out.println("player is moving on terrain1");
						}
						else if(gridX == 0 && gridZ == 0)
						{
							playerEntity.move(camera, terrain2, rocks); 																			//move function for player
																																					//System.out.println("player is moving on terrain2");

						}
						else if(gridX == 1 && gridZ == 0)
						{
							playerEntity.move(camera, terrain3, rocks); 																			//move function for player
																																					//System.out.println("player is moving on terrain3");

						}
						else if(gridX == 0 && gridZ == 1)
						{
							playerEntity.move(camera, terrain4, rocks); 																			//move function for player
																																					//System.out.println("player is moving on terrain4");

						}
						
																																					/*if(Keyboard.isKeyDown(Keyboard.KEY_K))
																																					{
																																						Methods.spawnEntity(0, terrain1.getHeightOfTerrain(0, 0), 0, 0, 0, 0, 1, entities, fish);
																																					}*/
						renderer.processEntity(rockEntity);
						playerEntity.setModel(astronaut);
						
						camera.setPosition(new Vector3f(0, PLAYER_HEIGHT, 0)); 																		//set camerapos
																																					//render function for shadows
						health = healthHelper.regenerateHealth(health, (float) 0.01); 																//slowly regenerates health
						camera.move(entities, playerEntity);																						//moves camera
						renderer.processGUI(gui1); 																									//adds gui to masterrenderer
						GL11.glEnable(GL30.GL_CLIP_DISTANCE0); 																						//lol idk
						
						fbos.bindReflectionFrameBuffer(); 																							//binds reflectionbuffer for water
						
																																					/*
																																					 * just some maths and rendering to calculate the reflection 
																																					 */
						
						float distance = 2 * (camera.getPosition().y - water.getHeight());
						camera.getPosition().y -= distance;
						camera.invertPitch();
						camera.invertRoll();
																																					//renderer.renderShadowMap(entities, light1);
						renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() - 1 ));
						skyboxRenderer.render(camera);
						camera.getPosition().y += distance;
						camera.invertPitch();
						camera.invertRoll();
						
						fbos.unbindCurrentFrameBuffer();
				
						fbos.bindRefractionFrameBuffer(); 																							//binds refraction (whats underwater)buffer
						
																																					/*
																																					 * again simple calculations
																																					 */
																																					//renderer.renderShadowMap(entities, light1);
						skyboxRenderer.render(camera);
						renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight() + 0.07f));
						fbos.unbindCurrentFrameBuffer();
																																					//System.out.println("x: " + playerEntity.getPosition().x + " y: " + playerEntity.getPosition().z);


						
						GL11.glDisable(GL30.GL_CLIP_DISTANCE0); 																					//i still dont know
						
						
							
							
							renderer.renderShadowMap(entities, light1); 																			//renders shadowmap for shadows
							if(camera.isFirstPerson == true)
								{
									playerEntity.setModel(noastronaut);
									
								}
							else 
								{
									playerEntity.setModel(astronaut);
								}
							Random rand = new Random();
							
						
							
							for(Entity fish1:fishes)
							{
								renderer.processEntity(fish1);
								float speed = rand.nextFloat() * 15;
								float rotY = (float) ((rand.nextFloat()) * 0.75);
								fish1.fishMove(fish1, speed, rotY, water, terrain1);
								
							}
							
																																					//System.out.println(playerEntity.getRotY());
																																					//System.out.println(playerEntity.getPosition().x + " : " + playerEntity.getPosition().z);
				
						renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 0, 0, 0));											//currently renders entities terrains camera and lights
						waterRenderer.render(waters, camera, light1); 																				//renders water
						skyboxRenderer.render(camera); 																								//renders skybox
						guiRenderer.render(guis); 																									//renders guis above everything
						DisplayMaster.updateDisplay(); 																								//updates display
						
						
				}
				
				
				
				
																																					/*
																																					* cleanup methods for when the game gets closed
																																					*/
				
		source.delete();
		fbos.cleanUp();
		waterShader.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayMaster.closeDisplay();
		AudioMaster.cleanUp();
	}

}
