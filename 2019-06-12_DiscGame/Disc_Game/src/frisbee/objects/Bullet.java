package frisbee.objects;

import javax.media.opengl.GL2;

/* Class: Bullet
 * Purpose: model the bullet
 */
public class Bullet extends MovingObject
{
	/* Method: update_velocity()
	 * Purpose: change the velocity of the bullet
	 * Pre: deltaT must be float
	 * Post: velocity of the bullet updated if the bullet hit something
	 */
	public void update_velocity(float deltaT)
	{
		if(state == HITTED)
		{
			velocity[2] -= GRAVITY * 0.0005; // z velocity, drops bullet to the ground
			if(pos[2] < -1.5) // Make the bullet disappear if its out of view
			{
				velocity[0] = velocity[1] = velocity[2] = 0; // Set the velocity to 0
				state = ELIMINATED; // Change state to ELIMINATED
			}
		}
	}
	
	/* Method: display()
	 * Purpose: show what the bullets look like
	 * Pre: gl must be GL2
	 * Post: draw coordinates of the bullets
	 */
	public void display(GL2 gl)
	{
		move(); // Update position
		
		// Find current position
		double x0 = pos[0];
		double y0 = pos[1];
		double z0 = pos[2];
		
		for(Crumb c : crumbs)
			c.draw(gl, x0, y0, z0); // Draw crumbs simulating explosion
		
		gl.glBegin( GL2.GL_TRIANGLES ); // Starts drawing triangles, takes GL_TRIANGLES as parameter, inherited from the GL interface

		gl.glNormal3f(0f, -1f, 0f); // Set normal for lighting
		gl.glColor3f(1f, 1f, 1f); // Set colour

		// Add points to make triangles; every three lines is a triangle totaling to 6 triangles where the bullet is a square based pyramid with triangle sides and 2 triangle formed square base
		add_gl_point(gl, x0 + 0.0   , y0 - 0.1, z0 + 0.0);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 + 0.025);
									   
		add_gl_point(gl, x0 + 0.0   , y0 - 0.1, z0 + 0.0);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
									   
		add_gl_point(gl, x0 + 0.0   , y0 - 0.1, z0 + 0.0);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 - 0.025);
									   
		add_gl_point(gl, x0 + 0.0   , y0 - 0.1, z0 + 0.0);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
									   
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
									   
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 + 0.025);
		add_gl_point(gl, x0 + 0.025 , y0 - 0.0, z0 - 0.025);
		add_gl_point(gl, x0 - 0.025 , y0 - 0.0, z0 - 0.025);
			
		gl.glEnd(); // Ends the triangles
	}
}