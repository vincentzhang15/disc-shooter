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
		if(score > 0) // Ask for name of player only if scores above 0
		{
			// Reference: How do I make a popup window with just a textfield? (https://stackoverflow.com/questions/17180023/how-do-i-make-a-popup-window-with-just-a-textfield)
			JPanel panel = new JPanel();
			JTextField name = new JTextField(20); // TextField with size 20

			panel.add(new JLabel("Game Over! Name: ")); // Ask for player name
			panel.add(name);

			JOptionPane.showMessageDialog(null, panel); // Show popup window
			stats.write(name.getText().toString()); // Add call write() in stats to associate name with current statistics
		}
	}
	
	/* Method: updateScoreBoard()
	 * Purpose: Call updateScoreBoard() in stats to update the score board
	 * Pre: parameter must be type ScoreBoard
	 * Post: updateScoreBoard() called
	 */
	public void updateScoreBoard(ScoreBoard scoreBoard) {
		stats.updateScoreBoard(scoreBoard, score);
	}
	
	/* Method: updateStatistics()
	 * Purpose: Change textfields with up to date statistics
	 * Pre: parameters must be type int
	 * Post: Textfields updated
	 */
	public void updateStatistics(int numFrisbees, int numTennisBalls, int numShots, int numFrisbeesHit, int numBallsHit, int difficulty)
	{
		accuracy = (Math.round((numFrisbeesHit + numBallsHit)/((numShots==0)?(1):((double)numShots))*10000.0))/100.0; // Accuracy is the number of objects hit over number of shots rounded to 2 decimal places, if number of shots is 0 set to 1 because 0/0 is NaN
		score = numFrisbeesHit*2 + numBallsHit*5; // Frisbees are worth 2 points, balls are worth 5 points if hit
		
		// Change difficulty level text
		switch(difficulty)
		{
			case 1: nDifficulty.setText("Easy"); break;
			case 2: nDifficulty.setText("Medium"); break;
			case 3: nDifficulty.setText("Hard"); break;
			case 4: nDifficulty.setText("Insane"); break;
			case 5: nDifficulty.setText("Nightmare"); break;
		}

		nShots.setText("" + numShots); // Update number of shots
		nFrisbees.setText("" + numFrisbees); // Update number of frisbees
		nTennisBalls.setText("" + numTennisBalls); // Update number of tennis balls
		nFrisbeesHit.setText("" + numFrisbeesHit); // Update number of frisbees shots
		nBallsHit.setText("" + numBallsHit); // Update number of tennis balls shots
		nScore.setText("" + score); // Update score
		nAccuracy.setText("" + accuracy + "%"); // Update accuracy

		if(firstTime)
		{
			firstTime = false; // Pack only once
			DiscShooter.frame.pack(); // Make sure all frame content is at or above preferred size
		}
	}
	
	/* Method: buttonImage()
	 * Purpose: Add images to buttons
	 * Pre: "num" must be int, "name" must be String
	 * Post: Buttons have images on them
	 */
	private void buttonImage(int num, String name)
	{
		try {
			Image original_img = ImageIO.read(getClass().getResource("/images/" + name)); // Reference: Java: using an image as a button (https://stackoverflow.com/questions/4898584/java-using-an-image-as-a-button)
			Image img = original_img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);    // Reference: How to resize an image in Java? (http://java-demos.blogspot.com/2013/10/how-to-resize-image-in-java.html)

			switch(num)
			{
				case 0: settingsBtn.setIcon(new ImageIcon(img)); break; // Settings image button
				case 1: scoreBtn.setIcon(new ImageIcon(img)); break; // Statistics image button
				case 2: exitBtn.setIcon(new ImageIcon(img)); break; // Exit image button
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/* Method: Toolbar()
	 * Purpose: Add all textfields and buttons to top of frame
	 * Pre: None
	 * Post: All textfields and buttons added to top of frame
	 */
	public Toolbar()
	{
		Panel dashboard = new Panel(); // Toolbar on top
		dashboard.setLayout(new FlowLayout()); // Toolbar labout is FlowLayout()

		// Settings popup window
		settingsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				
				JLabel objectSpeed = new JLabel("Object Speed"); // Object speed label
				JRadioButton slow = new JRadioButton("Slow"); // Setting 1: slow
				JRadioButton medium = new JRadioButton("Medium"); // Setting 2: medium
				JRadioButton fast = new JRadioButton("Fast"); // Setting 3: fast
				
				// Add label and buttons
				panel.add(objectSpeed);
				panel.add(slow);
				panel.add(medium);
				panel.add(fast);
				
				JOptionPane.showMessageDialog(null, panel); // Show popup window
				
				if(slow.isSelected()) // Change object speed to slow
				{
					Canvas.tBallSpeed = 0.002;
					Canvas.frisbeeSpeed = 0.002;
				}
				else if(fast.isSelected()) // Change object speed to fast
				{
					Canvas.tBallSpeed = 2;
					Canvas.frisbeeSpeed = 2;
				}
				else // Change object speed to medium/default
				{
					Canvas.tBallSpeed = 0.02;
					Canvas.frisbeeSpeed = 0.02;
				}
			}
		});
			
		// Statistics popup window
		scoreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stats.read(); // Read statistics from file
				if(stats.rows== null) // If there no statistics yet
				{
					JOptionPane.showMessageDialog(null, "There is no score for now. Do you want to be the first player?");
					return;
				}
				
				// Instantiations of Objects
				JTable table = new JTable(stats.rows, stats.cols); // Reference: How to format a table in JOptionpane? (https://stackoverflow.com/questions/27832268/how-to-format-a-table-in-joptionpane)
				JScrollPane scrollpane = new JScrollPane(table); // For adding table
				
				// Reference: Setting column width and row height for JTable (https://www.codejava.net/java-se/swing/setting-column-width-and-row-height-for-jtable)
				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(0).setPreferredWidth(100); // Custom column width for name
				columnModel.getColumn(1).setPreferredWidth(150); // Custom column width for time

				scrollpane.setPreferredSize(new Dimension(680, 300)); // Store table
				JOptionPane.showMessageDialog(null, scrollpane); // Show table via popup message
			}
		});

		// Exit program button
		exitBtn.addActionListener(new ActionListener() {
			// Reference: A JButton listener example (https://alvinalexander.com/java/jbutton-listener-pressed-actionlistener)
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		buttonImage(0, "Settings.png"); // Reference: Image (http://www.iconarchive.com/show/android-lollipop-icons-by-dtafalonso/Settings-icon.html)
		buttonImage(1, "Statistics.png"); // Reference: Image (https://pngtree.com/free-icon/iconmain-statistics_570414)
		buttonImage(2, "Exit.png"); // Reference: Image (http://www.iconarchive.com/show/sleek-xp-basic-icons-by-hopstarter/Close-2-icon.html)

		settingsBtn.setToolTipText("Settings");  // Reference: A JButton tooltip example (https://alvinalexander.com/blog/post/jfc-swing/how-set-help-tooltip-balloon-text-jbutton)
		scoreBtn.setToolTipText("Statistics");
		exitBtn.setToolTipText("Exit Program");

		// Difficulty textfields
		JTextField labelDifficulty = new JTextField("Difficulty:"); // Set text in textfield
		labelDifficulty.setToolTipText("Difficulty"); // Show description of box upon hover
		labelDifficulty.setEditable(false); // Deactivate editing of textfield
		nDifficulty = new JTextField(7); // Default textfield width
		nDifficulty.setEditable(false); // Deactivate editing of textfield

		// Shots textfields
		JTextField labelShots = new JTextField("Shots:");  
		labelShots.setToolTipText("Number of Shots");
		labelShots.setEditable(false);
		nShots = new JTextField(5);
		nShots.setEditable(false);

		// Frisbees textfields
		JTextField labelFrisbee = new JTextField("Frisbees:");  
		labelFrisbee.setToolTipText("Number of Frisbees");
		labelFrisbee.setEditable(false);
		nFrisbees = new JTextField(5); 
		nFrisbees.setEditable(false);

		// Tennis balls textfields
		JTextField labelTennisBalls = new JTextField("T-Balls:");  
		labelTennisBalls.setToolTipText("Number of Tennis Balls");
		labelTennisBalls.setEditable(false);
		nTennisBalls = new JTextField(5); 
		nTennisBalls.setEditable(false);
		
		// Frisbees shot textfields
		JTextField labelFrisbeesHit = new JTextField("Frisbees Shot:");  
		labelFrisbeesHit.setToolTipText("Number of Frisbees Shot");
		labelFrisbeesHit.setEditable(false);
		nFrisbeesHit = new JTextField(5);
		nFrisbeesHit.setEditable(false);

		// Tennis balls shot textfields
		JTextField labelBallsHit = new JTextField("Balls Shot:");  
		labelBallsHit.setToolTipText("Number of Tennis Balls Shot");
		labelBallsHit.setEditable(false);
		nBallsHit = new JTextField(5);
		nBallsHit.setEditable(false);

		// Score textfields
		JTextField labelScore = new JTextField("Score:");  
		labelScore.setToolTipText("Score");
		labelScore.setEditable(false);
		nScore = new JTextField(5);
		nScore.setEditable(false);

		// Accuracy textfields
		JTextField labelAccuracy= new JTextField("Accuracy:");  
		labelAccuracy.setToolTipText("Accuracy");
		labelAccuracy.setEditable(false);
		nAccuracy = new JTextField(5);
		nAccuracy.setEditable(false);
		
		// Add textfields and buttons to JPanel
		dashboard.add(labelDifficulty);
		dashboard.add(nDifficulty);
		
		dashboard.add(labelShots);
		dashboard.add(nShots);
		
		dashboard.add(labelFrisbee);
		dashboard.add(nFrisbees);
		
		dashboard.add(labelTennisBalls);
		dashboard.add(nTennisBalls);
		
		dashboard.add(labelFrisbeesHit);
		dashboard.add(nFrisbeesHit);
		
		dashboard.add(labelBallsHit);
		dashboard.add(nBallsHit);
		
		dashboard.add(labelScore);
		dashboard.add(nScore);
		
		dashboard.add(labelAccuracy);
		dashboard.add(nAccuracy);
		
		dashboard.add(settingsBtn);
		dashboard.add(scoreBtn);
		dashboard.add(exitBtn);
		
		// Add panel to top of border of frame
		DiscShooter.frame.add(dashboard, BorderLayout.NORTH); // Reference: Adding two JComponents to the North of a JFrame BorderLayout (https://stackoverflow.com/questions/40616722/adding-two-jcomponents-to-the-north-of-a-jframe-borderlayout)
	}
}