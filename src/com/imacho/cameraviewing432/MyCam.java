package com.imacho.cameraviewing432;

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;

/**
 * Created by Imacho on 4/12/2015.
 */

public class MyCam {

	private float x;
	private float y;
	private float z;
	private float rx;
	private float ry;
	private float rz;

	private float fov;
	private float aspect;
	private float near;
	private float far;

	public MyCam(GL10 gl, float fov, float aspect, float near, float far) {
		x = 0;
		y = 0;
		z = 0;
		rx = 0;
		ry = 0;
		rz = 0;

		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;
		initProjection(gl);
	}

	private void initProjection(GL10 gl) {
		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix

		// Use perspective projection
		// GLU.gluPerspective(gl, 60, aspect, 0.1f, 100.f);
		GLU.gluPerspective(gl, fov, aspect, near, far);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix

		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable texture (NEW)
	}

	public void useView(GL10 gl) {
		gl.glRotatef(rx, 1.f, 0.f, 0.f);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		gl.glTranslatef(x, y, z);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getRX() {
		return rx;
	}

	public float getRY() {
		return ry;
	}

	public float getRZ() {
		return rz;
	}

	public void setRX(float rx) {
		this.rx = rx;
	}

	public void setRY(float ry) {
		this.ry = ry;
	}

	public void setRZ(float rz) {
		this.rz = rz;
	}

	public void moveZ(float amt) {
		z += amt * Math.sin(Math.toRadians(ry + 90));// *
														// Math.sin(Math.toRadians(rx
														// + 90));
		x += amt * Math.cos(Math.toRadians(ry + 90));
		// y += amt * Math.sin(Math.toRadians(rx));
	}

	public void moveX(float amt) {
		z += amt * Math.sin(Math.toRadians(ry));
		x += amt * Math.cos(Math.toRadians(ry));
	}

	public void moveY(float amt) {
		y += amt * Math.sin(Math.toRadians(rz));
		y += amt * Math.cos(Math.toRadians(rx));
	}

	public void rotateY(float amt) {
		ry += amt;
	}

	public void rotateX(float amt) {
		rx += amt;
	}

	public void rotateZ(float amt) {
		rz += amt;
	}
}
