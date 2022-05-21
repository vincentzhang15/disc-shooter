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
		// Reference: Different ways of Reading a text file in Java (https://www.geeksforgeeks.org/different-ways-reading-text-file-java/)
		records.clear(); // Clear any existing records
        try (BufferedReader br = Files.newBufferedReader(Paths.get("score.txt"))) // Try to open file
		{
            String line;
            while ((line = br.readLine()) != null) // Loop through all the lines in the file
			{
				String [] fields = line.split(","); // Store one line of the file in an array where commas in the file indicates incremented index for the array
				if(fields.length >= 8) // If there are less than 8 statistics, it is not the statistics to be put on the statistics table
				{
					// Create a temporary record to store the data for one row
					PlayRecord r = new PlayRecord();
					r.name = fields[0];
					r.time = fields[1];
					r.numShots = Integer.parseInt(fields[2]);
					r.numFrisbees = Integer.parseInt(fields[3]);
					r.numTennisBalls = Integer.parseInt(fields[4]);
					r.frisbeesHit = Integer.parseInt(fields[5]);
					r.ballsHit = Integer.parseInt(fields[6]);
					r.score = Integer.parseInt(fields[7]);
					records.add(r); // Add the temporary record to the ArrayList of records for storing
				}
            }
			br.close(); // Close BufferedReader
        }
		catch (IOException e) {
            System.out.println("No statistics file yet."); // Catch error if there is no file
        }
	}
	
	/* Method: read()
	 * Purpose: read data from file and store it for the statistics table
	 * Pre: none
	 * Post: data from file read, converted and stored in 2D array rows
	 */
	public void read()
	{
		formatData(); // Custom format data from file to an ArrayList
		if(records.size() > 0) // If the file containes any data
		{
			data.clear(); // Clear all previous stored data in the 2D ArrayList
			for(PlayRecord r : records) // For every row of statistics
			{
				// Add values to a temporary ArrayList
				ArrayList<String> nData = new ArrayList<String>();
				nData.add(r.name);
				nData.add(r.time);
				nData.add(""+r.numShots);
				nData.add(""+r.numFrisbees);
				nData.add(""+r.numTennisBalls);
				nData.add(""+r.frisbeesHit);
				nData.add(""+r.ballsHit);
				nData.add(""+r.score);
				data.add(nData); // Add the ArrayList of data to an ArrayList creating a 2D ArrayList of statistics
			}

			// Reference: Convert ArrayList into 2D array containing varying lengths of arrays (https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays)
			rows = new String[data.size()][];
			for (int i = 0; i < data.size(); i++)
			{
				ArrayList<String> temp = data.get(i);
				rows[i] = temp.toArray(new String[temp.size()]); // Convert data stored ArrayList to array to put on the statistics table
			}
		}
	}
	
	/* Method: write()
	 * Purpose: write up-to-date statistics to "score.txt"
	 * Pre: none
	 * Post: data written to "score.txt"
	 */
	public void write(String name) // "name" is the player name to be stored
	{
		read(); // Make sure all the data is up-to-date before storing any new data
		{
			PlayRecord r = new PlayRecord(); // Temporary record of all data
			r.name = name.length() > 0 ? name : "Anonymous"; // IF the user does not input a name, their name is Anonymous
			r.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()); // Reference: Java – How to get current date time (https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/)

			DiscShooter.frame.canvas.getRecord(r); // Canvas gets the record containing up-to-date statistics for processing
			records.add(r); // Add new statistics: name and time
			Collections.sort(records); // Sort records by score
		}

		// Reference: How do I create a file and write to it in Java? (https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java)
		try
		{
			PrintWriter writer = new PrintWriter("score.txt", "UTF-8"); // Write to score.txt with charset "UTF-8"
			for(PlayRecord r : records) // Loop through all the current records
			{
				// Write the statistics
				writer.print(""+r.name);
				writer.print(","+r.time);
				writer.print(","+r.numShots);
				writer.print(","+r.numFrisbees);
				writer.print(","+r.numTennisBalls);
				writer.print(","+r.frisbeesHit);
				writer.print(","+r.ballsHit);
				writer.print(","+r.score);
				writer.println();
			}
			writer.close();
		}
		catch(Exception e) {
			System.out.println(e); // Print any exeptions that may be caused
		}
	}
	
	/* Method: updateScoreBoard()
	 * Purpose: update items to be shown at the end of a game on the score board
	 * Pre: none
	 * Post: items in the end of game score board updated
	 */
	public void updateScoreBoard(ScoreBoard scoreBoard, int score)
	{
		read(); // Make sure all the data is up-to-date before storing any new data
		
		scoreBoard.clear(); // Call clear method to clear ArrayList storing data local to ScoreBoard
		scoreBoard.addHeadText(score); // Call method to report to the user (Congratulations/Game over) and inform/instruct (You won x score/Click to start again.)
		
		// Add statistics to table of score board where perameters represent: row, col, text
		scoreBoard.addTableItemText(0,0, "Name");
		scoreBoard.addTableItemText(0,1, "Hits");
		scoreBoard.addTableItemText(0,2, "Hit rate");
		scoreBoard.addTableItemText(0,3, "Destroy rate");
		scoreBoard.addTableItemText(0,4, "Score");

		int i=0; // Act as counter
		for(PlayRecord r : records)
		{
			i++;
			int hits = r.frisbeesHit + r.ballsHit; // Total number of objects hit
			int hit_rate = (r.frisbeesHit + r.ballsHit) *100 / (r.numShots); // Percentage of objects hit over number of shots
			int destroy_rate = (r.frisbeesHit + r.ballsHit) *100 / (r.numFrisbees + r.numTennisBalls); // How much percentage of the objects that come out are destroyed
			scoreBoard.addTableItemText(i,0, r.name); // Add name of player
			scoreBoard.addTableItemText(i,1, "" + hits); // Add number of hits
			scoreBoard.addTableItemText(i,2, "" + hit_rate + "%"); // Add hit rate
			scoreBoard.addTableItemText(i,3, "" + destroy_rate + "%"); // Add destroy rate
			scoreBoard.addTableItemText(i,4, "" + r.score); // Add score
			
			if(i>=5) break; // Display only the top 5 on the score board
		}
	}
}