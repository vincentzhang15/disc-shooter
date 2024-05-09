package frisbee.objects;

import java.awt.geom.Rectangle2D;

import javax.media.opengl.GL2;
import javax.vecmath.Point3f;

import frisbee.util.TextRenderer3D;

/* Class: Text3D
 * Purpose: create moving text in screen
 */
public class Text3D extends MovingObject
{
	// variable declarations/initializations
	private static int last_id;
	private int type = 0;
	private int	index; // display list index
	
	private Point3f angle = new Point3f(90f, 0f, 0f);;
	private Point3f position = new Point3f(); // save original position, if the object jump higher than original position, reset the vertical velocity to zero to prevent the object from jump too high;
	private Point3f angularVelocity = new Point3f(40f, 10f, 60f);

	// Cache of the RGB color
	private float r;
	private float g;
	private float b;

	private String 	text;
	private static final float  SCALE_FACTOR = 0.05f;

	/* Method: Text3D()
	 * Purpose: perform initialization actions
	 * Pre: gl must be GL2, tr3 must be TextRenderer3D, type must be int, x, y, z, must be float, s must be String
	 * Post: Initialization actions performed
	 */
	public Text3D(GL2 gl, TextRenderer3D tr3, int type, float x, float y, float z, String s)
	{
		// [Code Private - Contact to View]

		// Initial values setting

		// Random value text
	}

	/* Method: update_velocity()
	 * Purpose: change the velocity of the bouncing text
	 * Pre: deltaT must be float
	 * Post: velocity of the bouncing text updated
	 */
	public void update_velocity(float deltaT)
	{
		// [Code Private - Contact to View]

			// Set speed of text
			
			// Degree of movement along the axis

	}	
	
	/* Method: display()
	 * Purpose: Display needs to be re-rendered. This is where all the heavy-lifting gets done.
	 * Pre: gl must be GL2, tr3 must be TextRenderer3D
	 * Post: text shown
	 */
	public void display(GL2 gl, TextRenderer3D tr3)
	{
		// [Code Private - Contact to View]
		
		// Find current position

		// Rotate the current matrix.
	}
}