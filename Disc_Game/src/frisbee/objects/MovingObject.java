package frisbee.objects;

import java.util.ArrayList;
import frisbee.util.SystemTime;
import frisbee.util.Time;

/* Class: MovingObject
 * Purpose: provide easy access to methods used by all derived classes of a moving object
 */
public abstract class MovingObject extends BaseObject // abstract class because it is a template definition of "update_velocity" which is an abstract method 
{
	// [Code Private - Contact to View]

	/* Method: MovingObject()
	 * Purpose: perform initializations
	 * Pre: none
	 * Post: initializations complete
	 */
	public MovingObject()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: isLive()
	 * Purpose: determine if an object is still in the game (ie. not hit or eliminated)
	 * Pre: none
	 * Post: state of an object (ie. frisbee, ball, bullet, text3D) is returned
	 */
	public boolean isLive() {
		// [Code Private - Contact to View]
	}
	
	/* Method: needRemove()
	 * Purpose: determine if an object needs to be removed
	 * Pre: none
	 * Post: if an object needs to be removed, true is returned, false if otherwise
	 */
	public boolean needRemove()
	{
		// [Code Private - Contact to View]
	}

	/* Method: startExplosion()
	 * Purpose: generate "crumbs" to simulate explosion
	 * Pre: none
	 * Post: explosion started
	 */
	public void startExplosion()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: setVelocity()
	 * Purpose: assign object velocity
	 * Pre: x, y, z are double
	 * Post: velocity of an object is assigned
	 */
	public void setVelocity(double x, double y, double z)
	{
		// [Code Private - Contact to View]
	}

	/* Method: update_velocity()
	 * Purpose: method part of all derived classes because abstract, general purpose is to update the velocity
	 * Pre: none
	 * Post: model of method set for derived classes
	 */
	public abstract void update_velocity(float deltaT);
	
	/* Method: move()
	 * Purpose: change position of the object
	 * Pre: none
	 * Post: position of object updated
	 */
	public void move()
	{
		// [Code Private - Contact to View]

		// Find deltaT
		
		// update new position according to velocity
	}
	
	/* Method: distance()
	 * Purpose: find distance from this object to passed object, for object collision
	 * Pre: other must be MovingObject
	 * Post: distance returned
	 */
	public double distance(MovingObject other)
	{
		// [Code Private - Contact to View]
	}
}