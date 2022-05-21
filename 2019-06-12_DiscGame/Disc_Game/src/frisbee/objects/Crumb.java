package frisbee.objects;

import javax.media.opengl.GL2;

// Idea comes from:		How to implement explosion in OpenGL with a particle effect?
//						https://gamedev.stackexchange.com/questions/40236/how-to-implement-explosion-in-opengl-with-a-particle-effect
//					    https://github.com/sgothel/jogl-demos/blob/master/src/demos/particles/engine/Engine.java
//                      https://github.com/sgothel/jogl-demos/blob/master/src/demos/particles/engine/Particle.java
//

/* Class: Crumb
 * Purpose: model explosion
 */
public class Crumb extends BaseObject
{
	private double rr = 0.01; // Radius for crumbs to appear in
	
	/* Method: draw()
	 * Purpose: draw crumbs
	 * Pre: g1 must be type GL2, x, y, z must be double
	 * Post: draws the crumbs
	 */
	public void draw(GL2 gl, double x, double y, double z) // x, y, z positions of the objects to explode
	{
		rr += 0.03; // Increase size of radius of spawning as object approaches the player
		if(rr > 0.8)
			rr = 0.8; // Stop at radius 0.8 if too large
		
		// Find random positions to spawn crumbs
		double r = Math.random() * rr;
		double a = Math.random() * 6.28;
		double b = Math.random() * 3.14;
		
		double x0 = x + r * sin(b) * cos(a);
		double y0 = y + r * cos(b);
		double z0 = z + r * sin(b) * sin(a);

		gl.glBegin( GL2.GL_TRIANGLES );  // Starts drawing triangles, takes GL_TRIANGLES as parameter, inherited from the GL interface

		gl.glNormal3f(0f, -1f, 0f); // Set normal for lighting
		gl.glColor3f(1f, (float)Math.random(), 0f); // Set colour
		
		// Add two triangles to make a square
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
									   
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
			
		gl.glEnd(); // End the triangles
	}
}