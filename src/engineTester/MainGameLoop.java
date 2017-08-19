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
import toolbox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		ModelData sample = OBJFileLoader.loadOBJ("cube");
		RawModel sampleModel = loader.loadToVAO(sample.getVertices(), sample.getTextureCoords(), sample.getNormals(), sample.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("face"));
		texture.setShineDamper(25);
		texture.setReflectivity(0.8f);
		TexturedModel texturedModel = new TexturedModel(sampleModel, texture);
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		
		float distance = 100f;
		for(int i = 1; i < 30; i++) {
			for(int j = 1; j < 30; j++) {
					entities.add(new Entity(texturedModel, new Vector3f(i*distance, 0, j*distance), 0, Maths.randFloat(0.0f, 360.0f), 0, 25));
			}
		}
		
		entities.add(new Entity(texturedModel, new Vector3f(1500, 150, 1500), 0, Maths.randFloat(0.0f, 360.0f), 0, 250));
		
		Light light = new Light(new Vector3f(1500, 2000, 1500), new Vector3f(1f, 1f, 1f)); // x, intensity, "y"
		
		Camera camera = new Camera();
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("green"));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				terrains.add(new Terrain(i, j, loader, terrainTexture));
			}
		}
		
		
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
