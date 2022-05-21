package frisbee;

/*	References:
	JOGL library downloaded from:
	https://jogamp.org/deployment/v2.0.2/jar/
	
	vecmath-1.5.1 (which is needed by TextRenderer3D) is downloaded from:
	http://www.java2s.com/Code/Jar/v/Downloadvecmath151jar.htm
*/

//	3D Graphics with OpenGL - Basic Theory
//	https://www.ntu.edu.sg/home/ehchua/programming/opengl/cg_basicstheory.html

//	The Perspective and Orthographic Projection Matrix 
//	http://www.scratchapixel.com/lessons/3d-basic-rendering/perspective-and-orthographic-projection-matrix/projection-matrices-what-you-need-to-know-first

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JFrame;
import java.util.*;
import javax.media.opengl.*;

import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import frisbee.objects.Frisbee;
import frisbee.objects.Bullet;
import frisbee.objects.TennisBall;
import frisbee.objects.Playground;
import frisbee.objects.ReadyToPlay;
import frisbee.objects.ScoreBoard;
import frisbee.util.TextFlow;
import frisbee.util.TextRenderer3D;

/* Class: Canvas
 * Purpose: create frame
 */
public class Canvas implements GLEventListener, MouseListener, KeyListener
{
	// Variable declarations and initializations/Object instantiations
	private GLU glu = new GLU();
	private float rotation = 270;

	private long timeLastTennisBall = 0;
	private long lastTimeFrisbeeAdded = 0;
	private double canvas_width = 1280;
	private double canvas_height = 924;
	public static double tBallSpeed = 0.02;
	public static double frisbeeSpeed = 0.02;
	
	// objects
	private ArrayList<Frisbee> frisbees = new ArrayList<Frisbee>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<TennisBall> balls = new ArrayList<TennisBall>();
	private Playground playground = new Playground();
	private ScoreBoard scoreBoard;
	private ReadyToPlay readyToPlay;
	private TextFlow textflow;

	// statistics
	private int numShots = 0;
	private int numFrisbees = 0;
	private int numTennisBalls = 0;
	private int frisbeesHit = 0;
	private int ballsHit = 0;
	
	// setings
	private int scene = 1;
	private double distance_for_hit = 1;
	private int difficulty = 2;

	private boolean scoreBoard_need_to_be_updated = true;
	
	// variables for screen dragging
	private boolean dragging = false;
    private int lastDragX;
	private int lastDragY;
	private float motionIncr = 1.0f;
	private float xRot=0, yRot=0;

	private TextRenderer3D tr3;
	
	// Light constants
    private static final float[] LIGHT_POSITION = { -50.f, -50.f, 50.f, 0.f };
    private static final float[] LIGHT_AMBIENT = { 0.125f, 0.125f, 0.125f, 1.f };
    private static final float[] LIGHT_DIFFUSE = { 1.0f, 1.0f, 1.0f, 1.f };
    private static final float[] MATERIAL_SPEC = { 1.0f, 1.0f, 1.0f, 0.f };
    private static final float[] ZERO_VEC4 = { 0.0f, 0.0f, 0.0f, 0.f };

	private GLCanvas gc;

	/* Method: keyPressed()
	 * Purpose: detect key pressed to change difficulty
	 * Pre: "e" must be KeyEvent
	 * Post: difficulty changed
	 */
	// Reference: How do I use keyTyped in Java? (https://stackoverflow.com/questions/45642622/how-do-i-use-keytyped-in-java)
    @Override
    public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_1: // 1 is easy
				distance_for_hit = 10;
				difficulty = 1;
				break;
				
			case KeyEvent.VK_2: // 2 is medium
				distance_for_hit = 1;
				difficulty = 2;
				break;
				
			case KeyEvent.VK_3: // 3 is hard
				distance_for_hit = 0.5;
				difficulty = 3;
				break;
				
			case KeyEvent.VK_4: // 4 is insane
				distance_for_hit = 0.3;
				difficulty = 4;
				break;
				
			case KeyEvent.VK_5: // 5 is nightmare
				distance_for_hit = 0.2;
				difficulty = 5;
				break;
		}

		switch(e.getKeyCode())
		{
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
				DiscShooter.frame.toolbar.updateStatistics(numFrisbees, numTennisBalls, numShots, frisbeesHit, ballsHit, difficulty); // update statistics if a difficulty key is pressed
				break;
			default:
				break;
		}
    }

	/* Method: keyTyped()
	 * Purpose: must be included because key listner is implemented
	 * Pre: "e" must be KeyEvent
	 * Post: none
	 */
	@Override public void keyTyped(KeyEvent e) {}

	/* Method: keyReleased()
	 * Purpose: must be included because key listner is implemented
	 * Pre: "e" must be KeyEvent
	 * Post: none
	 */
	@Override public void keyReleased(KeyEvent e) {}

	/* Method: mouseClicked()
	 * Purpose: change scenes/store information when mouse is clicked
	 * Pre: "e" must be MouseEvent
	 * Post: scene changed/information stored
	 */
	// Reference: Implementation of interface MouseListener (https://examples.javacodegeeks.com/desktop-java/swing/jframe/create-jframe-window-with-mouse-event-handling/)
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(scene) // Scenes are starting textflow, "are you ready", action, "game over"
		{
			case 1: scene = 2; 
					shoot(0, 200, 5);
					return;
			case 2:		
			case 4: gameStart();
					return;
		}
		
		// Get mouse coordinates in frame
		int x = e.getX();
		int y = e.getY();
		
		// Store postions
		double dx = (2.0 * (double)x - canvas_width )/canvas_width;
		double dz = (double) (canvas_height - y) / canvas_height;
		
		double vx = dx * 30;
		double vz = dz * 30;
		double vy = 20;

		shoot(vx, vy, vz);
	}
 
	/* Method: mousePressed()
	 * Purpose: detect mouse pressed to store drag value
	 * Pre: "e" must be MouseEvent
	 * Post: data stored
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		lastDragX = e.getX();
		lastDragY = e.getY();
	}
 
	/* Method: mouseReleased()
	 * Purpose: detect mouse release for screen dragging
	 * Pre: "e" must be MouseEvent
	 * Post: screen dragged
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Current mouse position
		int x = e.getX();
		int y = e.getY();
		
		// See if there is a drag
		if(x!=lastDragX)
		{
			// Rotate screen
            xRot -= (x - lastDragX) * motionIncr;
            yRot += (y - lastDragY) * motionIncr;
            lastDragX = e.getX();
            lastDragY = e.getY();
			
			// Rotate at most by 1 degree
			if(xRot > 1)
				xRot = 1;
			if(xRot < -1)
				xRot = -1;
		}
	}

	/* Method: mouseEntered()
	 * Purpose: must be included because mouse listner is implemented
	 * Pre: "e" must be MouseEvent
	 * Post: none
	 */
	@Override public void mouseEntered(MouseEvent e) {}

	/* Method: mouseExited()
	 * Purpose: must be included because mouse listner is implemented
	 * Pre: "e" must be MouseEvent
	 * Post: none
	 */
	@Override public void mouseExited(MouseEvent e) {}
	
	/*
	Implementation of interface GLEventListener:
		display(GLAutoDrawable drawable)
		dispose(GLAutoDrawable drawable)
		init(GLAutoDrawable drawable)
		reshape(GLAutoDrawable drawable)
	Reference: JOGL 3D Triangle (https://www.javatpoint.com/jogl-3d-triangle)
	*/
	/* Method: display()
	 * Purpose: draw everything in the canvas
	 * Pre: drawable must be GLAutoDrawable
	 * Post: everything in canvas drawn
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{
		switch(scene)
		{
			case 1: textflow.display(drawable); return; // Story/instruction text flowing screen
		}

		final GL2 gl = drawable.getGL().getGL2();

		gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
		gl.glLoadIdentity();

		gl.glTranslatef( -0.5f, 0, -6.0f );
		gl.glRotatef( xRot, 0.0f, 1.0f, 0.0f );
		gl.glRotatef( rotation, 1.0f, 0.0f, 0.0f );

		if(scene == 2) // Ready to play scene
			readyToPlay.display(drawable, scene); 
		else if(scene == 4) // Scoreboard scene
		{
			if(scoreBoard_need_to_be_updated)
			{
				scoreBoard_need_to_be_updated = false;
				scoreBoard.set_gl(gl);
				
				DiscShooter.frame.toolbar.updateStatistics(numFrisbees, numTennisBalls, numShots, frisbeesHit, ballsHit, difficulty);
				DiscShooter.frame.toolbar.updateScoreBoard(scoreBoard);
			}
			scoreBoard.display(drawable, scene); 
		}
		playground.display(drawable);

		// Collision detection
		Iterator<Bullet> itb = bullets.iterator();  // Reference: Java ArrayList Tutorial with Examples (https://www.callicoder.com/java-arraylist/)
        while (itb.hasNext()) {
            Bullet b = itb.next();
			
			if(b.state > b.HITTED)
				continue;

			// check hit frisbees
			Iterator<Frisbee> itF = frisbees.iterator();
			itF.forEachRemaining( f -> { 
				if(f.isLive())
				{
					double d = f.distance(b);
					if(d < distance_for_hit)
					{
						frisbeesHit++;
						b.startExplosion();
						f.startExplosion();
					}
				}
			});			
			
			// check hit tennis balls
			ListIterator<TennisBall> itTB = balls.listIterator(balls.size());
			while (itTB.hasPrevious()) {
				TennisBall tb = itTB.previous();
				if(tb.isLive())
				{
					double d = tb.distance(b);
					if(d < distance_for_hit)
					{
						ballsHit++;
						b.startExplosion();
						tb.startExplosion();
					}
				}
			}			
        }
		
		// remove objects hit or fly out of sight
		for(int i = balls.size(); i>0; i--)  
			if(balls.get(i-1).needRemove())
				balls.remove(i-1);
			
		for(int i = frisbees.size(); i>0; i--)  
			if(frisbees.get(i-1).needRemove())
				frisbees.remove(i-1);
			
		for(int i = bullets.size(); i>0; i--)  
			if(bullets.get(i-1).needRemove())
				bullets.remove(i-1);
		

		// draw moving objects
		for(Frisbee f : frisbees) // Reference: How to loop ArrayList in Java (https://beginnersbook.com/2013/12/how-to-loop-arraylist-in-java/)
			f.display(gl);
		
		for(int i = 0; i < balls.size(); i++)  // Java ArrayList Tutorial with Examples (https://www.callicoder.com/java-arraylist/)
			balls.get(i).display(gl);
		
		bullets.forEach( b -> b.display(gl) );  // ArrayList forEach() method in Java (https://www.geeksforgeeks.org/arraylist-foreach-method-in-java/)
		gl.glFlush();
		
		if(scene == 3) // Play scene
		{
			addTennisBall();
			addFrisbee(gl);
		}
	}

	/* Method: dispose()
	 * Purpose: must be included because implemented
	 * Pre: drawable must be GLAutoDrawable
	 * Post: none
	 */
	@Override public void dispose( GLAutoDrawable drawable ) {}

	/* Method: init()
	 * Purpose: initialize values
	 * Pre: drawable must be GLAutoDrawable
	 * Post: values initialized
	 */
	@Override
	public void init( GLAutoDrawable drawable ) 
	{
		// Instantiation/initializations
		tr3 = new TextRenderer3D(new Font("Times New Roman", Font.TRUETYPE_FONT, 3), 0.25f); // set word settings
		
		textflow = new TextFlow(); // scrolling text
		textflow.init(drawable);
		
		readyToPlay = new ReadyToPlay(); // Ready to play text
		readyToPlay.init(drawable, tr3);

		scoreBoard = new ScoreBoard() ; // Final score board
		scoreBoard.init(drawable, tr3);
		
		playground.init(drawable); // Start playground
		
		final GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL.GL_DEPTH_TEST);
	}

	/* Method: reshape()
	 * Purpose: respond to changes in size of window
	 * Pre: drawable must be GLAutoDrawable, x, y, width, height must be int
	 * Post: values in window resized
	 */
	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
	{
		switch(scene)
		{
			case 1: textflow.reshape(drawable, x, y, width, height); return; // Scene 1, scrolling text
		}
		
		// Set values
		canvas_height = height;
		canvas_width = width;
		
		final GL2 gl = drawable.getGL().getGL2();
		
		prepare_light(gl); // lighting

		// Adjust size of window
		gl.glViewport( 0, 0, width, height );
		gl.glMatrixMode( GL2.GL_PROJECTION );
		gl.glLoadIdentity();
		float aspect = (height > 0) ? ((float)width / (float)height) : 1.0f;
		glu.gluPerspective( 45.0f, aspect, 1.0, 100.0 );
		gl.glMatrixMode( GL2.GL_MODELVIEW );
		gl.glLoadIdentity();
	}
	
	/* Method: prepare_light()
	 * Purpose: lighting on objects
	 * Pre: gl must be GL2
	 * Post: light prepared
	 */
	public void prepare_light(GL2 gl)
	{
		// Reference for prepare_light: Cube Object (https://github.com/sgothel/jogl-demos/blob/master/src/demos/cubefbo/CubeObject.java)
		gl.glMatrixMode(GL2ES1.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glLightfv(GL2ES1.GL_LIGHT0, GL2ES1.GL_POSITION, LIGHT_POSITION, 0);
		gl.glLightfv(GL2ES1.GL_LIGHT0, GL2ES1.GL_AMBIENT, LIGHT_AMBIENT, 0);
		gl.glLightfv(GL2ES1.GL_LIGHT0, GL2ES1.GL_DIFFUSE, LIGHT_DIFFUSE, 0);
		gl.glLightfv(GL2ES1.GL_LIGHT0, GL2ES1.GL_SPECULAR, ZERO_VEC4, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2ES1.GL_SPECULAR, MATERIAL_SPEC, 0);
		
		// Reference: glEnable (https://www.khronos.org/registry/OpenGL-Refpages/es1.1/xhtml/glEnable.xml)
        gl.glEnable(GL2ES1.GL_NORMALIZE); // If enabled, normal vectors are normalized to unit length after transformation and before lighting.

		gl.glEnable(GL2ES1.GL_LIGHTING);
		gl.glEnable(GL2ES1.GL_LIGHT0);
		gl.glEnable(GL2ES1.GL_COLOR_MATERIAL);

		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_FASTEST);

		// Reference: Shade Model (https://www.khronos.org/registry/OpenGL-Refpages/gl2.1/xhtml/glShadeModel.xml)
        gl.glShadeModel(GL2ES1.GL_SMOOTH); // select flat or smooth shading; specifies a symbolic value representing a shading technique. Accepted values are GL_FLAT and GL_SMOOTH. The initial value is GL_SMOOTH.
		
		// Reference: Dither (https://webstyleguide.com/wsg2/graphics/dither.html)
        gl.glDisable(GL.GL_DITHER);  // If enabled, dither color components before they are written to the color buffer.
									 // Dithering is the most common means of reducing the color range of images down to the 256 (or fewer) colors seen in 8-bit GIF images. 
									 // Dithering is the process of juxtaposing pixels of two colors to create the illusion that a third color is present.
	
		gl.glEnableClientState(GL2ES1.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2ES1.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL2ES1.GL_COLOR_ARRAY);
	}
	
	/* Method: addTennisBall()
	 * Purpose: add a tennis ball
	 * Pre: none
	 * Post: tennis ball added
	 */
	public void addTennisBall()
	{
		long time = System.currentTimeMillis(); // current time
		
		double rt = Math.random() * 22500 + 2500; // rate of spawning of balls
		
		if(time - timeLastTennisBall < rt)
			return;
		timeLastTennisBall = time;
		
		// Set ball position and direction and speed
		double r1 = Math.random();
		double r2 = Math.random();
		double r3 = Math.random()*20-10;
		double r4 = Math.random()*10;
		double r5 = Math.random()*10-5;
		
		TennisBall ball = new TennisBall();
		ball.setPosition(r1*3-1.5, 18, r2*3+0.5);
		ball.setVelocity( r3 * tBallSpeed , -r4 * tBallSpeed, r5 * tBallSpeed * 0.5 );

		balls.add(ball); // Add a ball
		numTennisBalls ++;
		if(DiscShooter.frame.toolbar != null)
			DiscShooter.frame.toolbar.updateStatistics(numFrisbees, numTennisBalls, numShots, frisbeesHit, ballsHit, difficulty); // Update that new ball is launched
	}
	
	/* Method: addFrisbee()
	 * Purpose: add a frisbee
	 * Pre: gl must be GL2
	 * Post: frisbee added
	 */
	public void addFrisbee(GL2 gl)
	{
		long time = System.currentTimeMillis(); // current time
		
		double rt = Math.random() * 12500 + 2500; // rate of spawning of frisbees
		
		if(time - lastTimeFrisbeeAdded < rt)
			return;
		
		if(numFrisbees  >= 20) // One game ends when at least 20 balls are spawned
		{
			gameStop();
			return;
		}
		numFrisbees ++;
		
		lastTimeFrisbeeAdded = time;
		
		// Position, direction and speed of frisbee set
		double r1 = Math.random();
		double r2 = Math.random();
		
		double r3 = Math.random() * 100 - 50;
		double r4 = Math.random() * 100;
		double r5 = Math.random() * 100 - 50;
		
		Frisbee frisbee = new Frisbee();
		frisbee.setPosition(r1, 18, r2+1);
		frisbee.setVelocity( r3 * frisbeeSpeed , -r4 * frisbeeSpeed, r5 * frisbeeSpeed * 0.5 );

		frisbees.add(frisbee); // add new frisbee
		
		if(DiscShooter.frame.toolbar != null)
			DiscShooter.frame.toolbar.updateStatistics(numFrisbees, numTennisBalls, numShots, frisbeesHit, ballsHit, difficulty); // Update statistics
	}

	/* Method: shoot()
	 * Purpose: make a shot
	 * Pre: vx, vy, vz must be double
	 * Post: shot made
	 */
	public void shoot(double vx, double vy, double vz)
	{
		numShots++; // counter for shots
		Bullet bullet = new Bullet();
		bullet.setPosition(1, 0, -2); // Set position
		bullet.setVelocity(vx, vy, vz); // Set speed
		bullets.add(bullet); // Add bullet
		DiscShooter.frame.toolbar.updateStatistics(numFrisbees, numTennisBalls, numShots, frisbeesHit, ballsHit, difficulty); // Update statistics
	}

	/* Method: gameStart()
	 * Purpose: start game
	 * Pre: none
	 * Post: game started
	 */
	public void gameStart()
	{
		// Reset all values
		frisbees.clear();
		balls.clear();
		bullets.clear();
		numShots = 0;
		numFrisbees = 0;
		numTennisBalls = 0;
		frisbeesHit = 0;
		ballsHit = 0;
		scene = 3;
	}

	/* Method: gameStop()
	 * Purpose: stop game
	 * Pre: none
	 * Post: game stopped
	 */
	public void gameStop()
	{
		// save scoreboard
		DiscShooter.frame.toolbar.trySaveScore();
		scoreBoard_need_to_be_updated = true;
		scene = 4;
	}

	/* Method: getRecord()
	 * Purpose: receive statistics
	 * Pre: r must be PlayRecord
	 * Post: statistics assigned to values
	 */
	public void getRecord(PlayRecord r)
	{
		r.numShots = numShots;
		r.numFrisbees = numFrisbees;
		r.numTennisBalls = numTennisBalls;
		r.frisbeesHit = frisbeesHit;
		r.ballsHit = ballsHit;
		r.score = frisbeesHit*2 + ballsHit*5; // frisbees are 2 points, balls are 5 points
	}
	
	/* Method: Canvas()
	 * Purpose: initialize frame information
	 * Pre: frame must be JFrame
	 * Post: values initialized
	 */
	public Canvas(JFrame frame)
	{
		final GLProfile gp = GLProfile.get( GLProfile.GL2 );
		GLCapabilities cap = new GLCapabilities(gp );
       	gc = new GLCanvas( cap ); // Derived from java.awt.canvas
		
		// Add listners
		gc.addGLEventListener(this); 
		gc.addKeyListener(this); 
		gc.addMouseListener(this); // Reference: https://github.com/sgothel/jogl-demos/blob/master/src/demos/cubefbo/Main.java
		
		// Set layout data
		gc.setSize( 400, 400 );
		
		frame.add(gc, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(500,400);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		final FPSAnimator animator = new FPSAnimator(gc,400,true); // Reference: https://www.javatpoint.com/jogl-3d-triangle
		animator.start();
	}	
}