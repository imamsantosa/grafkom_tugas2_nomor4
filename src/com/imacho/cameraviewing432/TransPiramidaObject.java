package com.imacho.cameraviewing432;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Imacho on 4/12/2015.
 */

public class TransPiramidaObject {
	
	private float[] vertices = { // 5 vertices of the pyramid in (x,y,z)
			-1.0f, -1.0f, -1.0f, // 0. left-bottom-back
			1.0f, -1.0f, -1.0f, // 1. right-bottom-back
			1.0f, -1.0f, 1.0f, // 2. right-bottom-front
			-1.0f, -1.0f, 1.0f, // 3. left-bottom-front
			0.0f, 1.0f, 0.0f // 4. top
	};
	private float[] colors = { // Colors of the 5 vertices in RGBA
			0.0f, 0.0f, 1.0f, 1.0f, // 0. blue
			0.0f, 1.0f, 0.0f, 1.0f, // 1. green
			0.0f, 0.0f, 1.0f, 1.0f, // 2. blue
			0.0f, 1.0f, 0.0f, 1.0f, // 3. green
			1.0f, 0.0f, 0.0f, 1.0f // 4. red
	};
	
	private byte[] indices = { // Vertex indices of the 4 Triangles
			0, 1, 4, // back face
			1, 2, 4, // right face
			2, 3, 4, // front face (CCW)
			3, 0, 4 // left face
	};

	// Constructor - Set up the buffers
	public TransPiramidaObject() {
		
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

	// Draw the shape
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
										// orientation
		// Enable arrays and define their buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(colors));
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
				GL10.GL_UNSIGNED_BYTE, makeByteBuffer(indices));
		//gl.glDrawElements(GL10.GL_LINE_STRIP, indices.length,
				//GL10.GL_UNSIGNED_BYTE, makeByteBuffer(indices));
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}