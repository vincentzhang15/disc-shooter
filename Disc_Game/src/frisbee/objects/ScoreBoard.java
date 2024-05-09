package frisbee.objects;

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
		// [Code Private - Contact to View]
	}
	
	/* Method: clear()
	 * Purpose: clear ArrayList
	 * Pre: none
	 * Post: ArrayList cleared
	 */
	public void clear() {
		// [Code Private - Contact to View]
	}

	/* Method: set_gl()
	 * Purpose: assign gl to saved_gl
	 * Pre: gl must be GL2
	 * Post: value assigned
	 */
	public void set_gl(GL2 gl) {
		// [Code Private - Contact to View]
	}
		
	/* Method: addHeadText()
	 * Purpose: add text to inform user of results
	 * Pre: score must be int
	 * Post: ArrayList cleared
	 */
	public void addHeadText(int score)
	{
		// [Code Private - Contact to View]
	}

	/* Method: addTableItemText()
	 * Purpose: add text in table
	 * Pre: row and col must be int, text must be String
	 * Post: text in table added
	 */
	public void addTableItemText(int row, int col, String text)
	{
		// [Code Private - Contact to View]
	}

	/* Method: display()
	 * Purpose: Display needs to be re-rendered. This is where all the heavy-lifting gets done.
	 * Pre: drawable must be GLAutoDrawable, scene must be int
	 * Post: text shown
	 */
	public void display(GLAutoDrawable drawable, int scene)
	{
		// [Code Private - Contact to View]
	}
}