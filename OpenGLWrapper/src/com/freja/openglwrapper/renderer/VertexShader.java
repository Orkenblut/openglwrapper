package com.freja.openglwrapper.renderer;

import android.opengl.GLES20;

/**
 * Wrapper for Vertex Shaders
 * @author Orkenblut
 *
 */
public class VertexShader {
	/**
	 * OpenGL Handle
	 */
	private int mHandle;

	/**
	 * Load Vertex Shader
	 * @throws Exception 
	 * @return Vertex Shader Handle
	 */
	public int LoadByString(String shader) throws RuntimeException {
		mHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		if(mHandle != 0) {
			GLES20.glShaderSource(mHandle, shader);
			GLES20.glCompileShader(mHandle);
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(mHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			if(compileStatus[0] == 0) {
				GLES20.glDeleteShader(mHandle);
				mHandle = 0;
			}
		} else {
			throw new RuntimeException("Couldn't create VertexShader");
		}		
		if(mHandle == 0) {
			throw new RuntimeException("Couldn't compile VertexShader");
		}
		return mHandle;
	}

	/**
	 * Get OpenGL Handle
	 * @return OpenGL Handle
	 */
	public int getHandle() {
		return mHandle;
	}
}
