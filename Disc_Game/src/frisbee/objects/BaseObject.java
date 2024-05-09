package frisbee.objects;

import javax.media.opengl.GL2;

/* Class: BaseObject
 * Purpose: provide easy access to methods used by all derived classes of a base object
 */
public class BaseObject
{
	protected double [] pos = new double[3]; // Can only be used by derived classes
	
	/* Method: BaseObject()
	 * Purpose: set default values in pos to 0
	 * Pre: none
	 * Post: default values set to 0 in pos
	 */
	public BaseObject()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: setPosition()
	 * Purpose: set values in pos to the x, y, z coordinates passed in
	 * Pre: x, y, z must be double
	 * Post: pos values set to x, y, z
	 */
	public void setPosition(double x, double y, double z)
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: add_gl_point()
	 * Purpose: add a point to GL2
	 * Pre: g1 must be type GL2, x, y, z must be double
	 * Post: point added to GL2
	 */
	protected void add_gl_point(GL2 gl, double x, double y, double z) {
		// [Code Private - Contact to View]
	}
	
	/* Method: add_triangle_with_normal()
	 * Purpose: add a triangle with a normal
	 * Pre: "g1" must be type GL2, v1, v2, v3 must be double[]
	 * Post: triangle added with the normal
	 */
	public void add_triangle_with_normal(GL2 gl, double v1[], double v2[], double v3[])
	{
		// [Code Private - Contact to View]
		
		// Two vectors of the triangle
			// Calculate normal with cross product
		
			// Indicate normal of the triangle
		
		// Add triangle points
	}

	/* Method: sin()
	 * Purpose: return sine value, shorten Math.sin()
	 * Pre: "A" must be double
	 * Post: sine value returned
	 */
	public static double sin(final double A) {
		// [Code Private - Contact to View]
	}
	
	/* Method: cos()
	 * Purpose: return cosine value, shorten Math.cos()
	 * Pre: "A" must be double
	 * Post: cosine value returned
	 */
	public static double cos(final double A) {
		// [Code Private - Contact to View]
	}

	/* Method: sqrt()
	 * Purpose: return square root value, shorten Math.sqrt()
	 * Pre: "A" must be double
	 * Post: square root value returned
	 */
	public static double sqrt(final double A) {
		// [Code Private - Contact to View]
	}

	/* Method: setVector()
	 * Purpose: assign x, y, z values to array
	 * Pre: vect must be doube[], x, y, z must be double
	 * Post: x, y, z values assigned to array
	 */
	public void setVector(double vect[], double x, double y, double z)
	{
		// [Code Private - Contact to View]
	}

	/* Method: subtraction()
	 * Purpose: subtract values in two arrays
	 * Pre: vect_A, vect_B, diff must be double[]
	 * Post: values in vect_A[] is subtracted by values in vect_B[], difference is stored in diff[]
	 */
	public void subtraction(double vect_A[], double vect_B[], double diff[]) { 
		// [Code Private - Contact to View]
    } 
	
	/* Method: dotProduct()
	 * Purpose: sum of the dot product of two arrays
	 * Pre: vect_a, vect_b, diff must be double[]
	 * Post: dot product of values in vect_a[] and vect_B[] stored in sum and returned
	 */
    public double dotProduct(double vect_a[], double vect_b[]) {
		// [Code Private - Contact to View]
    }
	
	/* Method: magnitude()
	 * Purpose: using Euclidean distance formula to find magnitude of a vector
	 * Pre: vect_a, diff must be double[]
	 * Post: magnitude of vector returned
	 */
	public double magnitude(double vect_a[]) {
		// [Code Private - Contact to View]
	}
	
	/* Method: crossProduct()
	 * Purpose: find cross product of vectors
	 * Pre: vect_A, vect_B, cross_P must be double[]
	 * Post: cross product of vectors assigned to cross_p[]
	 */
	private void crossProduct(double vect_A[], double vect_B[],  double cross_P[]) 
    { 
		// [Code Private - Contact to View]
    } 
}