package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
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

public class GameOfLifeTest {
	
	private static final int BOARD_WIDTH = 512;
	private static final int BOARD_HEIGHT = 512;

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Display.setTitle("MRCEngine - Game of Life Test");
		
		Loader loader = new Loader();
		
		ModelData sample = OBJFileLoader.loadOBJ("cat");
		RawModel sampleModel = loader.loadToVAO(sample.getVertices(), sample.getTextureCoords(), sample.getNormals(), sample.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture("cat"));
		texture.setShineDamper(25);
		texture.setReflectivity(0.8f);
		TexturedModel texturedModel = new TexturedModel(sampleModel, texture);
		
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		int[][] matrix = new int[BOARD_WIDTH][BOARD_HEIGHT];
		
		
		//Table initialization
		float distance = 7.5f;
		for(int i = 1; i < BOARD_WIDTH; i++) {
			for(int j = 1; j < BOARD_HEIGHT; j++) {
				if((int) (Math.random() * 2) > 0.2) {
					entities.add(new Entity(texturedModel, new Vector3f(i*distance, 0, j*distance), 0, Maths.randFloat(0.0f, 360.0f), 0, 2.5f));
					matrix[i][j] = 1;
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		
		// entities.add(new Entity(texturedModel, new Vector3f(1500, 150, 1500), 0, Maths.randFloat(0.0f, 360.0f), 0, 250));
		
		Light light = new Light(new Vector3f(500, 200, 300), new Vector3f(1f, 1f, 1f)); // x, intensity, "y"
		
		Camera camera = new Camera();
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		
		ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("black"));
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 6; j++) {
				terrains.add(new Terrain(i, j, loader, terrainTexture));
			}
		}
		
		
		ArrayList<Entity> tempEntities = new ArrayList<Entity>();
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			
			camera.move();
			
			shuffleMatrix(matrix);
			matrix = parseMatrix(matrix);
			entities = updateRender(matrix, entities, texturedModel);
			
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
	
	private static int[][] parseMatrix(int[][] matrix){
		
		int[][] tempMatrix = new int[BOARD_WIDTH][BOARD_HEIGHT];
		for(int i = 0; i < tempMatrix.length; i++)
		    tempMatrix[i] = matrix[i].clone();
		
		for(int x = 0; x < tempMatrix.length; x++) {
			for(int y = 0; y < tempMatrix[x].length; y++) {
				int temp = 0;

				try {
					temp += tempMatrix[x][y-1];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x][y+1];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x-1][y];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x+1][y];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x-1][y-1];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x+1][y+1];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x-1][y+1];
				} catch(Exception e) {}

				try {
					temp += tempMatrix[x+1][y-1];
				} catch(Exception e) {}
				
				if(tempMatrix[x][y] == 1 && temp < 2 || temp > 3) {
				    matrix[x][y] = 0;
				}
	               

	            if(tempMatrix[x][y] == 0 && temp == 3) {
	            	matrix[x][y] = 1;
	            }
			}
		}			
		
		return matrix;
	}
	
	private static void shuffleMatrix(int[][] matrix){
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {
			for(int x = 0; x < BOARD_WIDTH; x++) {
				for(int y = 0; y < BOARD_HEIGHT; y++) {
					if((int) (Math.random() * 2) > 0.2 && matrix[x][y] == 0) {
						matrix[x][y] = 1;
					}
				}
			}
		}
	}
	
	private static ArrayList<Entity> updateRender(int[][] matrix, ArrayList<Entity> entities, TexturedModel texturedModel) {
		entities.clear();
		float distance = 7.5f;
		for(int i = 1; i < BOARD_WIDTH; i++) {
			for(int j = 1; j < BOARD_HEIGHT; j++) {
				if(matrix[i][j] == 1) {
					entities.add(new Entity(texturedModel, new Vector3f(i*distance, 0, j*distance), 0, Maths.randFloat(0.0f, 360.0f), 0, 2.5f));
				}
			}
		}
		return entities;
	}

}
