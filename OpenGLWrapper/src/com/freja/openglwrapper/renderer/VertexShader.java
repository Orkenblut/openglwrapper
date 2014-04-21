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
	 * Initialize with base vertex shader
	 */
	public VertexShader() {
		LoadByString(baseVertexShader);
	}
	
	/**
	 * Initilize with shader
	 * @param shader Shader source
	 */
	public VertexShader(final String shader) {
		LoadByString(shader);
	}

	/**
	 * Load Vertex Shader
	 * @throws Exception 
	 * @return Vertex Shader Handle
	 */
	public int LoadByString(final String shader) throws RuntimeException {
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
	
	/**
	 * Base vertex shader source
	 */
	final String baseVertexShader = 
			"uniform mat4 u_MVPMatrix;\n" +
			"attribute vec4 a_Vertex;\n" +
			"attribute vec4 a_Color;\n" +
			"varying vec4 v_Color;\n" +
		
			"void main(void)\n" +
			"{\n" +
			"   v_Color = a_Color;\n" +
			"   v_Color = vec4(1,1,1,1);\n" +
			"   gl_Position = u_MVPMatrix * a_Vertex;\n" +
			"}";
	
}
