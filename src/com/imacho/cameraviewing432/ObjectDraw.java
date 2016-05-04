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

	public void draw_segitiga(GL10 gl) {
		// Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    
        gl.glColor4f(0.0f,1.0f,0.0f,1.0f);
        
        // draw the shape
        gl.glVertexPointer( // point to vertex data:
                COORDS_PER_VERTEX,
                GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_segitiga));
        
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
        
        //gl.glDrawArrays(    // draw shape:
        //        GL10.GL_TRIANGLES, 0,
        //        vertices_segitiga.length / COORDS_PER_VERTEX);
        gl.glDrawArrays(    // draw shape:
                GL10.GL_LINE_LOOP, 0,
                vertices_segitiga.length / COORDS_PER_VERTEX);

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
}
