package frisbee.objects;

import java.util.ArrayList;
import frisbee.util.SystemTime;
import frisbee.util.Time;

/* Class: MovingObject
 * Purpose: provide easy access to methods used by all derived classes of a moving object
 */
public abstract class MovingObject extends BaseObject // abstract class because it is a template definition of "update_velocity" which is an abstract method 
{
	// Constants
	public static final int LIVE = 1;
	public static final int HITTED = 2;
	public static final int ELIMINATED = 3;
	public static final double GRAVITY = 9.8;
	
	// Variable declarations/assignments
	protected Time time; // Protected can only be used in derived classes
	protected double [] velocity = new double[3];
	public int state;
	public int time_left_to_delete = 5; // Time for objects to delete
	
	public ArrayList<Crumb> crumbs = new ArrayList<Crumb>(); // Dots to simulate explosion

	/* Method: MovingObject()
	 * Purpose: perform initializations
	 * Pre: none
	 * Post: initializations complete
	 */
	public MovingObject()
	{
		for(int i=0;i<3;i++)
			velocity[i] = 0; // Initialize velocity to be 0
		state = LIVE; // The object is not hit or eliminated
		time = new SystemTime();
		((SystemTime) time).rebase(); // Call method in SystemTime to rebase timer
	}
	
	/* Method: isLive()
	 * Purpose: determine if an object is still in the game (ie. not hit or eliminated)
	 * Pre: none
	 * Post: state of an object (ie. frisbee, ball, bullet, text3D) is returned
	 */
	public boolean isLive() {
		return state == LIVE;
	}
	
	/* Method: needRemove()
	 * Purpose: determine if an object needs to be removed
	 * Pre: none
	 * Post: if an object needs to be removed, true is returned, false if otherwise
	 */
	public boolean needRemove()
	{
		if(state == ELIMINATED)
		{
			time_left_to_delete--; // Decrease countdown of time left to delete an object
			if(time_left_to_delete < 1) // Delete if time left to delete is less than 1
				return true;
		}
		
		// if object has move out of sight
		if(pos[1] < -5 || pos[1] > 50) // y position
			return true;
		return false;
	}

	/* Method: startExplosion()
	 * Purpose: generate "crumbs" to simulate explosion
	 * Pre: none
	 * Post: explosion started
	 */
	public void startExplosion()
	{
		for(int i=0;i<500;i++)
			crumbs.add(new Crumb()); // Add a new dot on the screen
		state = HITTED; // The object is hit
	}
	
	/* Method: setVelocity()
	 * Purpose: assign object velocity
	 * Pre: x, y, z are double
	 * Post: velocity of an object is assigned
	 */
	public void setVelocity(double x, double y, double z)
	{
		velocity[0] = (float)x;
		velocity[1] = (float)y;
		velocity[2] = (float)z;
	}

	/* Method: update_velocity()
	 * Purpose: method part of all derived classes because abstract, general purpose is to update the velocity
	 * Pre: none
	 * Post: model of method set for derived classes
	 */
	public abstract void update_velocity(float deltaT);  // Reference: Java: Abstract classes and abstract methods (https://idratherbewriting.com/java-abstract-methods/)

	/* Method: move()
	 * Purpose: change position of the object
	 * Pre: none
	 * Post: position of object updated
	 */
	public void move()
	{
		// Find deltaT
		time.update();
		float deltaT = (float) time.deltaT();

		update_velocity(deltaT);
		
		// update new position according to velocity
		for(int i=0;i<3;i++)
			pos[i] += velocity[i] * deltaT;
	}
	
	/* Method: distance()
	 * Purpose: find distance from this object to passed object, for object collision
	 * Pre: other must be MovingObject
	 * Post: distance returned
	 */
	public double distance(MovingObject other)
	{
		double [] v = new double[3];
		subtraction(pos, other.pos, v);
		return magnitude(v);
	}
}