/**
 * 
 */
package com.freja.openglwrapper;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.util.Log;

/**
 * @author Orkenblut
 *
 */
public class Utility {
	/**
	 * Read File To String
	 * @param fileName Input file name
	 * @return Complete File as String
	 */
	public static String ReadFile(String fileName) {		
		BufferedInputStream is = null;
		try {
			String result = "";
			//is = new BufferedInputStream(new FileInputStream(fileName));
			is = new BufferedInputStream( Class.class.getClassLoader().getResourceAsStream(fileName));
			int c;
			while((c = is.read()) != -1) {
				result += c;
			}
			Log.i("ReadFile " + fileName, result);
			return result;
		} catch(FileNotFoundException fnfEx) {
			Log.e("Exception", fnfEx.toString() + fnfEx.getStackTrace());
		} catch(IOException ioEx) {
			Log.e("Exception", ioEx.toString() + ioEx.getStackTrace());
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(IOException ioEx) {
					Log.e("Exception", ioEx.toString() + ioEx.getStackTrace());
				}
			}
		}
		return null;
	}
}
