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
	
}
