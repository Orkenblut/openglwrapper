package com.freja.openglwrapper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.freja.openglwrapper.renderer.Color;
import com.freja.openglwrapper.renderer.RenderObject;

public class FrejaRenderer implements Renderer {
	/**
	 * View Matrix
	 */
	private float[] mViewMatrix = new float[16];
	

	private RenderObject mRenderObject;
	
	/**
	 * Standard Constructor 
	 */
	public FrejaRenderer() {
		
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

		mRenderObject = RenderObject.createQuad(2, -2, Color.White());	}

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
		
		Matrix.frustumM(mRenderObject.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
				
		long time = SystemClock.uptimeMillis() % 10000;
		float angle = (260.f / 10000.f) * (int)time;
		
		float [] mm = mRenderObject.getModelMatrix();
		Matrix.setIdentityM(mm, 0);
		Matrix.rotateM(mm, 0, angle, 0f, 0f, 1);
		
		mRenderObject.setModelMatrix(mm);
		
		mRenderObject.draw();
	}

}
