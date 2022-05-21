package frisbee.objects;
// Reference: Bouncing Text 3D (https://github.com/sgothel/jogl-utils/blob/master/demos/src/jgudemos/BouncingText3D.java)

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Iterator;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import frisbee.util.TextRenderer3D;

/* Class: ReadyToPlay
 * Purpose: Simple class to demonstrate the use of compile/call with TextRenderer3D
 */
public class ReadyToPlay
{
	private TextRenderer3D tr3;
	private ArrayList<Text3D> textInfo = new ArrayList<Text3D>();

	/* Method: init()
	 * Purpose: Initialize the GL instance. Set up the lights and other variables and conditions specific to this class
	 * Pre: drawable must be GLAutoDrawable, textRenderer must be TextRenderer3D
	 * Post: GL initialized, other conditions set up
	 */
	public void init(GLAutoDrawable drawable, TextRenderer3D textRenderer)
	{
		GL2 gl = drawable.getGL().getGL2();
		tr3 = textRenderer;
		
		textInfo.clear(); // Clear arrayList
		textInfo.add(new Text3D(gl, tr3, 1, 0f,  -4.0f, 0.5f, "Are you ready?")); // Add text
		textInfo.add(new Text3D(gl, tr3, 1, 0f,  -4.0f, 0.3f, "Click to start.")); // Add text
	}

	/* Method: display()
	 * Purpose: Display needs to be re-rendered. This is where all the heavy-lifting gets done.
	 * Pre: drawable must be GLAutoDrawable, scene must be int
	 * Post: text shown
	 */
	public void display(GLAutoDrawable drawable, int scene)
	{
		GL2 gl = drawable.getGL().getGL2();

		try {
			for(int i=0; i<textInfo.size(); i++)
			{
				Text3D info = textInfo.get(i);
				info.display(gl, tr3); // Display items in the arraylist
			}
		}
		catch (Exception e) {
			e.printStackTrace(); // Print exception if any
		}
	}
}	
