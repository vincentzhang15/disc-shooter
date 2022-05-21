/**
 * Vincent Zhang
 * June 12, 2019
 * 3D frisbee and tennis ball shooting game.
 * ICS3U7-02 Mr. Anthony
 */

package frisbee; // Reference: Java Packages (http://tutorials.jenkov.com/java/packages.html)

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/* Class: DiscShooter
 * Purpose: Main class to run program
 */
public class DiscShooter extends JFrame
{
	// Object Declarations
	public static DiscShooter frame;
	public Canvas canvas;
	public Toolbar toolbar;

	// Constants
	private static final int WINDOWWIDTH = 1280;
	private static final int WINDOWHEIGHT = 924;
	
	/* Method: run()
	 * Purpose: process program
	 * Pre: None
	 * Post: program processed
	 */	
	private void run()
	{
		// Instantiation of Objects
		canvas = new Canvas(this); // Reference: Java AWT Canvas (https://www.javatpoint.com/java-awt-canvas)
		toolbar = new Toolbar();
		
		// Initial Settings
		setSize(WINDOWWIDTH, WINDOWHEIGHT); // Set window size
		setLocationRelativeTo(null); // Center frame in screen
		setResizable(true); // Allow user to resize
		setVisible(true); // Allow frame to be visible
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // End program when close button is pressed
	}

	/* Method: DiscShooter()
	 * Purpose: Put game name on top bar
	 * Pre: None
	 * Post: Name on top bar put
	 */
	public DiscShooter() {
		super("Disc Shooter");
	}
	
	/* Method: main()
	 * Purpose: start program
	 * Pre: None
	 * Post: program started
	 */	
    public static void main(String[] args) {
		frame = new DiscShooter();
		frame.run();
    }
}