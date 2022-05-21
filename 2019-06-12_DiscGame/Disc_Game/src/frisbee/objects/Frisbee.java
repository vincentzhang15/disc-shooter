package frisbee.objects;

import javax.media.opengl.GL2;

/* Class: Frisbee
 * Purpose: model the frisbee
 */
public class Frisbee extends MovingObject
{
	// Variable declarations/initialization
	private static int count = 0;
	private float red, green, blue;

	/* Method: Frisbee()
	 * Purpose: change colour of frisbee
	 * Pre: none
	 * Post: colour of frisbee changed
	 */
	public Frisbee()
	{
		count ++;
		switch(count % 7) // Change colour
		{
			case 0: red = 1.0f; green = 0.3f; blue = 0.3f; break; // Red pink
			case 1: red = 0.3f; green = 1.0f; blue = 0.3f; break; // Light green
			case 2: red = 0.3f; green = 0.3f; blue = 1.0f; break; // Blue
			case 3: red = 1.0f; green = 1.0f; blue = 0.3f; break; // Light yellow
			case 4: red = 1.0f; green = 0.3f; blue = 1.0f; break; // Purple pink
			case 5: red = 0.3f; green = 1.0f; blue = 1.0f; break; // Light blue
			case 6: red = 1.0f; green = 1.0f; blue = 1.0f; break; // White
			default: red = 1.0f; green = 0.3f; blue = 0.3f; break; // Red pink
		}
	}
	
	/* Method: update_velocity()
	 * Purpose: change the velocity of the bullet
	 * Pre: deltaT must be float
	 * Post: velocity of the bullet updated if the bullet hit something
	 */
	public void update_velocity(float deltaT)
	{
		if(state == HITTED)
		{
			velocity[2] -= GRAVITY * 0.0002; // z velocity, drops frisbee to the ground
			if(pos[2] < -1.5) // Make the frisbee disappear if its out of view
			{
				velocity[0] = velocity[1] = velocity[2] = 0; // Set the velocity to 0
				state = ELIMINATED; // Change state to ELIMINATED
			}
		}
	}
	
	/* Method: display()
	 * Purpose: show what the frisbee look like
	 * Pre: gl must be GL2
	 * Post: draw coordinates of the frisbee
	 */
	public void display(GL2 gl)
	{
		move(); // Update position
		
		// Find current position
		double x0 = pos[0];
		double y0 = pos[1];
		double z0 = pos[2];
		
		double [] v1 = new double[3];
		double [] v2 = new double[3];
		double [] v3 = new double[3];
		
		for(Crumb c : crumbs)
			c.draw(gl, x0, y0, z0); // Draw crumbs simulating explosion

		gl.glBegin( GL2.GL_TRIANGLES ); // Starts drawing triangles, takes GL_TRIANGLES as parameter, inherited from the GL interface
		gl.glColor3f( red, green, blue ); // Set colour

		for(int i=0;i<18;i++) // Draw the frisbee in 18 sets of pieces
		{
			double a1 = i * 6.28 / 18;
			double a2 = (i + 1) * 6.28 / 18 + 0.01;
			
			double b = a1*2;
			
			// Draw top of frisbee
			double r = 0.5;
			setVector(v1, 0+x0, 0+y0, 0+z0);
			setVector(v3, r * cos(a1)+x0, r * sin(a1)+y0, 0+z0);
			setVector(v2, r * cos(a2)+x0, r * sin(a2)+y0, 0+z0);
			add_triangle_with_normal(gl, v1, v2, v3);
			

			// Draw second layer of the frisbee
			double r1 = 0.55;
			double z1 = -0.03;
			setVector(v1, r * cos(a1)+x0, r * sin(a1)+y0, 0+z0);
			setVector(v3, r1 * cos(a1)+x0, r1 * sin(a1)+y0, z1+z0);
			setVector(v2, r1 * cos(a2)+x0, r1 * sin(a2)+y0, z1+z0);
			add_triangle_with_normal(gl, v1, v2, v3);

			setVector(v1, r * cos(a1)+x0, r * sin(a1)+y0, 0+z0);
			setVector(v3, r1 * cos(a2)+x0, r1 * sin(a2)+y0, z1+z0);
			setVector(v2, r * cos(a2)+x0, r * sin(a2)+y0, 0+z0);
			add_triangle_with_normal(gl, v1, v2, v3);
			
			// Draw outermost layer of frisbee
			double r2 = 0.58;
			double z2 = -0.08;
			setVector(v1, r1 * cos(a1)+x0, r1 * sin(a1)+y0, z1+z0);
			setVector(v3, r2 * cos(a1)+x0, r2 * sin(a1)+y0, z2+z0);
			setVector(v2, r2 * cos(a2)+x0, r2 * sin(a2)+y0, z2+z0);
			add_triangle_with_normal(gl, v1, v2, v3);

			setVector(v1, r1 * cos(a1)+x0, r1 * sin(a1)+y0, z1+z0);
			setVector(v3, r2 * cos(a2)+x0, r2 * sin(a2)+y0, z2+z0);
			setVector(v2, r1 * cos(a2)+x0, r1 * sin(a2)+y0, z1+z0);
			add_triangle_with_normal(gl, v1, v2, v3);
		}
		gl.glEnd();
	}
	
	
}