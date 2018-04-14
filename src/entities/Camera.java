package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private static final float MOUSE_SENSIBILITY = 3f;
	private static final float FAKE_DELTA_TIME = (float) DisplayManager.getFps() / 1000;
	
	private Vector3f position = new Vector3f(100, 100, 0);
	private float pitch;    // rotation over x
	private float yaw;      // rotation over y
	private float roll;     // rotation over z
	
	public Camera() {}
	
	public Camera(float x, float y, float z, float pitch, float yaw, float roll) {
		this.position.setX(x);
		this.position.setY(y);
		this.position.setZ(z);
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}
	
	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += 1f;
		}if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.y -= 1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw += 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw -= 1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch += 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch -= 1f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			roll -= 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			roll += 1f;
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
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public String getCameraCurrentState() {
		Vector3f currPos = getPosition();
		return "(" +
			   String.valueOf(currPos.x) + ", " +
			   String.valueOf(currPos.y) + ", " +
			   String.valueOf(currPos.z) + "), (" +
			   String.valueOf(getPitch()) + ", " +
			   String.valueOf(getYaw()) + ", " +
			   String.valueOf(getRoll()) + ")";
	}
	
}
