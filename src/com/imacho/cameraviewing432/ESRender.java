package com.imacho.cameraviewing432;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class ESRender implements Renderer {

	private PrimitivesObject primitivesobject; // the primitive object to be drawn
	private PrimitivesPolarObject primitivespolarobject;
	private TransObject transobject;
	private ObjectDraw objectdraw;
	private PrimitivesObject myobject;
	private TransPiramidaObject piramida;
	private MyCam mycam;
	Context context;
	private ESText glText;
	int mywidth = 0;
	int myheight = 0;
	boolean get_size_screen = true;
	private float x_lebar_layar = 0;
	private float y_tinggi_layar = 0;
	private float translateObject;

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
	
	float sb_x = 0.0f;
	float sb_y = 0.0f;
	
	float r_x = 0.0f;
	float r_z = 0.0f;
	
	float mv_x = 0.0f;
	float mv_y = 0.0f;
	
	float touching_x;
	float touching_y;
	
	private float mAngle;
	int RunMode = 1;
	float CurrentAngle = 0.0f; // Angle in degrees
	float AnimateStep = 5.0f; // Rotation step per update

	float aspect = 0;

	boolean forward = false, backward = false, f_up = false, f_down = false, left = false, right = false, open = false;
	boolean rotateleft = false, rotateright = false, rotateRolldepan = false, rotateRollbelakang = false, rotateRollkanan = false, rotateRollkiri = false;

	/** Constructor to set the handed over context */
	public ESRender(Context context) {
		this.objectdraw = new ObjectDraw();
		this.primitivesobject = new PrimitivesObject();
		this.primitivespolarobject = new PrimitivesPolarObject();
		this.myobject = new PrimitivesObject();
		this.piramida = new TransPiramidaObject();
		this.transobject = new TransObject();
		this.context = context;
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		this.mycam = new MyCam(gl, 60.f, aspect, 1.0f, 100.f);
		
		if (get_size_screen) {
			this.x_lebar_layar = mywidth;
			this.y_tinggi_layar = myheight;
			get_size_screen = false;
		}
		
//		if (forward) { // bergerak ke depan (tekan tombol keyboard case KeyEvent.KEYCODE_W )
//			mycam.moveZ(0.5f); // mycam.moveZ(0.02f);
//			forward = false;
//		}
//		if (backward) { // bergerak ke belakang (tekan tombol keyboard case KeyEvent.KEYCODE_S )
//			mycam.moveZ(-0.5f); // mycam.moveZ(-0.02f);
//			backward = false;
//		}
//		if (f_up) { // keatas (tekan tombol keyboard case KeyEvent.KEYCODE_C )
//			mycam.moveY(0.5f); // mycam.moveY(0.02f);
//			f_up = false;
//		}
//		if (f_down) { // kebawah (tekan tombol keyboard case KeyEvent.KEYCODE_V)
//			mycam.moveY(-0.5f); // mycam.moveY(-0.02f);
//			f_down = false;
//		}
//		if (left) { // ke kiri (tekan tombol keyboard case KeyEvent.KEYCODE_A )
//			mycam.moveX(0.5f); // mycam.moveX(0.02f);
//			left = false;
//		}
//		if (right) { // ke kanan (tekan tombol keyboard case KeyEvent.KEYCODE_D)
//			mycam.moveX(-0.5f); // mycam.moveX(-0.02f);
//			right = false;
//		}
//		if (rotateleft) { // rotate terhadap sumbu Y ke kiri (tekan tombol keyboard case KeyEvent.KEYCODE_DPAD_LEFT)
//			mycam.rotateY(-3.0f); // mycam.rotateY(-0.1f);
//			rotateleft = false;
//		}
//		if (rotateright) { // rotate terhadap sumbu Y ke kanan (tekan tombol keyboard case KeyEvent.KEYCODE_DPAD_RIGHT)
//			mycam.rotateY(3.0f); // mycam.rotateY(0.1f);
//			rotateright = false;
//		}
//
//		if (rotateRolldepan) { // rotate terhadap sumbu X ke depan (tekan tombol keyboard case KeyEvent.KEYCODE_DPAD_UP)
//			mycam.rotateX(-3.0f); // mycam.rotateY(-0.1f);
//			rotateRolldepan = false;
//		}
//		if (rotateRollbelakang) { // rotate terhadap sumbu X ke belakang (tekan tombol keyboard case KeyEvent.KEYCODE_DPAD_DOWN)
//			mycam.rotateX(3.0f); // mycam.rotateY(0.1f);
//			rotateRollbelakang = false;
//		}
//
//		if (rotateRollkanan) { // rotate terhadap sumbu Z ke depan (tekan tombol keyboard case KeyEvent.KEYCODE_Q)
//			mycam.rotateZ(-3.0f); // mycam.rotateY(-0.1f);
//			rotateRollkanan = false;
//		}
//		if (rotateRollkiri) { // rotate terhadap sumbu Z ke belakang (tekan tombol keyboard case KeyEvent.KEYCODE_E)
//			mycam.rotateZ(3.0f); // mycam.rotateY(0.1f);
//			rotateRollkiri = false;
//		}

		// Draw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// gl.glLoadIdentity(); // Reset

		// GLU.gluLookAt(gl, mataX, mataY, mataZ, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
		// 0.0f);

		mycam.useView(gl);
		
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glTranslatef(mv_x, mv_y, 0.0f);
		gl.glRotatef(r_z, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(r_x, 1.0f, 0.0f, 0.0f);
		object(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		tombol(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		tombol_text(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, sb_y, 0.0f);
		gl.glTranslatef(-1.0f, 0.6f, 1.0f);
		tulisan(gl);
		gl.glPopMatrix();
		
		// Update the rotational angle after each refresh
		if (RunMode == 1) {
			// re-Calculate animation parameters
			CurrentAngle += AnimateStep;
			if (CurrentAngle > 360.0) {
				// CurrentAngle -= 360.0*Math.floor(CurrentAngle/360.0);
				CurrentAngle = 0.0f;
				CurrentAngle += AnimateStep;
			}
		}

	}
	
	public void text_naik(){
		sb_y += 0.05f;
	}
	
	public void text_turun(){
		sb_y -= 0.05f;
	}
	private float angle_rotasi = 0.8f;
	
	public void rotasi_depan(){
		r_x += angle_rotasi;
	}
	
	public void rotasi_belakang(){
		r_x -= angle_rotasi;
	}
	
	public void rotasi_kanan(){
		r_z -= angle_rotasi;
	}
	
	public void rotasi_kiri(){
		r_z += angle_rotasi;
	}
	
	private float motion = 0.05f;
	public void move_atas(){
		mv_y += motion;
	}
	
	public void move_bawah(){
		mv_y -= motion;
	}
	
	public void move_kanan(){
		mv_x += motion;
	}
	
	public void move_kiri(){
		mv_x -= motion;
	}
	
	private void object(GL10 gl){
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, -2.5f, -5.0f);
		gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 1.5f, 0.0f);
		gl.glScalef(10.0f, 10.0f, 10.0f);
		primitivesobject.draw_kotak(gl);
		gl.glPopMatrix();

		// menampilkan piramida
		gl.glPushMatrix();
		gl.glTranslatef(0.0f, translateObject, 0.0f);
		// Rotate the object
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glScalef(1.5f, 1.5f, 1.5f); // Scale down
		piramida.draw(gl);
		gl.glPopMatrix();

		// menampilkan kubus dengan polygon
		gl.glPushMatrix();
		gl.glTranslatef(2.5f, -0.1f, 0.0f);

		// Rotate the object
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		//gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f);
		gl.glTranslatef(-0.5f, -0.5f, -0.5f);
		transobject.draw_kubus(gl);
		gl.glPopMatrix();
	}
	
	private void tombol_text(GL10 gl){
		gl.glPushMatrix();
		gl.glTranslatef(2.5f, 2.3f, -5.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		objectdraw.draw_kotak(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(2.5f, 1.8f, -5.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		objectdraw.draw_kotak1(gl);
		gl.glPopMatrix();
	}
	
	private void tombol(GL10 gl){
		// Membuat Tombol
				// display drawing circle with one color (lingkaran besar)
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-3.0f, -1.0f, -4.0f);
				gl.glScalef(0.75f, 0.75f, 0.75f);
				objectdraw.draw_circle_one_color(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				//Lingkar Merah
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-3.0f, -0.35f, -4.0f);
				gl.glScalef(0.25f, 0.25f, 0.25f);
				objectdraw.draw_circle_one_color2(gl, 1.0f, 0.0f, 0.0f);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				//Lingkar Kuning
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-3.0f, -1.59f, -4.0f);
				gl.glScalef(0.25f, 0.25f, 0.25f);
				objectdraw.draw_circle_one_color2(gl, 1.0f, 1.0f, 0.0f);
				gl.glPopMatrix(); // end freeze state/event to each object

				//Lingkar Putih
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-3.65f, -0.98f, -4.0f);
				gl.glScalef(0.25f, 0.25f, 0.25f);
				objectdraw.draw_circle_one_color2(gl, 1.0f, 1.0f, 1.0f);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				//Lingkar Putih
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-2.35f, -0.98f, -4.0f);
				gl.glScalef(0.25f, 0.25f, 0.25f);
				objectdraw.draw_circle_one_color2(gl, 0.0f, 0.0f, 1.0f);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				//Lingkar Biru
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(-3.65f, -0.98f, -4.0f);
				gl.glScalef(0.25f, 0.25f, 0.25f);
				objectdraw.draw_circle_one_color2(gl, 1.0f, 1.0f, 1.0f);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				// display drawing circle with one color (lingkaran besar)
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(3.0f, -1.0f, -4.0f);
				gl.glScalef(0.75f, 0.75f, 0.75f);
				objectdraw.draw_circle_one_color(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				// Tombol Atas
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(3.0f, -0.36f, -4.0f);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				objectdraw.draw_segitiga(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				// Tombol Bawah
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(3.0f, -1.59f, -4.0f);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				gl.glRotatef(180, 0.0f, 0.0f, 1.0f);
				objectdraw.draw_segitiga(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				// Tombol Kanan
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(3.66f, -0.98f, -4.0f);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				gl.glRotatef(-90, 0.0f, 0.0f, 1.0f);
				objectdraw.draw_segitiga(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
				
				// Tombol Kiri
				gl.glPushMatrix(); // start freeze state/event to each object
				gl.glTranslatef(2.33f, -0.98f, -4.0f);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				gl.glRotatef(90, 0.0f, 0.0f, 1.0f);
				objectdraw.draw_segitiga(gl);
				gl.glPopMatrix(); // end freeze state/event to each object
	}
	
	private void tulisan(GL10 gl){
//		gl.glDisable(GL10.GL_LIGHTING);
//		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping
		gl.glEnable(GL10.GL_BLEND); // Enable Alpha Blend
		gl.glDisable(GL10.GL_DEPTH_TEST); // Turn depth testing off (NEW)
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); // Set
																		// Alpha
																		// Blend
																		// Function

		glText.begin(0.0f, 1.0f, 0.0f, 1.0f); // Begin Text Rendering (Set Color
												// // WHITE)
		gl.glTranslatef(-2.0f, 1.0f, -5.0f);
		gl.glScalef(0.002f, 0.002f, 0.002f);
		glText.draw("Moch Wahyu Imam Santosa :-) x="+touching_x+" y= "+touching_y, 0, 0); // Draw
		glText.end(); // End Text Rendering
//		gl.glEnable(GL10.GL_LIGHTING);
//
//		// disable texture + alpha
		gl.glDisable(GL10.GL_BLEND); // Disable Alpha Blend
//		gl.glDisable(GL10.GL_TEXTURE_2D); // Disable Texture Mapping
	}

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
		
//		gl.glEnable(GL10.GL_LIGHTING);
		// gl.glEnable(GL10.GL_LIGHT0);

		// Create the GLText
		glText = new ESText(gl, context.getAssets());

		// Load the font from file (set size + padding), creates the texture
		// NOTE: after a successful call to this the font is ready for
		// rendering!
		glText.load("Roboto-Regular.ttf", 100, 10, 10); // Create Font (Height:
														// 14
		// Pixels / X+Y Padding
		// 2 Pixels)

		// Setup Blending (NEW)
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full brightness, 50% alpha
												// (NEW)
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Select blending
														// function (NEW)
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable texture (NEW)

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0)
			height = 1; // To prevent divide by zero
		mywidth = width;
		myheight = height;
		aspect = (float) width / height;
		// Set the viewport (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);
		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
		gl.glLoadIdentity(); // Reset projection matrix
		// Use perspective projection
		GLU.gluPerspective(gl, 70, aspect, 0.1f, 100.f);
		// gl.glOrthof(-3.0f, 3.0f, -3.0f, 3.0f, -3.0f, 3.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset
	}

	public int getMywidth() {
		return mywidth;
	}

	public void setMywidth(int mywidth) {
		this.mywidth = mywidth;
	}

	public int getMyheight() {
		return myheight;
	}

	public void setMyheight(int myheight) {
		this.myheight = myheight;
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
	public void setTranslateObject(float x){
		this.translateObject = x;
	}
	public float getTranslateObject(){
		return this.translateObject;
	}
}