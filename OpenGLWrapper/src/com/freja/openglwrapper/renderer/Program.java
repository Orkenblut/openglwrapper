package com.freja.openglwrapper.renderer;

import android.opengl.GLES20;

/**
 * Wrapper for OpenGL VertexProgram (Compined Fragment & Vertex Shader)
 * @author Orkenblut
 *
 */
public class Program {
	private int mHandle;
	private int mMVPMatrixHandle;
	private int mPositionHandle;
	private int mColorHandle;

	/**
	 * Compile Fragment & Vertex Shader into one Program
	 * @param vs VertexShader
	 * @param fs FragmentShader (Pixelshader)
	 * @throws RuntimeException
	 */
	public void Compile(VertexShader vs, FragmentShader fs) throws RuntimeException {
		mHandle = GLES20.glCreateProgram();
		
		if(mHandle != 0) {
			GLES20.glAttachShader(mHandle, vs.getHandle());
			GLES20.glAttachShader(mHandle, fs.getHandle());
			
			GLES20.glBindAttribLocation(mHandle, 0, "a_Position");
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
	}

	/**
	 * Use Program
	 */
	public void Use() {
		mMVPMatrixHandle = GLES20.glGetUniformLocation(mHandle, "u_MVPMatrix");
		mPositionHandle = GLES20.glGetAttribLocation(mHandle, "a_Position");
		mColorHandle = GLES20.glGetAttribLocation(mHandle, "a_Color");
		
		GLES20.glUseProgram(mHandle);
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
