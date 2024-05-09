package frisbee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import frisbee.objects.ScoreBoard;

/* Class: CalcScore
 * Purpose: Process statistics
 */
public class CalcScore
{
	// Delcarations / initializations
	public String [] cols = {"Name", "Time", "Shots", "Frisbees", "Tennis Balls", "Frisbees Hit", "Balls Hit", "Score"};
	public String [][] rows;
	private ArrayList<PlayRecord> records = new ArrayList<PlayRecord>();
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	
	/* Method: formatData()
	 * Purpose: add all statistics in score.txt to an ArrayList to organize in a specific format
	 * Pre: none
	 * Post: data stored in ArrayList records
	 */
	private void formatData()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: read()
	 * Purpose: read data from file and store it for the statistics table
	 * Pre: none
	 * Post: data from file read, converted and stored in 2D array rows
	 */
	public void read()
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: write()
	 * Purpose: write up-to-date statistics to "score.txt"
	 * Pre: none
	 * Post: data written to "score.txt"
	 */
	public void write(String name) // "name" is the player name to be stored
	{
		// [Code Private - Contact to View]
	}
	
	/* Method: updateScoreBoard()
	 * Purpose: update items to be shown at the end of a game on the score board
	 * Pre: none
	 * Post: items in the end of game score board updated
	 */
	public void updateScoreBoard(ScoreBoard scoreBoard, int score)
	{
		// [Code Private - Contact to View]
	}
}