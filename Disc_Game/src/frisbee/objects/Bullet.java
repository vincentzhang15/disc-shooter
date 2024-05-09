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
		// [Code Private - Contact to View]
	}
	
	/* Method: display()
	 * Purpose: show what the bullets look like
	 * Pre: gl must be GL2
	 * Post: draw coordinates of the bullets
	 */
	public void display(GL2 gl)
	{
		// [Code Private - Contact to View]

		// Find current position

		// Add points to make triangles; every three lines is a triangle totaling to 6 triangles where the bullet is a square based pyramid with triangle sides and 2 triangle formed square base

	}
}