package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private static final float MOUSE_SENSIBILITY = 3f;
	private static final float FAKE_DELTA_TIME = (float) DisplayManager.getFps() / 1000;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;    // rotation over x
	private float yaw;      // rotation over y
	private float roll;     // rotation over z
	
	public Camera() {}
	
	public void move() {
		
		int DWheelCurrentValue = Mouse.getDWheel();
		int DXMouseCurrentValue = Mouse.getDX();
		int DYMouseCurrentValue = Mouse.getDY();
		
		// Directing camera
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S )) {
			position.z+=0.02f;
		}
		
		// DWheel functionality
		if(DWheelCurrentValue > 0) {
			if(DWheelCurrentValue == 120) {
				position.z-=3f;
			} else if(DWheelCurrentValue == 240) {
				position.z-=6f;
			} else if(DWheelCurrentValue == 360) {
				position.z-=9f;
			}
		}
		if(DWheelCurrentValue < 0) {
			if(DWheelCurrentValue == -120) {
				position.z+=3f;
			} else if(DWheelCurrentValue == -240) {
				position.z+=6f;
			} else if(DWheelCurrentValue == -360) {
				position.z+=9f;
			}
		}
		
		// Camera pitch and yaw
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch-=1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch+=1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw-=1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw+=1f;
		}
		if(DXMouseCurrentValue != 0) {
			yaw-=DXMouseCurrentValue * MOUSE_SENSIBILITY * FAKE_DELTA_TIME;
		}
		if(DYMouseCurrentValue != 0) {
			pitch-=DYMouseCurrentValue * MOUSE_SENSIBILITY * FAKE_DELTA_TIME;
		}
		
		// Moving camera up and down of Y axis
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y+=0.02f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.y-=0.02f;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
