package frisbee.objects;
// Reference: Bouncing Text 3D (https://github.com/sgothel/jogl-utils/blob/master/demos/src/jgudemos/BouncingText3D.java)

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
		// Initial values setting
		if(type != 2)
		{
			angularVelocity.x = 4f;
			angularVelocity.y = 1f;
			angularVelocity.z = 6f;
		}
		
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
		position.x = x;
		position.y = y;
		position.z = z;
		
		this.type = type;
		index = last_id + 1;
		text = s;
		
		Rectangle2D rect = tr3.getBounds(text, SCALE_FACTOR);

		float offX = (float) rect.getCenterX();
		float offY = (float) rect.getCenterY();
		float offZ = tr3.getDepth() / 2.0f;

		tr3.setDepth(0.5f); // Thickness of words

		index = tr3.compile(text, -offX, offY, -offZ, SCALE_FACTOR);

		// Random value text
		int rand = ((int)(Math.random() * 7)) % 7;
		switch(rand)
		{
			case 0 : r = 1.0f; g = 0.3f; b = 0.3f; break;
			case 1 : r = 0.3f; g = 1.0f; b = 0.3f; break;
			case 2 : r = 0.3f; g = 0.3f; b = 1.0f; break;
			case 3 : r = 1.0f; g = 1.0f; b = 0.3f; break;
			case 4 : r = 1.0f; g = 0.3f; b = 1.0f; break;
			case 5 : r = 0.3f; g = 1.0f; b = 1.0f; break;
			case 6 : r = 1.0f; g = 1.0f; b = 1.0f; break;
			default: r = 1.0f; g = 0.3f; b = 0.3f; break;
		}
	}

	/* Method: update_velocity()
	 * Purpose: change the velocity of the bouncing text
	 * Pre: deltaT must be float
	 * Post: velocity of the bouncing text updated
	 */
	public void update_velocity(float deltaT)
	{
		if(type == 2) // win, lose
		{
			// Set speed of text
			if(position.z < pos[2])
				velocity[2] = 0;
			
			velocity[2] -= GRAVITY * 0.0002;
			
			if(position.z - pos[2]  > 0.1 )
				if(velocity[2]<0)
					velocity[2] = - velocity[2];
			
			// Degree of movement along the axis
			angle.x += angularVelocity.x * deltaT; if(angle.x > 120f || angle.x < 60f) angularVelocity.x = - angularVelocity.x;
			angle.y += angularVelocity.y * deltaT; if(angle.y > 30f || angle.y < -30f) angularVelocity.y = - angularVelocity.y;
			angle.z += angularVelocity.z * deltaT; if(angle.z > 30f || angle.z < -30f) angularVelocity.z = - angularVelocity.z;
		}
		else if(type == 3) // score table
		{
			// Set speed of text
			if(position.z < pos[2])
				velocity[2] = 0;
			
			velocity[2] -= GRAVITY * 0.0002;
			
			if(position.z - pos[2]  > 0.1 )
				if(velocity[2]<0)
					velocity[2] = - velocity[2];
			
			// Degree of movement along the axis
			angle.x += angularVelocity.x * deltaT; if(angle.x > 110f || angle.x < 70f) angularVelocity.x = - angularVelocity.x;
			angle.y += angularVelocity.y * deltaT; if(angle.y > 20f || angle.y < -20f) angularVelocity.y = - angularVelocity.y;
			angle.z += angularVelocity.z * deltaT; if(angle.z > 20f || angle.z < -20f) angularVelocity.z = - angularVelocity.z;
		}
		else // ready to play
		{
			// Set speed of text
			if(position.z < pos[2])
				velocity[2] = 0;
			
			velocity[2] -= GRAVITY * 0.0002;
			
			if(position.z - pos[2]  > 0.1 )
				if(velocity[2]<0)
					velocity[2] = - velocity[2];
			
			// Degree of movement along the axis
			angle.x += angularVelocity.x * deltaT; if(angle.x > 100f || angle.x < 80f) angularVelocity.x = - angularVelocity.x;
			angle.y += angularVelocity.y * deltaT; if(angle.y > 10f || angle.y < -10f) angularVelocity.y = - angularVelocity.y;
			angle.z += angularVelocity.z * deltaT; if(angle.z > 10f || angle.z < -10f) angularVelocity.z = - angularVelocity.z;
		}
	}	
	
	/* Method: display()
	 * Purpose: Display needs to be re-rendered. This is where all the heavy-lifting gets done.
	 * Pre: gl must be GL2, tr3 must be TextRenderer3D
	 * Post: text shown
	 */
	public void display(GL2 gl, TextRenderer3D tr3)
	{
		move(); // Update position
		
		// Find current position
		double x0 = pos[0];
		double y0 = pos[1];
		double z0 = pos[2];
		
		gl.glPushMatrix(); // "Push the current matrix to it's stack, while preserving it's values."
		gl.glEnable( GL2.GL_NORMALIZE ); // "Entry point to C language function: void glEnable(GLenum cap)"
		
		gl.glTranslatef((float)x0, (float)y0, (float)z0); // "Entry point to C language function: void glTranslated(GLdouble x, GLdouble y, GLdouble z);"

		// Rotate the current matrix.
		gl.glRotatef(angle.x, 1, 0, 0);
		gl.glRotatef(angle.y, 0, 1, 0);
		gl.glRotatef(angle.z, 0, 0, 1);
		
		gl.glColor3f(r, g, b); // Set colour
		tr3.call(index);
		gl.glPopMatrix(); // "Pop the current matrix from it's stack."
	}
}