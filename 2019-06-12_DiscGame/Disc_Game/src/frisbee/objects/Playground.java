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
	// Reference: JOGL - Quick Guide (https://www.tutorialspoint.com/jogl/jogl_quick_guide.htm)
    private int textureId = 0;
	
	/* Method: display()
	 * Purpose: show what the background look like
	 * Pre: drawable must be GLAutoDrawable
	 * Post: draw coordinates of the background
	 */
	public void display(GLAutoDrawable drawable)
	{
	    GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_TEXTURE_2D); // "Interface to C language function: void glBindTexture(GLenum target, GLuint texture);;"
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId); // "Interface to C language function: void glEnable(GLenum cap);"
		
		// Values for background
		float x = 30f;
		float y = 33f;
		float z = 18f;
		
		gl.glNormal3f(0f, -1f, 0f); // Set normal
		gl.glColor3f(1f, 1f, 1f); // Set colour

		for(int i=0;i<25;i++) // Draw 25 times for best user experience in variant screens
		{
			x = 120f * i - 60f * 25f - 30f;

			// Draw images
			gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2d(1.0, 0.0); gl.glVertex3d(x,     y, -z);
			gl.glTexCoord2d(1.0, 1.0); gl.glVertex3d(x,     y,  z);
			gl.glTexCoord2d(0.0, 1.0); gl.glVertex3d(x+60f, y,  z);
			gl.glTexCoord2d(0.0, 0.0); gl.glVertex3d(x+60f, y, -z);
			gl.glEnd();
			
			gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2d(0.0, 0.0); gl.glVertex3d(x+60f,  y, -z);
			gl.glTexCoord2d(0.0, 1.0); gl.glVertex3d(x+60f,  y,  z);
			gl.glTexCoord2d(1.0, 1.0); gl.glVertex3d(x+120f, y,  z);
			gl.glTexCoord2d(1.0, 0.0); gl.glVertex3d(x+120f, y, -z);
			gl.glEnd();
		}
		
        gl.glDisable(GL2.GL_TEXTURE_2D);
	}	
	
	/* Method: init()
	 * Purpose: opens the background image
	 * Pre: drawable must be GLAutoDrawable
	 * Post: background image opened
	 */	
    public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); // "Returns the GL pipeline object this GLAutoDrawable uses."
		
		try {
			// Reference: Class TextureIO (https://jogamp.org/deployment/v2.0.2/javadoc/jogl/javadoc/com/jogamp/opengl/util/texture/TextureIO.html)
			InputStream i = getClass().getResourceAsStream("/images/playground.jpg"); // Get background image
			Texture t = TextureIO.newTexture(i,true, "jpg"); // "Creates an OpenGL texture object from the specified stream using the current OpenGL context."
			textureId = t.getTextureObject(gl); // "Returns the underlying OpenGL texture object for this texture."
		}
		catch(IOException e) {
			e.printStackTrace(); // Print error if there were any
		}
	}
}