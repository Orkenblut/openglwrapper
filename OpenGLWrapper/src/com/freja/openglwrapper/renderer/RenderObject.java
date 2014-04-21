package com.freja.openglwrapper.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Object that should be rendered
 * @author Orkenblut
 *
 */
public class RenderObject {
	/**
	 * Vertex & Color Buffer (Ordered: (x, y, z), (r, g, b, a))
	 */
	private FloatBuffer mMesh = null;
	/**
	 * How should OpenGL handle this mesh?
	 */
	private int mRenderType;

	/**
	 * Projection Matrix
	 */
	private float[] mModelMatrix = new float[16];
	
	/**
	 * Bytes per float 
	 */
	static private final int mBytesPerFloat = 4;

	/**
	 * Combined Model View Projection Matrix
	 */
	private float[] mMVPMatrix = new float[16];
	
	/**
	 * Projection Matrix
	 */
	private float[] mProjectionMatrix = new float[16];

	/**
	 * How many elements per vertex
	 */
	static private final int mStrideBytes = 7 * mBytesPerFloat;
	
	/**
	 * Offset of the position data
	 */
	static private final int mPositionOffset = 0;
	
	/**
	 * Size of the position data elements
	 */
	static private final int mPositionDataSize = 3;
	
	/**
	 * Offset to color data
	 */
	static private final int mColorOffset = 3;
	/**
	 * Size of the color data elements
	 */
	static private final int mColorDataSize = 4;
	 
	/**
	 * Create RenderObject
	 * @param program Linked Shader Program
	 * @param buffer Vertex / Color Buffer
	 */
	public RenderObject(Program program, FloatBuffer buffer, int renderType) {
		mProgram = program;
		mMesh = buffer;
		mRenderType = renderType;
	}
	
	public static RenderObject createQuad(float size, float depth, Color color) {
		float halfSize = size / 2.f;
		float [] triangles = {
								-halfSize, -halfSize, depth,
								color.r(), color.g(), color.b(), color.a(), 
								
								halfSize, -halfSize, depth,
								color.r(), color.g(), color.b(), color.a(),
								
								halfSize, halfSize, depth,
								color.r(), color.g(), color.b(), color.a(),
								
								-halfSize, halfSize, depth,
								color.r(), color.g(), color.b(), color.a(),
							};

		FloatBuffer mesh = ByteBuffer.allocateDirect(triangles.length * mBytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
		mesh.put(triangles).position(0); 
		
		return new RenderObject(new Program(new VertexShader(), new FragmentShader()), mesh, GLES20.GL_TRIANGLE_FAN);
	}
	
	public void setModelMatrix(float [] matrix) {
		this.mModelMatrix = matrix;
	}
	
	public float[] getModelMatrix() {
		return mModelMatrix;
	}
	
	private void drawBuffer() {
		mMesh.position(mPositionOffset);
		
		// Vertex Info
		GLES20.glVertexAttribPointer(mProgram.getPositionHandle(), mPositionDataSize, GLES20.GL_FLOAT, false, mStrideBytes, mMesh);
		GLES20.glEnableVertexAttribArray(mProgram.getPositionHandle());
		
		// Color Info
		GLES20.glVertexAttribPointer(mProgram.getColorHandle(), mColorDataSize, GLES20.GL_FLOAT, false, mStrideBytes, mMesh);
		GLES20.glEnableVertexAttribArray(mProgram.getColorHandle());
		
		// Create mvp matrix
		Matrix.multiplyMM(mMVPMatrix, 0, this.mProjectionMatrix, 0, mMVPMatrix, 0);
		
		GLES20.glUniformMatrix4fv(mProgram.getMVPMatrixHandle(), 1, false, mMVPMatrix, 0);
		GLES20.glDrawArrays(mRenderType, 0, 3);		
	}
	
	public void draw() {
		mProgram.Use();
		
		drawBuffer();
	}


	private Program mProgram;

	/**
	 * Return the actual projection matrix
	 * @return The projection matrix
	 */
	public float[] getProjectionMatrix() {
		return mProjectionMatrix;
	}
}
