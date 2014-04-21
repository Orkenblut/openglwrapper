package com.freja.openglwrapper.renderer;

import junit.framework.Assert;
import android.opengl.GLES20;

/**
 * Wrapper for OpenGL VertexProgram (Compined Fragment & Vertex Shader)
 * @author Orkenblut
 *
 */
public class Program {
	/**
	 * The linked vertex shader
	 */
	private VertexShader mVertexShader;
	/**
	 * The linked fragment shader
	 */
	private FragmentShader mFragmentShader;	
	/**
	 * OpenGL Program handle
	 */
	private int mHandle;
	/**
	 * Model View Projection Matrix handle
	 */
	private int mMVPMatrixHandle;
	/**
	 * Position handle
	 */
	private int mPositionHandle;
	/**
	 * Color handle
	 */
	private int mColorHandle;	
	/**
	 * Last bound handle to prevent binding the program multiple times
	 */
	static private int LastHandle = -1;
	
	/**
	 * Standard constructor - Links both shaders
	 * @param vs Vertex shader
	 * @param fs Fragment shader
	 */
	public Program(VertexShader vs, FragmentShader fs) {
		Compile(vs, fs);
	}

	/**
	 * Compile Fragment & Vertex Shader into one Program
	 * @param vs VertexShader
	 * @param fs FragmentShader (Pixelshader)
	 * @throws RuntimeException
	 */
	void Compile(VertexShader vs, FragmentShader fs) throws RuntimeException {
		mHandle = GLES20.glCreateProgram();
		
		if(mHandle != 0) {
			GLES20.glAttachShader(mHandle, vs.getHandle());
			GLES20.glAttachShader(mHandle, fs.getHandle());
			
			GLES20.glBindAttribLocation(mHandle, 0, "a_Vertex");
			GLES20.glBindAttribLocation(mHandle, 1, "a_Color");
			
			GLES20.glLinkProgram(mHandle);
			
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(mHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
			
			if(linkStatus[0] == 0) {
				GLES20.glDeleteProgram(mHandle);
				mHandle = 0;
			}
		}
		
		if(mHandle == 0) {
			throw new RuntimeException("Error linking Vertex & Fragmentshader");
		}
		
		mVertexShader = vs;
		mFragmentShader = fs;
	}

	/**
	 * Use Program
	 */
	public void Use() {
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mHandle, "u_MVPMatrix");
		Assert.assertTrue(mMVPMatrixHandle != -1);
		mPositionHandle = GLES20.glGetAttribLocation(mHandle, "a_Vertex");
		Assert.assertTrue(mPositionHandle != -1);
		mColorHandle = GLES20.glGetAttribLocation(mHandle, "a_Color");
		Assert.assertTrue(mColorHandle != -1);
		
		// Don't wan't to bind it twice right?
		if(mHandle != LastHandle) {
			GLES20.glUseProgram(mHandle);
			LastHandle = mHandle;
		}
	}		
	
	/**
	 * Get Model View Projection Handle
	 * @return Model View Projection handle
	 */
	public int getMVPMatrixHandle() {
		return mMVPMatrixHandle;
	}
	
	/**
	 * Get Position handle
	 * @return position handle
	 */
	public int getPositionHandle() {
		return mPositionHandle;
	}

	/**
	 * Get Color Handle
	 * @return Color Handle
	 */
	public int getColorHandle() {
		return mColorHandle;
	}
}
