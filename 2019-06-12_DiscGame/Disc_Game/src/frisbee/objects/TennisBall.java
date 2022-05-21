package frisbee.objects;

import javax.media.opengl.GL2;

/* Class: TennisBall
 * Purpose: model the tennis ball
 */
public class TennisBall extends MovingObject
{
	/* Method: update_velocity()
	 * Purpose: change the velocity of the tennis ball
	 * Pre: deltaT must be float
	 * Post: velocity of the tennis ball updated if the bullet hit something
	 */
	public void update_velocity(float deltaT)
	{
		velocity[2] -= GRAVITY * 0.001; // z velocity, drops ball to the ground
		if(pos[2] < -1.5) // Make the ball disappear if its out of view
			if(state == LIVE)
			{
				// If the ball is still alive allow it to bounce by reversing the velocity if it touches the ground
				if(velocity[2] < 0)
					velocity[2] = - velocity[2];
			}
			else
			{
				// If the ball is not live it is hit and needs to be eliminated
				velocity[0] = velocity[1] = velocity[2] = 0;
				state = ELIMINATED;
			}
	}

	/* Method: display()
	 * Purpose: show what the ball look like
	 * Pre: gl must be GL2
	 * Post: draw coordinates of the ball
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
		gl.glColor3f(0.5f, 1f, 0.5f); // Set colour
		
		double r = 0.1; // Set radius for the ball
		for(int j=0;j<9;j++) // 9 sets of layers for the ball
		{
			double b1 = j * 3.14 / 9;
			double b2 = (j + 1) * 3.14 / 9 + 0.01;

			if(j==0) // Top cap
			{
				for(int i=0;i<18;i++) // 18 pieces
				{
					double a1 = i * 6.28 / 18;
					double a2 = (i + 1) * 6.28 / 18 + 0.01;
					
					// Triangles for the top piece
					setVector(v1, r * cos(a1) * sin(b1) + x0, r * sin(a1) * sin(b1) + y0, r * cos(b1) + z0);
					setVector(v3, r * cos(a1) * sin(b2) + x0, r * sin(a1) * sin(b2) + y0, r * cos(b2) + z0);
					setVector(v2, r * cos(a2) * sin(b2) + x0, r * sin(a2) * sin(b2) + y0, r * cos(b2) + z0);
					add_triangle_with_normal(gl, v1, v2, v3);
				}
				
			}
			else if(j==8) // Bottom cap
			{
				for(int i=0;i<18;i++)
				{
					double a1 = i * 6.28 / 18;
					double a2 = (i + 1) * 6.28 / 18 + 0.01;
					
					// Triangles for the bottom piece
					setVector(v1, r * cos(a1) * sin(b1) + x0, r * sin(a1) * sin(b1) + y0, r * cos(b1) + z0);
					setVector(v3, r * cos(a2) * sin(b2) + x0, r * sin(a2) * sin(b2) + y0, r * cos(b2) + z0);
					setVector(v2, r * cos(a2) * sin(b1) + x0, r * sin(a2) * sin(b1) + y0, r * cos(b1) + z0);
					add_triangle_with_normal(gl, v1, v2, v3);
				}
			}
			else // Everything in the middle
			{
				for(int i=0;i<18;i++)
				{
					double a1 = i * 6.28 / 18;
					double a2 = (i + 1) * 6.28 / 18 + 0.01;
					
					// Two triangles forming a square for all middle layers
					setVector(v1, r * sin(b1) * cos(a1)+x0, r * sin(b1) * sin(a1)+y0, r * cos(b1) + z0);
					setVector(v3, r * sin(b2) * cos(a1)+x0, r * sin(b2) * sin(a1)+y0, r * cos(b2) + z0);
					setVector(v2, r * sin(b2) * cos(a2)+x0, r * sin(b2) * sin(a2)+y0, r * cos(b2) + z0);
					add_triangle_with_normal(gl, v1, v2, v3);

					setVector(v1, r * sin(b1) * cos(a1)+x0, r * sin(b1) * sin(a1)+y0, r * cos(b1) + z0);
					setVector(v3, r * sin(b2) * cos(a2)+x0, r * sin(b2) * sin(a2)+y0, r * cos(b2) + z0);
					setVector(v2, r * sin(b1) * cos(a2)+x0, r * sin(b1) * sin(a2)+y0, r * cos(b1) + z0);
					add_triangle_with_normal(gl, v1, v2, v3);
				}
			}
		}
		gl.glEnd();
	}
}