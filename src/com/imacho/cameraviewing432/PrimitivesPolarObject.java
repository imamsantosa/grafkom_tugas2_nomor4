package com.imacho.cameraviewing432;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

/**
 * Created by Imacho on 4/12/2015.
 */

public class PrimitivesPolarObject {
	
	private float vertices_limacon[]={0.0f,0.0f,0.0f};
	private float vertices_limacon_color[]={0.0f,0.0f,0.0f,0.5f};
	private int batas_sudut=360;
	float step=1.0f;
	private int loop,loop_color;

	public PrimitivesPolarObject() {

        //Inisialisasi
		//Menggambar persamaan koordinat polar, misal r=b+acos(sudut)
		float a = 1;
		float b = 1;

        loop=3;
        loop_color=4;
        vertices_limacon=new float[(int)(11*batas_sudut/step)*3];
        vertices_limacon_color=new float[(int)(11*batas_sudut/step)*4];
        for(float teta=0;teta<=10*batas_sudut;teta+=step){
        	float radian=(float) ((teta/180)*3.14);
			//float _r=(float) (b+(a*Math.cos(radian)));
        	float _r=(float) (Math.cos((7*radian)/4));
        	//float _r=(float) (2+7*Math.sin(3*radian));

			//float _r=(float) ((2+7*Math.sin(3*radian))*Math.cos(5*radian));
			//float _r=(a*exp(b*radian));
			//float _r=10*(a+(b*radian));
			
			vertices_limacon[loop] = (float) (_r*Math.cos(radian));
        	vertices_limacon[loop+1] = (float) (_r*Math.sin(radian));
        	vertices_limacon[loop+2]=0;
        	
        	loop+=3;
        	
        	//mengenerate warna untuk setiap vertex
        	vertices_limacon_color[loop_color]=(float) (_r*Math.cos(radian));
        	vertices_limacon_color[loop_color+1]=(float) (_r*Math.sin(radian));
        	vertices_limacon_color[loop_color+2]=0.5f;
        	vertices_limacon_color[loop_color+3]=0.1f;
        	loop_color+=4;
		}
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
	
	/** The draw method for the primitive object with the GL context */	
	public void draw_limacon(GL10 gl) {
		
		// mengaktifkan pencahayaan
		//float[] mat_amb = {0.2f * 1.0f, 0.2f * 0.4f, 0.2f * 0.4f, 1.0f,};
		float[] mat_amb = {0.0f * 1.0f, 0.0f * 0.4f, 0.0f * 0.4f, 1.0f,};
		//float[] mat_diff = {1.0f, 0.4f, 0.4f, 0.5f,};
		//float[] mat_diff = {1.0f, 0.8f, 0.04f, 1.0f};
		float[] mat_spec = {1.0f, 1.0f, 1.0f, 1.0f}; 

    	//gl.glEnable(GL10.GL_DEPTH_TEST);
    	//gl.glEnable(GL10.GL_CULL_FACE);	
    	//gl.glShadeModel(GL10.GL_SMOOTH);

    	//gl.glEnable(GL10.GL_LIGHTING);	
    	//gl.glEnable(GL10.GL_LIGHT0);	

    	//gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, makeFloatBuffer(mat_amb));	
    	//gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloatBuffer(mat_diff));
    	//gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(mat_spec));
    	//gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, 64);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the triangle
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_limacon));
	    
	    //memetakan warna untuk setiap vertex
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_limacon_color));
	    
	    //draw circle as filled shape
	    gl.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 1, (int) ((int) 10*batas_sudut/step));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    //gl.glDrawArrays(GLES20.GL_LINE_STRIP, 1, (int) ((int) 10*batas_sudut/step)); 
	    //gl.glDrawArrays(GLES20.GL_POINTS, 1, (int) ((int) 10*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}



