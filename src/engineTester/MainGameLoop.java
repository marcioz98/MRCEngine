package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		ModelData sample = OBJFileLoader.loadOBJ("player");
		RawModel sampleModel = loader.loadToVAO(sample.getVertices(), sample.getTextureCoords(), sample.getNormals(), sample.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("yellow"));
		texture.setShineDamper(25);
		texture.setReflectivity(0.8f);
		TexturedModel texturedModel = new TexturedModel(sampleModel, texture);
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		/*
		float distance = 10f;
		for(int i = 1; i < 30; i++) {
			for(int j = 1; j < 30; j++) {
					entities.add(new Entity(texturedModel, new Vector3f(i*distance, 0, j*distance), 0, Maths.randFloat(0.0f, 360.0f), 0, 2.5f));
			}
		}
		*/
		
		entities.add(new Entity(texturedModel, new Vector3f(150, 0, 150), 0, Maths.randFloat(0.0f, 360.0f), 0, 25f));
		
		Light light = new Light(new Vector3f(0, 100000, 0), new Vector3f(1f, 1f, 1f)); // x, intensity, "y"
		
		Camera camera = new Camera();
		camera.setPosition(new Vector3f(2f, 2f, 2f));
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("default_background"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("rock"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("green"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("water"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend_map"));
		
		ArrayList<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(new Terrain(0, 0, loader, texturePack, blendMap));
				
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			
			camera.move();			
			
			for(Terrain terrain : terrains) {
				renderer.processTerrain(terrain);
			}
			
			for(Entity entity : entities) {
				entity.rotateEntity(0, Maths.randFloat(2f, 5f), 0);
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();

		}
	
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
