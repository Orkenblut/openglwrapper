package com.freja.openglwrapper.renderer;

/**
 * Class that contains RGBA color
 * @author Orkenblut
 */
public class Color {
	/**
	 * Standard Constructor
	 */
	public Color() {
		r(0);
		g(0);
		b(0);
		a(0);
	}
	
	/**
	 * Color constructor
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param a Alpha
	 */
	public Color(float r, float g, float b, float a) {
		this.r(r);
		this.g(g);
		this.b(b);
		this.a(a);
	}
	
	/**
	 * Color container
	 */
	float color[] = new float[4];
	
	/**
	 * Set Red
	 * @param value Red
	 */
	public void r(float value) { color[0] = value; }
	/**
	 * Set Green
	 * @param value Green
	 */
	public void g(float value) { color[1] = value; }
	/**
	 * Set Blue
	 * @param value Blue
	 */
	public void b(float value) { color[2] = value; }
	/**
	 * Set Alpha
	 * @param value Alpha
	 */
	public void a(float value) { color[3] = value; }
	
	/**
	 * Get Red
	 * @return Red
	 */	
	public float r() { return color[0]; }
	/**
	 * Get Green
	 * @return Green
	 */
	public float g() { return color[1]; }
	/**
	 * Get Blue
	 * @return Blue
	 */		
	public float b() { return color[2]; }
	/**
	 * Get Alpha
	 * @return Alpha
	 */	public float a() { return color[3]; }
	 
	 /**
	  * White color factory
	  * @return Returns the color
	  */
	 public static Color White() {
		 return new Color(1,1,1,1);
	 }
	 
}
