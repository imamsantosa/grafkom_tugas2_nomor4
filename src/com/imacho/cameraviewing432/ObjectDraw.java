package com.imacho.cameraviewing432;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

/**
 * Created by Imacho on 4/12/2015.
 */

public class ObjectDraw {
	
	//Circle
	private float vertices_circle[] = null;
	private float vertices_circle_color[] = null;
	private float batas_sudut_lower = 0.0f, batas_sudut_upper = 360.0f;
	float jari_jari;
	float a, b;
	float x, y;
	private int loop, loop_color;
	float step;
	int byk_vertices, byk_vertices1; // menyatakan banyaknya titik yang
										// di-generate
	int realloop;
	private float getApproxValue(float f) {
		if (Math.abs(f) < 0.001) {
			return 0;
		}
		return f;
	}

	// number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
	private float vertices_kotak[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
             0.5f, -0.5f, 0.0f,   // bottom right
             0.5f,  0.5f, 0.0f }; // top right
	
	private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
	
	// number of coordinates per vertex in this array
    static float vertices_segitiga[] = {
            // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f,// top
           -0.5f, -0.311004243f, 0.0f,// bottom left
            0.5f, -0.311004243f, 0.0f // bottom right
    };
    
	private float vertices_color[] = {
			1.0f, 0.0f,  0.0f, 1.0f,	// CV1 - first color (red,green,blue)
			0.0f, 1.0f,  0.0f, 1.0f,	// CV2
			0.0f, 0.0f,  1.0f, 1.0f,	// CV3
			0.0f, 1.0f, 0.0f,  1.0f,	// CV4
			1.0f, 0.0f,  0.0f, 1.0f		// CV5	 
	};
	
	public ObjectDraw() {
		// ============ start to generate vertices to circle
		// ==========================
		// Inisialisasi
		jari_jari = 1.0f;

		// Titik Pusat
		a = 0.0f;
		b = 0.0f;
		x = a + jari_jari;
		y = b;
		// step = 1.0f;
		step = 3.0f;
		byk_vertices1 = (int) (((batas_sudut_upper - batas_sudut_lower) + step) / step);
		// byk_vertices=(((int)
		// Math.floor((batas_sudut_upper-batas_sudut_lower)/ step))+1); //
		// 4.7/0.1 = 46.9999 ??
		byk_vertices = (((int) Math
				.round((batas_sudut_upper - batas_sudut_lower) / step)) + 1);

		loop = 0;
		loop_color = 0;
		realloop = 0;
		// menambah nilai +1 untuk byk_vertices untuk menghindari pemesanan
		// memori yang kurang
		vertices_circle = new float[(byk_vertices + 1) * 3];
		vertices_circle_color = new float[(byk_vertices + 1) * 4];

		// dengan menggunakan while
		/*
		 * float teta = batas_sudut_lower; while(teta <=
		 * batas_sudut_upper+step){ // +step untuk batas_sudut_upper, untuk
		 * mengatasi looping yang kurang pada while dikarenakan bug pada nilai
		 * update tetanya karena float dan double android studio tidak stabil
		 * vertices_circle[loop] = (float) ((x - a)
		 * getApproxValue((float)Math.cos(Math.toRadians(teta))) - ((y - b) *
		 * getApproxValue((float)Math.sin(Math.toRadians(teta)))) + a);
		 * vertices_circle[loop + 1] = (float) ((x - a)
		 * getApproxValue((float)Math.sin(Math.toRadians(teta))) - ((y - b) *
		 * getApproxValue((float)Math.cos(Math.toRadians(teta)))) + b);
		 * vertices_circle[loop + 2] = 0; loop += 3; realloop=realloop+1;
		 * 
		 * // mengenerate warna untuk setiap vertex
		 * vertices_circle_color[loop_color] = (float) ((x - a)
		 * Math.cos(Math.toRadians(teta)) - ((y - b) *
		 * Math.sin(Math.toRadians(teta))) + a);
		 * vertices_circle_color[loop_color + 1] = (float) ((x - a)
		 * Math.sin(Math.toRadians(teta)) - ((y - b) *
		 * Math.cos(Math.toRadians(teta))) + b);
		 * vertices_circle_color[loop_color + 2] = 0.5f;
		 * vertices_circle_color[loop_color + 3] = 0.5f; loop_color += 4;
		 * 
		 * teta += step;
		 * 
		 * }
		 */

		// dengan for
		for (float teta = batas_sudut_lower; teta <= batas_sudut_upper + step; teta += step) {
			vertices_circle[loop] = (float) ((x - a)
					* getApproxValue((float) Math.cos(Math.toRadians(teta)))
					- ((y - b) * getApproxValue((float) Math.sin(Math
							.toRadians(teta)))) + a);
			vertices_circle[loop + 1] = (float) ((x - a)
					* getApproxValue((float) Math.sin(Math.toRadians(teta)))
					- ((y - b) * getApproxValue((float) Math.cos(Math
							.toRadians(teta)))) + b);
			vertices_circle[loop + 2] = 0;
			loop += 3;
			realloop = realloop + 1;

			// mengenerate warna untuk setiap vertex
			vertices_circle_color[loop_color] = (float) ((x - a)
					* Math.cos(Math.toRadians(teta))
					- ((y - b) * Math.sin(Math.toRadians(teta))) + a);
			vertices_circle_color[loop_color + 1] = (float) ((x - a)
					* Math.sin(Math.toRadians(teta))
					- ((y - b) * Math.cos(Math.toRadians(teta))) + b);
			vertices_circle_color[loop_color + 2] = 0.5f;
			vertices_circle_color[loop_color + 3] = 0.5f;
			loop_color += 4;
		}

		// ============= end for generate vertices to circle
		// ====================
	}
	
	// Point to our vertex buffer, return buffer holding the vertices
	public static FloatBuffer makeFloatBuffer(float[] arr){
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
	}

	// Setup index-array buffer. Indices in byte.
	public static ByteBuffer makeByteBuffer(byte[] arr){
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
	    bb.put(arr);
	    bb.position(0);
	    return bb;
	}
	
	// initialize byte buffer for the draw list
	public static  ShortBuffer makeShortBuffer(short[] arr){
		ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
				arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer db = dlb.asShortBuffer();
        db.put(arr);
        db.position(0);
        return db;
	}
	
	/** The draw method for the primitive object with the GL context */	
	
	public void draw_kotak(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
        // draw the shape
        gl.glVertexPointer( // point to vertex data:
                COORDS_PER_VERTEX,
                GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_kotak));
        
        gl.glDrawElements(  // draw shape:
                GL10.GL_TRIANGLES,
                drawOrder.length, GL10.GL_UNSIGNED_SHORT,
                makeShortBuffer(drawOrder));
        
        /*gl.glDrawElements(  // draw shape:
                GL10.GL_LINE_LOOP,
                drawOrder.length, GL10.GL_UNSIGNED_SHORT,
                makeShortBuffer(drawOrder));*/

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void draw_kotak1(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glColor4f(1.0f,1.0f,1.0f,1.0f);
        // draw the shape
        gl.glVertexPointer( // point to vertex data:
                COORDS_PER_VERTEX,
                GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_kotak));
        
        gl.glDrawElements(  // draw shape:
                GL10.GL_TRIANGLES,
                drawOrder.length, GL10.GL_UNSIGNED_SHORT,
                makeShortBuffer(drawOrder));
        
        /*gl.glDrawElements(  // draw shape:
                GL10.GL_LINE_LOOP,
                drawOrder.length, GL10.GL_UNSIGNED_SHORT,
                makeShortBuffer(drawOrder));*/

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void draw_segitiga(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    
        gl.glColor4f(1.0f,1.0f,1.0f,1.0f);
        
        // draw the shape
        gl.glVertexPointer( // point to vertex data:
                COORDS_PER_VERTEX,
                GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_segitiga));
        
//        gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
        
        //gl.glDrawArrays(    // draw shape:
        //        GL10.GL_TRIANGLES, 0,
        //        vertices_segitiga.length / COORDS_PER_VERTEX);
        gl.glDrawArrays(    // draw shape:
                GL10.GL_TRIANGLE_FAN, 0,
                vertices_segitiga.length / COORDS_PER_VERTEX);

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	public void draw_circle_one_color(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // set the colour for the object circle
        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        gl.glLineWidth(4.0f);
        // create VBO from buffer with glBufferData()
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,makeFloatBuffer(vertices_circle));

        // draw circle contours
        //gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, byk_vertices);
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, byk_vertices);

        // Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
	
	public void draw_circle_one_color2(GL10 gl, float r_in, float g_in, float b_in) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // set the colour for the object circle
        gl.glColor4f(r_in, g_in, b_in, 1.0f);

        // create VBO from buffer with glBufferData()
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                makeFloatBuffer(vertices_circle));

        // draw circle contours
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, byk_vertices);

        // Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
	
	public void draw_ractangle(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // set the colour for the object circle
        gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);
        gl.glLineWidth(4.0f);
        
        // create VBO from buffer with glBufferData()
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,makeFloatBuffer(vertices_circle));

        // draw circle contours
        //gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, byk_vertices);
        gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, byk_vertices);

        // Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
