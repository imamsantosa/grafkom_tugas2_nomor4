package com.imacho.cameraviewing432;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Imacho on 4/12/2015.
 */

/**
 * A view container where OpenGL ES graphics can be drawn on screen. This view
 * can also be used to capture touch events, such as a user interacting with
 * drawn objects.
 */
public class ESSurfaceView extends GLSurfaceView {

	private final ESRender esRender;
	private boolean _dragging = false;
	
	public ESSurfaceView(Context context) {
		super(context);

		// Set the Renderer for drawing on the GLSurfaceView
		esRender = new ESRender(context);
//		esRender = new ESRender();
		setRenderer(esRender);

		// To enable keypad
		this.setFocusable(true);
		this.requestFocus();

		// To enable touch mode
		this.setFocusableInTouchMode(true);

		// Render the view only when there is a change in the drawing data
		// merender hanya ketika ada perubahan/ event
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	private float mPreviousX;
	private float mPreviousY;

	@Override
	public boolean onTouchEvent(MotionEvent v) {
		// MotionEvent reports input details from the touch screen
		// and other input controls. In this case, we are only
		// interested in events where the touch position changed.

		float x = v.getX();
		float y = v.getY();
		kontrol(v);

		switch (v.getAction()) {

		case MotionEvent.ACTION_DOWN:
			Log.v("Test Action Down", "action down working");
			break;
		// case MotionEvent.ACTION_POINTER_UP:
		// Log.v("Test Action ACTION_POINTER_UP", "action working");
		// requestRender();
		// case MotionEvent.ACTION_MOVE:
		// Log.v("Test Action ACTION_POINTER_DOWN", "action working");
		// requestRender();
		// case MotionEvent.ACTION_UP:
		// Log.v("Test Action Up", "action up working");
		// requestRender();
		case MotionEvent.ACTION_MOVE:

			float dx = (x - mPreviousX);
			float dy = (y - mPreviousY);
			mPreviousX = x;
			mPreviousY = y;

			esRender.setmataX(10 * Math.cos(esRender.gettheta()));
			esRender.setmataY(esRender.gety_camera());
			esRender.setmataZ(10 * Math.sin(esRender.gettheta()));

			// right
			if (dx > 0.0f) {
				// theta += dTheta;
				esRender.settheta(esRender.gettheta() - esRender.getdTheta());
			}

			// left
			if (dx < 0.0f) {
				// theta -= dTheta;
				esRender.settheta(esRender.gettheta() + esRender.getdTheta());
			}

			// up
			if (dy > 0.0f) {
				if (esRender.gety_camera() > esRender.getdy_camera()) {
					// y -= dy;
					esRender.sety_camera(esRender.gety_camera()
							- esRender.getdy_camera());
				}
			}

			// down
			if (dy < 0.0f) {
				// y += dy;
				esRender.sety_camera(esRender.gety_camera()
						+ esRender.getdy_camera());

			}

			// tracking
			Log.i("Nilai theta : ", "" + esRender.gettheta());
			Log.i("Nilai y_camera : ", "" + esRender.gety_camera());
			Log.i("Nilai dTheta : ", "" + esRender.getdTheta());
			Log.i("Nilai dx : ", "" + dx);
			Log.i("Nilai dy : ", "" + dy);

			requestRender();
		}

		return true;
	}
	
	public void kontrol(MotionEvent event){
		Point _touchingPoint = new Point(1013, 500);
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_dragging = true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			_dragging = false;
		}
		
		if(_dragging){
			_touchingPoint.x = (int) event.getX();
			_touchingPoint.y = esRender.myheight - (int) event.getY();
			esRender.touching_x = _touchingPoint.x;
			esRender.touching_y = _touchingPoint.y;
			
			if((_touchingPoint.x >= 614 && _touchingPoint.x <= 657) && (_touchingPoint.y >= 410 && _touchingPoint.y <= 450)){
				esRender.text_naik();
			}
			
			if((_touchingPoint.x >= 614 && _touchingPoint.x <= 657) && (_touchingPoint.y >= 367 && _touchingPoint.y <= 410)){
				esRender.text_turun();
			}
			
			//lingkaran
			
			if((_touchingPoint.x >= 90 && _touchingPoint.x <= 142) && (_touchingPoint.y >= 175 && _touchingPoint.y <= 229)){
				esRender.rotasi_depan();
			}
			
			if((_touchingPoint.x >= 90 && _touchingPoint.x <= 142) && (_touchingPoint.y >= 48 && _touchingPoint.y <= 101)){
				esRender.rotasi_belakang();
			}
			
			if((_touchingPoint.x >= 158 && _touchingPoint.x <= 208) && (_touchingPoint.y >= 111 && _touchingPoint.y <= 163)){
				esRender.rotasi_kanan();
			}
			
			if((_touchingPoint.x >= 21 && _touchingPoint.x <= 73) && (_touchingPoint.y >= 111 && _touchingPoint.y <= 163)){
				esRender.rotasi_kiri();
			}
			
			//panah
			if((_touchingPoint.x >= 715 && _touchingPoint.x <= 766) && (_touchingPoint.y >= 184 && _touchingPoint.y <= 233)){
				esRender.move_atas();
			}
			
			if((_touchingPoint.x >= 715 && _touchingPoint.x <= 766) && (_touchingPoint.y >= 41 && _touchingPoint.y <= 89)){
				esRender.move_bawah();
			}
			
			if((_touchingPoint.x >= 793 && _touchingPoint.x <= 837) && (_touchingPoint.y >= 111 && _touchingPoint.y <= 163)){
				esRender.move_kanan();
			}
			
			if((_touchingPoint.x >= 639 && _touchingPoint.x <= 686) && (_touchingPoint.y >= 111 && _touchingPoint.y <= 163)){
				esRender.move_kiri();
			}
			
		} 
	}

	// Key-down event handler
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		float x = getHeight();

		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_RIGHT: // rotate terhadap sumbu Y ke kanan
			Log.v("Test Action KEYCODE_DPAD_RIGHT", "action working");
			esRender.setrotateright(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT: // rotate terhadap sumbu Y ke kiri
			esRender.setrotateleft(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_DPAD_UP: // rotate terhadap sumbu X ke depan
			esRender.setrotateRolldepan(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN: // rotate terhadap sumbu X ke belakang
			esRender.setrotateRollbelakang(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER: // Stop
			break;

		case KeyEvent.KEYCODE_Q: // rotate terhadap sumbu Z ke kanan
			Log.v("Test Action Z", "action working");
			esRender.setrotateRollkanan(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_E: // rotate terhadap sumbu Z ke kiri
			Log.v("Test Action Z", "action working");
			esRender.setrotateRollkiri(true);
			requestRender();
			break;	
		case KeyEvent.KEYCODE_Z: // reset
			Log.v("Test Action Z", "action working");
			requestRender();
			break;
		case KeyEvent.KEYCODE_W: // ke depan
			Log.v("Test Action W", "action working");
			esRender.setforward(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_S: // arah ke belakang
			Log.v("Test Action S", "action working");
			esRender.setbackward(true);
			requestRender();
			break;

		case KeyEvent.KEYCODE_A: // left
			Log.v("Test Action A", "action working");
			esRender.setleft(true);
			requestRender();
			break;

		case KeyEvent.KEYCODE_D: // right
			Log.v("Test Action D", "action working");
			esRender.setright(true);
			requestRender();
			break;

		case KeyEvent.KEYCODE_C: // arah ke atas
			Log.v("Test Action C", "action working");
			esRender.setf_up(true);
			requestRender();
			break;
		case KeyEvent.KEYCODE_V: // arah ke bawah
			Log.v("Test Action V", "action working");
			esRender.setf_down(true);
			requestRender();
			break;
		}
		return true; // Event handled
	}
}
