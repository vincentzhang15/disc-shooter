package frisbee.objects;

import java.io.InputStream;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/* Class: Playground
 * Purpose: model the playground (background)
 */
public class Playground extends BaseObject
{
    private int textureId = 0;
	
	/* Method: display()
	 * Purpose: show what the background look like
	 * Pre: drawable must be GLAutoDrawable
	 * Post: draw coordinates of the background
	 */
	public void display(GLAutoDrawable drawable)
	{
		// [Code Private - Contact to View]
		
		// Values for background
			// Draw images
	}	
	
	/* Method: init()
	 * Purpose: opens the background image
	 * Pre: drawable must be GLAutoDrawable
	 * Post: background image opened
	 */	
    public void init(GLAutoDrawable drawable) {
		// [Code Private - Contact to View]
	}
}