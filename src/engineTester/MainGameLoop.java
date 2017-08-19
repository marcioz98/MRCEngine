package engineTester;

import java.util.ArrayList;

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
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		ModelData sample = OBJFileLoader.loadOBJ("yasuo");
		RawModel sampleModel = loader.loadToVAO(sample.getVertices(), sample.getTextureCoords(), sample.getNormals(), sample.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("yasuo"));
		texture.setShineDamper(25);
		texture.setReflectivity(1);
		TexturedModel texturedModel = new TexturedModel(sampleModel, texture);
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		
		float distance = 125f;
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 20; j++) {
				for(int k = 0; k < 20; k++) {
					entities.add(new Entity(texturedModel, new Vector3f(i*distance, j*distance, k*distance), 0, 0, 0, 1));
				}
			}
		}
		
		// Entity entity = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1f);
		
		Light light = new Light(new Vector3f(-60f, 100f, 70f), new Vector3f(1.3f, 1.3f, 1.3f));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			
			camera.move();
			
			for(Entity entity : entities) {
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
