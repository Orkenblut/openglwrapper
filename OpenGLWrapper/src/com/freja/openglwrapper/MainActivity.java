package com.freja.openglwrapper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Main Test Activity
 * @author Orkenblut
 *
 */
public class MainActivity extends ActionBarActivity {
	/**	
	 * Main OpenGL Surface
	 */
	private GLSurfaceView mGLSurfaceView;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CreateOpenGLSurface();
	}

	/**
	 * Create OpenGL Surface
	 */
	private void CreateOpenGLSurface() {
		mGLSurfaceView = new GLSurfaceView(this);
		
		final ActivityManager activitiyManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activitiyManager.getDeviceConfigurationInfo();
		final Boolean supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
		
		if(supportEs2) {
			mGLSurfaceView.setEGLContextClientVersion(2);
			
			mGLSurfaceView.setRenderer(new FrejaRenderer());
		}
		
		setContentView(mGLSurfaceView);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResume(){
		super.onResume();
		mGLSurfaceView.onResume();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();
		mGLSurfaceView.onResume();
	}
}
