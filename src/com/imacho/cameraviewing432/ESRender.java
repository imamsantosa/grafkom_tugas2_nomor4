package com.imacho.cameraviewing432;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;

/**
 * Created by Imacho on 4/12/2015.
 */

public class ESRender implements Renderer {

	private PrimitivesObject primitivesobject; // the primitive object to be
												// drawn
	private PrimitivesPolarObject primitivespolarobject;
	private TransObject transobject;
	private ObjectDraw objectdraw;
	private PrimitivesObject myobject;
	private TransPiramidaObject piramida;
	private MyCam mycam;
	private ObjectController controller;

	private float x = 0.f;
	private float y = 0.0f;
	private float z = 0.0f;
	private float direction = 1.0f;
	private float maximumHeight = 2.0f;
	private float radius = 0.6f;

	private float mataX = 0.0f;
	private float mataY = 0.0f;
	private float mataZ = 5.0f;

	private float theta = 0.0f;
	private float y_camera = 0.0f;
	private float dTheta = 0.04f;
	private float dy_camera = 0.2f;
	int height;
	int width;

	private float mAngle;
	int RunMode = 1;
	float CurrentAngle = 0.0f; // Angle in degrees
	float AnimateStep = 5.0f; // Rotation step per update

	float aspect = 0;

	boolean forward = false, backward = false, f_up = false, f_down = false,
			left = false, right = false, open = false;
	boolean rotateleft = false, rotateright = false, rotateRolldepan = false,
			rotateRollbelakang = false, rotateRollkanan = false,
			rotateRollkiri = false;

	/** Constructor to set the handed over context */
	public ESRender() {
		this.objectdraw = new ObjectDraw();
		this.primitivesobject = new PrimitivesObject();
		this.primitivespolarobject = new PrimitivesPolarObject();
		this.myobject = new PrimitivesObject();
		this.piramida = new TransPiramidaObject();
		this.transobject = new TransObject();
		this.controller = new ObjectController();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -0.5f, 0.0f);
		kontrol(gl);	
	gl.glPopMatrix();
		
		gl.glPushMatrix();
//			gl.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
			gl.glPushMatrix();
				// menampilkan segi empat dengan gradasi warna
				gl.glPushMatrix();
				gl.glTranslatef(0.0f, -2.5f, -5.0f);
				gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
				gl.glTranslatef(0.0f, 1.5f, 0.0f);
				gl.glScalef(10.0f, 10.0f, 10.0f);
				primitivesobject.draw_kotak(gl);
			gl.glPopMatrix();
			
			// menampilkan kubus dengan polygon
			gl.glPushMatrix();
				gl.glTranslatef(2.5f, -0.1f, 0.0f);
				// Rotate the object
				gl.glTranslatef(0.0f, 0.0f, -5.0f);
				// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f);
				gl.glTranslatef(-0.5f, -0.5f, -0.5f);
				transobject.draw_kubus(gl);
			gl.glPopMatrix();
						
			// menampilkan piramida
			gl.glPushMatrix();
				// Rotate the object
				gl.glTranslatef(0.0f, 0.0f, -5.0f);
				gl.glScalef(1.5f, 1.5f, 1.5f); // Scale down
				piramida.draw(gl);
			gl.glPopMatrix();
	
			
		gl.glPopMatrix();
		


	}

	/**
	 * to make control button object
	 * 
	 * @param gl
	 */
	private void kontrol(GL10 gl) {
		// panah kiri
		gl.glPushMatrix();
		gl.glTranslatef(2.0f, -0.6f, -5.0f);
		gl.glRotatef(90, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.panah_kontrol(gl);
		gl.glPopMatrix();

		// panah bawah
		gl.glPushMatrix();
		gl.glTranslatef(2.4f, -0.95f, -5.0f);
		gl.glRotatef(180, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.panah_kontrol(gl);
		gl.glPopMatrix();

		// panah kanan
		gl.glPushMatrix();
		gl.glTranslatef(2.8f, -0.6f, -5.0f);
		gl.glRotatef(-90, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.panah_kontrol(gl);
		gl.glPopMatrix();

		// panah atas
		gl.glPushMatrix();
		gl.glTranslatef(2.4f, -0.3f, -5.0f);
//		gl.glRotatef(270, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.panah_kontrol(gl);
		gl.glPopMatrix();
		
		// lingkaran kiri
		gl.glPushMatrix();
		gl.glTranslatef(-2.0f, -0.6f, -5.0f);
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glRotatef(90, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.lingkaran_kontrol_putih(gl);
		gl.glPopMatrix();

		// lingkaran bawah
		gl.glPushMatrix();
		gl.glTranslatef(-2.4f, -0.95f, -5.0f);
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glRotatef(180, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.lingkaran_kontrol_kuning(gl);
		gl.glPopMatrix();

		// lingkaran kanan
		gl.glPushMatrix();
		gl.glTranslatef(-2.8f, -0.6f, -5.0f);
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glRotatef(-90, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.lingkaran_kontrol_biru(gl);
		gl.glPopMatrix();

		// lingkaran atas
		gl.glPushMatrix();
		gl.glTranslatef(-2.4f, -0.3f, -5.0f);
		gl.glScalef(0.2f, 0.2f, 0.2f);
//				gl.glRotatef(270, 0.0f, 0.0f, 1.0f);
		// gl.glScalef(3.0f, 3.0f, 3.0f);
		controller.lingkaran_kontrol_merah(gl);
		gl.glPopMatrix();

		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set color's clear-value to
													// black
		gl.glClearDepthf(1.0f); // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables depth-buffer for hidden
		// surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL); // The type of depth testing to do
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); // nice
		// perspective
		// view
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
		// performance

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0)
			height = 1; // To prevent divide by zero

		this.width = width;
		this.height = height;

		aspect = (float) width / height;

		// Set the viewport (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix

		// Use perspective projection
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);
		// gl.glOrthof(-3.0f, 3.0f, -3.0f, 3.0f, -3.0f, 3.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset
	}

	public float getAngle() {
		return mAngle;
	}

	public void setAngle(float angle) {
		mAngle = angle;
	}

	public float getmataX() {
		return mataX;
	}

	public void setmataX(double mataX_) {
		mataX = (float) mataX_;
	}

	public float getmataY() {
		return mataY;
	}

	public void setmataY(float mataY_) {
		mataY = mataY_;
	}

	public float getmataZ() {
		return mataZ;
	}

	public void setmataZ(double mataZ_) {
		mataZ = (float) mataZ_;
	}

	public float gettheta() {
		return theta;
	}

	public void settheta(double theta_) {
		theta = (float) theta_;
	}

	public float gety_camera() {
		return y_camera;
	}

	public void sety_camera(double y_camera_) {
		y_camera = (float) y_camera_;
	}

	public float getdTheta() {
		return dTheta;
	}

	public void setdTheta(double dTheta_) {
		dTheta = (float) dTheta_;
	}

	public float getdy_camera() {
		return dy_camera;
	}

	public void setdy_camera(double dy_camera_) {
		dy_camera = (float) dy_camera_;
	}

	public void update_pantulan() {
		y += direction * 0.05;
		if (y > maximumHeight) {
			y = maximumHeight;
			direction = -1;

		} else if (y < radius) {
			y = radius;
			direction = 1;
		}

	}

	public boolean getforward() {
		return forward;
	}

	public void setforward(boolean forward_) {
		forward = forward_;
	}

	public boolean getbackward() {
		return backward;
	}

	public void setbackward(boolean backward_) {
		backward = backward_;
	}

	public boolean getleft() {
		return left;
	}

	public void setleft(boolean left_) {
		left = left_;
	}

	public boolean getright() {
		return right;
	}

	public void setright(boolean right_) {
		right = right_;
	}

	public boolean getf_up() {
		return f_up;
	}

	public void setf_up(boolean f_up_) {
		f_up = f_up_;
	}

	public boolean getf_down() {
		return f_down;
	}

	public void setf_down(boolean f_down_) {
		f_down = f_down_;
	}

	public boolean getrotateleft() {
		return rotateleft;
	}

	public void setrotateleft(boolean rotateleft_) {
		rotateleft = rotateleft_;
	}

	public boolean getrotateright() {
		return rotateright;
	}

	public void setrotateright(boolean rotateright_) {
		rotateright = rotateright_;
	}

	public boolean getrotateRolldepan() {
		return rotateRolldepan;
	}

	public void setrotateRolldepan(boolean rotateRolldepan_) {
		rotateRolldepan = rotateRolldepan_;
	}

	public boolean getrotateRollbelakang() {
		return rotateRollbelakang;
	}

	public void setrotateRollbelakang(boolean rotateRollbelakang_) {
		rotateRollbelakang = rotateRollbelakang_;
	}

	public boolean getrotateRollkanan() {
		return rotateRollkanan;
	}

	public void setrotateRollkanan(boolean rotateRollkanan_) {
		rotateRollkanan = rotateRollkanan_;
	}

	public boolean getrotateRollkiri() {
		return rotateRollkiri;
	}

	public void setrotateRollkiri(boolean rotateRollkiri_) {
		rotateRollkiri = rotateRollkiri_;
	}
}