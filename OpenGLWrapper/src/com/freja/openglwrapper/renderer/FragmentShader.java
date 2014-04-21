package com.freja.openglwrapper.renderer;

import android.opengl.GLES20;

/**
 * Wrapper for FragmentShader
 * @author Orkenblut
 *
 */
public class FragmentShader {
	/**
	 * OpenGL Handle for the fragment shader
	 */
	private int mHandle;
	
	/**
	 * Initializes basic fragment shader
	 */
	public FragmentShader() {
		LoadByString(baseFragmentShader);
	}
	
	/**
	 * Load Fragment Shader by string
	 * @param shader Shader source
	 */
	public FragmentShader(final String shader) {
		LoadByString(shader);
	}

	/**
	 * Load Fragment Shader
	 * @param shader Shader source
	 * @return Fragment Shader Handle
	 */
	public int LoadByString(final String shader) {
		mHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
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
			throw new RuntimeException("Couldn't create FragmentShader");
		}		
		if(mHandle == 0) {
			throw new RuntimeException("Couldn't compile FragmentShader");
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
	 * Base fragment shader source
	 */
	final String baseFragmentShader =
			"precision mediump float; \n" +
			"varying vec4 v_Color;\n" +

			"void main(void)\n" +
			"{\n" +
			"   //gl_FragColor = v_Color;//vec4( 0.4, 0.0, 0.9, 1.0 );\n" +
			"   gl_FragColor = vec4( 0.4, 0.0, 0.9, 1.0 );\n" +
			"}";
}
