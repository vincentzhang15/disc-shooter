/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN
 * MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR
 * ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR
 * DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE
 * DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF
 * SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed or intended for use
 * in the design, construction, operation or maintenance of any nuclear
 * facility.
 * 
 * Sun gratefully acknowledges that this software was originally authored
 * and developed by Kenneth Bradley Russell and Christopher John Kline.
 */

package frisbee.util;
//https://github.com/sgothel/jogl-demos/blob/master/src/demos/j2d/TextFlow.java

import com.jogamp.opengl.util.awt.TextRenderer;
//import demos.common.Demo;
import frisbee.util.SystemTime;
import frisbee.util.Time;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;


/** Illustrates both the TextRenderer's capability for handling
    relatively large amounts of text (more than drawn on the screen --
    showing the least recently used capabilities of its internal
    cache) as well as using the Java 2D text layout mechanisms in
    conjunction with the TextRenderer to flow text across the
    screen. */

public class TextFlow  {
		/*
  public static void main(String[] args) {
    Frame frame = new Frame("Text Flow");
    frame.setLayout(new BorderLayout());

    GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
    GLCanvas canvas = new GLCanvas(caps);
    final TextFlow demo = new TextFlow();

    canvas.addGLEventListener(demo);
    frame.add(canvas, BorderLayout.CENTER);

    frame.setSize(512, 512);
    final Animator animator = new Animator(canvas);
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          // Run this on another thread than the AWT event queue to
          // make sure the call to Animator.stop() completes before
          // exiting
          new Thread(new Runnable() {
              public void run() {
                animator.stop();
                System.exit(0);
              }
            }).start();
        }
      });
    frame.setVisible(true);
    animator.start();
  }
  */
  

  private List<String> lines = new ArrayList<String>();
  private Time time;
  private TextRenderer renderer;
  private int curParagraph;
  private float x = 30;
  private float y;
  private float velocity = 50;  // pixels/sec
  private int lineSpacing;
  private int EXTRA_LINE_SPACING = 5;

  private void reflow(float width) {
    lines.clear();
    lineSpacing = 0;
    int numLines = 0;
    FontRenderContext frc = renderer.getFontRenderContext();
    for (int i = 0; i < text.length; i++) {
      String paragraph = text[i];
      Map<TextAttribute, Font> attrs = new HashMap<TextAttribute, Font>();
      attrs.put(TextAttribute.FONT, renderer.getFont());
      AttributedString str = new AttributedString(paragraph, attrs);
      LineBreakMeasurer measurer = new LineBreakMeasurer(str.getIterator(), frc);
      int curPos = 0;
      while (measurer.getPosition() < paragraph.length()) {
        int nextPos = measurer.nextOffset(width);
        String line = paragraph.substring(curPos, nextPos);
        Rectangle2D bounds = renderer.getBounds(line);
        lines.add(line);
        lineSpacing += (int) bounds.getHeight();
        ++numLines;
        curPos = nextPos;
        measurer.setPosition(curPos);
      }
      // Indicate end of paragraph with a null LineInfo
      lines.add(null);
    }
    lineSpacing = (int) ((float) lineSpacing / (float) numLines) + EXTRA_LINE_SPACING;
  }
  
  public void init(GLAutoDrawable drawable) {
    renderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 36), true, false);
    time = new SystemTime();
    ((SystemTime) time).rebase();
  }

  public void dispose(GLAutoDrawable drawable) {
    renderer = null;
    time = null;
  }

  public void display(GLAutoDrawable drawable) {
    time.update();

    GL gl = drawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    
    float deltaT = (float) time.deltaT();
    y += velocity * deltaT;

    // Draw text starting at the specified paragraph
    int paragraph = 0;
    float curY = y;
    renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
    boolean renderedOne = false;
    for (int i = 0; i < lines.size(); i++) {
      String line = (String) lines.get(i);
      if (line == null) {
        ++paragraph;
        if (paragraph >= curParagraph) {
          // If this paragraph has scrolled off the top of the screen,
          // don't draw it the next frame
          if (paragraph > curParagraph && curY > drawable.getHeight()) {
            ++curParagraph;
            y = curY;
          }
          curY -= 2 * lineSpacing;
        }
      } else {
        if (paragraph >= curParagraph) {
          curY -= lineSpacing;
          if (curY < drawable.getHeight() + lineSpacing) {
            renderer.draw(line, (int) x, (int) curY);
            renderedOne = true;
          }
          if (curY < 0) {
            // Done rendering all visible lines
            break;
          }
        }
      }
    }
    renderer.endRendering();
    if (!renderedOne) {
      // Start over
      curParagraph = 0;
      y = 0;
    }
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    reflow(Math.max(100, width - 60));
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

  // Scrolling text at start of game
  private static final String[] text = {
		
	"A long time in the future, in a galaxy far, far away, it was a period of war against man and alien. In a planet, in a continent, in a country, in a province, in a city, in a school started the epidemic that would soon create a catastrophe to the celestial object, planet Earth.",

	"An unknown species to man appeared mysteriously in the middle of room C3 when the clocks struck thirteen. It was a bright cold day in February, a shadow appeared. It was snowing hard and school was canceled. This mysterious creature spawned along with a virus. These creatures have one goal: infect and destroy all living organisms and convert them to their own kind.",

	"Your goal is to stop the infection from the root. These aliens will spread their disease by throwing diseased frisbees and tennis balls. These objects can be quite deceptive. They come in different colors! Destroy as many of these objects as possible! You have one and only one blaster to stop the alien invasion. Easy, medium, hard, insane, nightmare modes allow the blaster to destroy everything within 10, 1, 0.5, 0.3, 0.2 radii respectively. The default difficulty is medium.",

	"Rankings are based on score. The higher the score, the higher on the rankings list. Every frisbee hit is worth 2 points and every ball hit is worth 5 points. The score is the sum of the number of points from frisbees and balls. One game ends after 20 frisbees are launched.",
	
	"The settings button allows for the frisbee and ball speed to be slow, medium, or fast. The default speed is medium. The statistics button beside the settings button shows the full rankings. The exit button beside the statistics button quits the program.",
	
	"Additionally, if there is a frisbee that is on the edge of the left and right side of the screen, the player can drag the screen to the left or right to increase the view to the left or right side of the screen, allowing for easier hit on the target.",
	
	"To play, click near the bottom of the screen to shoot at a straight view level. The higher the click, the higher the bullets will rise. Good luck. Click to start the game..."
		
};
}
