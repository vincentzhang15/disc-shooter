package frisbee;

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
    @Override
    public void keyPressed(KeyEvent e) {
		// [Code Private - Contact to View]
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
	@Override
	public void mouseClicked(MouseEvent e) {
		// [Code Private - Contact to View]
		
		// Get mouse coordinates in frame
		
		// Store postions
	}
 
	/* Method: mousePressed()
	 * Purpose: detect mouse pressed to store drag value
	 * Pre: "e" must be MouseEvent
	 * Post: data stored
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// [Code Private - Contact to View]
	}
 
	/* Method: mouseReleased()
	 * Purpose: detect mouse release for screen dragging
	 * Pre: "e" must be MouseEvent
	 * Post: screen dragged
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// [Code Private - Contact to View]

		// Current mouse position
		
		// See if there is a drag
			// Rotate screen
			
			// Rotate at most by 1 degree
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
	
	/* Method: display()
	 * Purpose: draw everything in the canvas
	 * Pre: drawable must be GLAutoDrawable
	 * Post: everything in canvas drawn
	 */
	@Override
	public void display(GLAutoDrawable drawable)
	{
		// [Code Private - Contact to View]
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
		// [Code Private - Contact to View]
	}

	/* Method: reshape()
	 * Purpose: respond to changes in size of window
	 * Pre: drawable must be GLAutoDrawable, x, y, width, height must be int
	 * Post: values in window resized
	 */
	@Override
	public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height )
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: prepare_light()
	 * Purpose: lighting on objects
	 * Pre: gl must be GL2
	 * Post: light prepared
	 */
	public void prepare_light(GL2 gl)
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: addTennisBall()
	 * Purpose: add a tennis ball
	 * Pre: none
	 * Post: tennis ball added
	 */
	public void addTennisBall()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: addFrisbee()
	 * Purpose: add a frisbee
	 * Pre: gl must be GL2
	 * Post: frisbee added
	 */
	public void addFrisbee(GL2 gl)
	{
		// [Code Private - Contact to View]
	}

	/* Method: shoot()
	 * Purpose: make a shot
	 * Pre: vx, vy, vz must be double
	 * Post: shot made
	 */
	public void shoot(double vx, double vy, double vz)
	{
		// [Code Private - Contact to View]
	}

	/* Method: gameStart()
	 * Purpose: start game
	 * Pre: none
	 * Post: game started
	 */
	public void gameStart()
	{
		// [Code Private - Contact to View]
	}

	/* Method: gameStop()
	 * Purpose: stop game
	 * Pre: none
	 * Post: game stopped
	 */
	public void gameStop()
	{
		// [Code Private - Contact to View]
	}

	/* Method: getRecord()
	 * Purpose: receive statistics
	 * Pre: r must be PlayRecord
	 * Post: statistics assigned to values
	 */
	public void getRecord(PlayRecord r)
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: Canvas()
	 * Purpose: initialize frame information
	 * Pre: frame must be JFrame
	 * Post: values initialized
	 */
	public Canvas(JFrame frame)
	{
		// [Code Private - Contact to View]
	}	
}