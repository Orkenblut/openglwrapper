package com.freja.openglwrapper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.freja.openglwrapper.renderer.FragmentShader;
import com.freja.openglwrapper.renderer.Program;
import com.freja.openglwrapper.renderer.VertexShader;

public class FrejaRenderer implements Renderer {
	/**
	 * Vertex & Color Buffer (Ordered: (x, y, z), (r, g, b, a))
	 */
	private final FloatBuffer mTriangle1Vertices;
	//private final FloatBuffer mTriangle2Vertices;
	//private final FloatBuffer mTriangle3Vertices;
	
	/**
	 * View Matrix
	 */
	private float[] mViewMatrix = new float[16];
	
	/**
	 * Projection Matrix
	 */
	private float[] mProjectionMatrix = new float[16];
	
	/**
	 * Projection Matrix
	 */
	private float[] mModelMatrix = new float[16];
	
	/**
	 * Bytes per float 
	 */
	private final int mBytesPerFloat = 4;

	/**
	 * Combined Model View Projection Matrix
	 */
	private float[] mMVPMatrix = new float[16];

	/**
	 * How many elements per vertex
	 */
	private final int mStrideBytes = 7 * mBytesPerFloat;
	
	/**
	 * Offset of the position data
	 */
	private final int mPositionOffset = 0;
	
	/**
	 * Size of the position data elements
	 */
	private final int mPositionDataSize = 3;
	
	/**
	 * Offset to color data
	 */
	private final int mColorOffset = 3;
	/**
	 * Size of the color data elements
	 */
	private final int mColorDataSize = 4;

	/**
	 * Standard Constructor 
	 */
	public FrejaRenderer() {
		final float[] triangle1Vertices = {
			// x, y, z,
			// r, g, b, a
			-.5f, -.25f, .0f,
			1.0f, 0f, 0f, 1f,
			
			.5f, -.25f, 0f,
			0f, 0f, 1f, 1f,
			
			0f, .5f, 0f,
			0f, 1f, 0f, 1f			
		};
		
		mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1Vertices.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangle1Vertices.put(triangle1Vertices).position(0);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(.5f, .5f, .5f, .5f);

		final float [] eye = { 0f, 0f, 1.5f };
		final float [] look =  { 0f, 0f, -5f };
		final float [] up = { 0f, 1f, 0f };
		
		Matrix.setLookAtM(mViewMatrix, 0, eye[0], eye[1], eye[2], look[0], look[1], look[2], up[0], up[1], up[2]);
		
		VertexShader vs = new VertexShader();
		vs.LoadByString(vertexShader);
		
		FragmentShader fs = new FragmentShader();
		fs.LoadByString(fragmentShader);

		mProgram = new Program();
		mProgram.Compile(vs, fs);
		mProgram.Use();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		final float ratio = (float)width / (float)height;
		final float left = - ratio;
		final float right = ratio;
		final float bottom = -1f;
		final float top = 1f;
		final float near = 1f;
		final float far = 10f;
		
		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
	}
 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		long time = SystemClock.uptimeMillis() % 10000;
		float angle = (260.f / 10000.f) * (int)time;
		
		Matrix.setIdentityM(mModelMatrix, 0);
		Matrix.rotateM(mModelMatrix, 0, angle, 0f, 0f, 1);
		
		drawMatrix(mTriangle1Vertices);
	}

	private void drawMatrix(FloatBuffer vertices) {
		vertices.position(mPositionOffset);
		
		// Vertex Info
		GLES20.glVertexAttribPointer(mProgram.getPositionHandle(), mPositionDataSize, GLES20.GL_FLOAT, false, mStrideBytes, vertices);
		GLES20.glEnableVertexAttribArray(mProgram.getPositionHandle());
		
		// Color Info
		GLES20.glVertexAttribPointer(mProgram.getColorHandle(), mColorDataSize, GLES20.GL_FLOAT, false, mStrideBytes, vertices);
		GLES20.glEnableVertexAttribArray(mProgram.getColorHandle());
		
		// Create mvp matrix
		Matrix.multiplyMM(mMVPMatrix, 0, this.mProjectionMatrix, 0, mMVPMatrix, 0);
		
		GLES20.glUniformMatrix4fv(mProgram.getMVPMatrixHandle(), 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);		
	}

	final String vertexShader = 
			"uniform mat4 u_MVPMatrix;\n" +
			"attribute vec4 a_Vertex;\n" +
			"attribute vec4 a_Color;\n" +
			"varying vec4 v_Color;\n" +
		
			"void main(void)\n" +
			"{\n" +
			"   v_Color = a_Color;\n" +
			"   gl_Position = u_MVPMatrix * a_Vertex;\n" +
			"}";
	
	final String fragmentShader =
			"precision mediump float; \n" +
			"varying vec4 v_Color;\n" +

			"void main(void)\n" +
			"{\n" +
			"   gl_FragColor = v_Color;//vec4( 0.4, 0.0, 0.9, 1.0 );\n" +
			"}";

	private Program mProgram;
}
