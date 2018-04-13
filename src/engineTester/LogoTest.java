package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
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

public class LogoTest {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Display.setTitle("MRCEngine - LOGO 1.0");
		
		Loader loader = new Loader();
		
		ModelData sample = OBJFileLoader.loadOBJ("cube");
		RawModel sampleModel = loader.loadToVAO(sample.getVertices(), sample.getTextureCoords(), sample.getNormals(), sample.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("transparence_test"));
		texture.setShineDamper(25);
		texture.setReflectivity(0.8f);
		TexturedModel texturedModel = new TexturedModel(sampleModel, texture);
		texturedModel.getTexture().setHasTransparency(true);
		
		Player turtle = new Player(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		Light light = new Light(new Vector3f(1536, 5000, 1536), new Vector3f(1f, 1f, 1f)); // x, intensity, "y"
		
		Camera camera = new Camera();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("face"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("rock"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("green"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("water"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend_map"));
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("black"));
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
		
		MasterRenderer renderer = new MasterRenderer();

		
		while(!Display.isCloseRequested()) {
			
			camera.move();
			
			turtle.move();
			
			renderer.processTerrain(terrain);
			
			renderer.processEntity(turtle);
			
			renderer.render(light, camera);
			DisplayManager.setDisplayTitle(
					"MRCEngine - LOGO 1.0 - FPS: " + String.valueOf(DisplayManager.getFps())
			);
			DisplayManager.updateDisplay();

		}
	
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
	
	private static void loadFromFile(String filename) {
		// TODO: Load from CSV
	}
	
	private static void saveToFile(String filename) {
		// TODO: Save to CSV
	}

}
