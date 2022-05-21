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
		for(int i=0;i<3;i++)
			pos[i] = 0;
	}
	
	/* Method: setPosition()
	 * Purpose: set values in pos to the x, y, z coordinates passed in
	 * Pre: x, y, z must be double
	 * Post: pos values set to x, y, z
	 */
	public void setPosition(double x, double y, double z)
	{
		pos[0] = (float)x;
		pos[1] = (float)y;
		pos[2] = (float)z;
	}
	
	/* Method: add_gl_point()
	 * Purpose: add a point to GL2
	 * Pre: g1 must be type GL2, x, y, z must be double
	 * Post: point added to GL2
	 */
	protected void add_gl_point(GL2 gl, double x, double y, double z) {
		gl.glVertex3f( (float)x, (float)y, (float)z );
	}
	
	/* Method: add_triangle_with_normal()
	 * Purpose: add a triangle with a normal
	 * Pre: "g1" must be type GL2, v1, v2, v3 must be double[]
	 * Post: triangle added with the normal
	 */
	public void add_triangle_with_normal(GL2 gl, double v1[], double v2[], double v3[])
	{
		double [] v12 = new double[3];
		double [] v13 = new double[3];
		double [] normal = new double[3];
		
		// Two vectors of the triangle
		subtraction(v1, v2, v12);
		subtraction(v1, v3, v13);
		crossProduct(v12, v13, normal); // Calculate normal with cross product
		
		gl.glNormal3f((float)normal[0], (float)normal[1], (float)normal[2]); // Indicate normal of the triangle
		
		// Add triangle points
		add_gl_point(gl, v1[0], v1[1], v1[2]);
		add_gl_point(gl, v2[0], v2[1], v2[2]);
		add_gl_point(gl, v3[0], v3[1], v3[2]);
	}

	/* Method: sin()
	 * Purpose: return sine value, shorten Math.sin()
	 * Pre: "A" must be double
	 * Post: sine value returned
	 */
	public static double sin(final double A) {
		return Math.sin(A);
	}
	
	/* Method: cos()
	 * Purpose: return cosine value, shorten Math.cos()
	 * Pre: "A" must be double
	 * Post: cosine value returned
	 */
	public static double cos(final double A) {
		return Math.cos(A);
	}

	/* Method: sqrt()
	 * Purpose: return square root value, shorten Math.sqrt()
	 * Pre: "A" must be double
	 * Post: square root value returned
	 */
	public static double sqrt(final double A) {
		return Math.sqrt(A);
	}

	/* Method: setVector()
	 * Purpose: assign x, y, z values to array
	 * Pre: vect must be doube[], x, y, z must be double
	 * Post: x, y, z values assigned to array
	 */
	public void setVector(double vect[], double x, double y, double z)
	{
		vect[0] = x;
		vect[1] = y;
		vect[2] = z;
	}

	/* Method: subtraction()
	 * Purpose: subtract values in two arrays
	 * Pre: vect_A, vect_B, diff must be double[]
	 * Post: values in vect_A[] is subtracted by values in vect_B[], difference is stored in diff[]
	 */
	public void subtraction(double vect_A[], double vect_B[], double diff[]) { 
		// Reference: How to subtract values of two lists/arrays in Java? (https://stackoverflow.com/questions/40644999/how-to-subtract-values-of-two-lists-arrays-in-java)
		for(int i =0; i< 3; i++)
			diff[i] = vect_A[i] - vect_B[i]; 
    } 
	
	/* Method: dotProduct()
	 * Purpose: sum of the dot product of two arrays
	 * Pre: vect_a, vect_b, diff must be double[]
	 * Post: dot product of values in vect_a[] and vect_B[] stored in sum and returned
	 */
    public double dotProduct(double vect_a[], double vect_b[]) {
		// Reference: return the inner product of Vector a and b (https://introcs.cs.princeton.edu/java/33design/Vector.java.html)
		double sum = 0.0;
		for (int i = 0; i < 3; i++)
			sum = sum + (vect_a[i] * vect_b[i]);
		return sum;
    }
	
	/* Method: magnitude()
	 * Purpose: using Euclidean distance formula to find magnitude of a vector
	 * Pre: vect_a, diff must be double[]
	 * Post: magnitude of vector returned
	 */
	public double magnitude(double vect_a[]) {
		// Reference: return the Euclidean norm of a Vector (https://introcs.cs.princeton.edu/java/33design/Vector.java.html)
		return sqrt(dotProduct(vect_a, vect_a));
	}
	
	/* Method: crossProduct()
	 * Purpose: find cross product of vectors
	 * Pre: vect_A, vect_B, cross_P must be double[]
	 * Post: cross product of vectors assigned to cross_p[]
	 */
	private void crossProduct(double vect_A[], double vect_B[],  double cross_P[]) 
    { 
		// Reference: Function to find cross product of two vector array. (https://www.geeksforgeeks.org/program-dot-product-cross-product-two-vector/)
        cross_P[0] = vect_A[1] * vect_B[2] - vect_A[2] * vect_B[1]; 
        cross_P[1] = vect_A[0] * vect_B[2] - vect_A[2] * vect_B[0]; 
        cross_P[2] = vect_A[0] * vect_B[1] - vect_A[1] * vect_B[0]; 
    } 
}