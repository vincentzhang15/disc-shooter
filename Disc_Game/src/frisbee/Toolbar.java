package frisbee;

import java.awt.Image;
import java.awt.Panel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;

import javax.imageio.ImageIO;

import frisbee.objects.ScoreBoard;

/* Class: DiscShooter
 * Purpose: Toolbar at the top of the frame
 */
public class Toolbar
{
	// Instantiate Object
	private CalcScore stats = new CalcScore();

	// Variables
	private boolean firstTime = true;
	public int numFrisbees;
	public int numTennisBalls;
	public int numShots;
	public int numFrisbeesHit;
	public int numBallsHit;
	public int score;
	public double accuracy;
	
	// Buttons
	private JButton settingsBtn = new JButton();
	private JButton scoreBtn = new JButton();
	private JButton exitBtn = new JButton();

	// Text Fields with Statistics
	private JTextField nDifficulty;
	private JTextField nShots;
	private JTextField nFrisbees;
	private JTextField nTennisBalls;
	private JTextField nFrisbeesHit;
	private JTextField nBallsHit;
	private JTextField nScore;
	private JTextField nAccuracy;
	
	
	/* Method: trySaveScore()
	 * Purpose: Ask for name of player if score above 0
	 * Pre: None
	 * Post: If score above 0, player score is saved, if otherwise, score not saved because score is 0
	 */
	public void trySaveScore()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: updateScoreBoard()
	 * Purpose: Call updateScoreBoard() in stats to update the score board
	 * Pre: parameter must be type ScoreBoard
	 * Post: updateScoreBoard() called
	 */
	public void updateScoreBoard(ScoreBoard scoreBoard) {
		// [Code Private - Contact to View]
	}
	
	/* Method: updateStatistics()
	 * Purpose: Change textfields with up to date statistics
	 * Pre: parameters must be type int
	 * Post: Textfields updated
	 */
	public void updateStatistics(int numFrisbees, int numTennisBalls, int numShots, int numFrisbeesHit, int numBallsHit, int difficulty)
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: buttonImage()
	 * Purpose: Add images to buttons
	 * Pre: "num" must be int, "name" must be String
	 * Post: Buttons have images on them
	 */
	private void buttonImage(int num, String name)
	{
		// [Code Private - Contact to View]
	}

	/* Method: Toolbar()
	 * Purpose: Add all textfields and buttons to top of frame
	 * Pre: None
	 * Post: All textfields and buttons added to top of frame
	 */
	public Toolbar()
	{
		// [Code Private - Contact to View]
	}
}