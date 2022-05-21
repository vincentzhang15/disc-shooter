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

/* Class: ScoreBoard
 * Purpose: create scoreboard
 */
public class ScoreBoard
{
	private TextRenderer3D tr3;
	private ArrayList<Text3D> textInfo = new ArrayList<Text3D>();
	private GL2 saved_gl;

	/* Method: init()
	 * Purpose: Initialize the GL instance. Set up the lights and other variables and conditions specific to this class
	 * Pre: drawable must be GLAutoDrawable, textRenderer must be TextRenderer3D
	 * Post: GL initialized, other conditions set up
	 */
	public void init(GLAutoDrawable drawable, TextRenderer3D textRenderer) {
		GL2 gl = drawable.getGL().getGL2();
		tr3 = textRenderer;
	}
	
	/* Method: clear()
	 * Purpose: clear ArrayList
	 * Pre: none
	 * Post: ArrayList cleared
	 */
	public void clear() {
		textInfo.clear();
	}

	/* Method: set_gl()
	 * Purpose: assign gl to saved_gl
	 * Pre: gl must be GL2
	 * Post: value assigned
	 */
	public void set_gl(GL2 gl) {
		saved_gl = gl;
	}
		
	/* Method: addHeadText()
	 * Purpose: add text to inform user of results
	 * Pre: score must be int
	 * Post: ArrayList cleared
	 */
	public void addHeadText(int score)
	{
		if(score > 0)
		{
			// Congratulate user if they win (ie. score above 0)
			textInfo.add(new Text3D(saved_gl, tr3, 2, 0f,  -3.0f,  0.9f, "Congratulations!"));
			textInfo.add(new Text3D(saved_gl, tr3, 2, 0f,  -3.0f,  0.6f, "You Won " + score + " scores."));
		}
		else // Tell the user the game is over
			textInfo.add(new Text3D(saved_gl, tr3, 2, 0f,  -3.0f,  0.8f, "GAME OVER"));
		textInfo.add(new Text3D(saved_gl, tr3, 2, 0f,  -3.0f, 0.4f, "Click to start again.")); // Inform user to click if desired to play again
	}

	/* Method: addTableItemText()
	 * Purpose: add text in table
	 * Pre: row and col must be int, text must be String
	 * Post: text in table added
	 */
	public void addTableItemText(int row, int col, String text)
	{
		// Positions
		float x = -1.2f + (float)col * 0.8f;
		float y = -2.0f;
		float z = 0.0f - (float)row * 0.2f;
		textInfo.add(new Text3D(saved_gl, tr3, 3, x,  y, z, text)); // Add to arrayList
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
			for(Text3D info : textInfo)
				info.display(gl, tr3); // Display text
		}
		catch (Exception e) {
			e.printStackTrace(); // Print error if any
		}
	}
}