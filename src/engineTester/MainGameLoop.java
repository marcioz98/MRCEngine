package engineTester;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		ModelData dragon = OBJFileLoader.loadOBJ("yasuo");
		RawModel dragonModel = loader.loadToVAO(dragon.getVertices(), dragon.getTextureCoords(), dragon.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("yasuo"));
		TexturedModel texturedModel = new TexturedModel(dragonModel, texture);
		
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		for(int i = 0; i < 200; i++) {
			entityList.add(new Entity(texturedModel, new Vector3f((float) Maths.randFloat(-30f, 30f),
																  (float) Maths.randFloat(-30f, 30f),
																  (float) Maths.randFloat(-100f, -70f)), 
					(float) Maths.randFloat(-20f, 20f), (float) Maths.randFloat(-20f, 20f), (float) Maths.randFloat(-5f, 5f), 0.05f));
		}
		
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			// entity.moveEntity(0.0f, 0.0f, -0.01f);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			for(Entity entity : entityList) {
				entity.rotateEntity((float) Maths.randFloat(0, 10f), (float) Maths.randFloat(0, 10f), (float) Maths.randFloat(0, 10f));
				renderer.render(entity, shader);
			}
			shader.stop();
			
			DisplayManager.updateDisplay();

		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
