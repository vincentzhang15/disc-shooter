package frisbee;

/* Class: PlayRecord
 * Purpose: To store all variables needed in the statistics
 */
class PlayRecord implements Comparable<PlayRecord>
{
	// Variable declarations/initializations
	public String name = "Anonymous";
	public String time;
	public int numShots = 0;
	public int numFrisbees = 0;
	public int numTennisBalls = 0;
	public int frisbeesHit = 0;
	public int ballsHit = 0;
	public int score = 0;
	
	/* Method: compareTo()
	 * Purpose: compare values to sort
	 * Pre: "o" must be PlayRecord
	 * Post: values compared for sort
	 */
    @Override
	public int compareTo(PlayRecord o) {
		// [Code Private - Contact to View]
	}
}