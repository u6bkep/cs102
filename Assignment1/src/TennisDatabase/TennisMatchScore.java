package TennisDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores the scores for a tennis match
 * @author Ben Kepenr
 */
class TennisMatchScore 
{
	
	int[] player1Scores;
	int[] player2Scores;
	int winner;
	
	
	/**
	 * constructor
	 * @param s
	 */
	TennisMatchScore(String s)
	{
		
		
		//parse score string
		ArrayList<String> stringList = new ArrayList<String>();
		Collections.addAll(stringList, s.split(","));
		
		this.player1Scores = new int[stringList.size()];
		this.player2Scores = new int[stringList.size()];
		
		ArrayList<int[]> scores = parseScore(stringList);
		
		for(int i = 0; i < scores.size(); i++)
		{
			this.player1Scores[i] = scores.get(i)[0];
			this.player2Scores[i] = scores.get(i)[1];
		}
		
		//update winner
		
		int player1Total = 0;
		int player2Total = 0;
		
		for(int i = 0; i < this.player1Scores.length; i++)
		{
			player1Total += this.player1Scores[i];
			player2Total += this.player2Scores[i];
		}
		
		if(player1Total > player2Total)
			this.winner = 1;
		else if(player1Total < player2Total)
			this.winner = 2;
		else if(player1Total == player2Total)
			throw new TennisDatabaseRuntimeException("match score is a tie");
		
	}
	
	/**
	 * obsolete: recursively parses the match score string for storage
	 * @param s
	 * @return an ArrayList of all of the match scores 
	 */
	private ArrayList<int[]> parseScore(ArrayList<String> s)
	{
		ArrayList<int[]> matchScores = new ArrayList<int[]>();
		String thisScore = "";
		
		
		if(s.size() > 1)
		// Continue recursively
		{
			thisScore = s.remove(0);
			matchScores = parseScore(s);
		}
		else
		{
			//return
	
		String[] split = thisScore.split("-");
		
		matchScores.add(new int[] {Integer.parseInt(split[0]),Integer.parseInt(split[1])});
		
		
		}
		
		return null;
		
	}
	
	/**
	 * tests if two score sets are equal
	 */
	public boolean equals(TennisMatchScore o)
	{
		if(this.player1Scores.equals(o.player1Scores) && this.player2Scores.equals(o.player2Scores))
			return true;
		else
			return false;
	}
	
	/**
	 * converts the relevant information to a string
	 */
	public String toString()
	{
		
		String returnString = "";
		
		for(int i=0; i < (this.player1Scores.length - 1); i++)
		{
			returnString += this.player1Scores[i] + "-" + this.player2Scores[i] + ",";
		}
		
		returnString += this.player1Scores[this.player1Scores.length - 1] + "-" + this.player2Scores[this.player1Scores.length - 1];
		
		return(returnString);
	}
	/*
	 * returns the number of the winning player
	 */
	public int getWinner()
	{
		return this.winner;
	}

}
