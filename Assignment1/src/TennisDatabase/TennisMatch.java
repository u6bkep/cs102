package TennisDatabase;

import java.time.LocalDate;


/**
 * stores information about one tennis match
 * @author Ben Kepner
 */
public class TennisMatch implements TennisMatchInterface {

	String idPlayer1;
	String idPlayer2;
	String namePlayer1;
	String namePlayer2;
	
	LocalDate Date;
	String location;
	String score;
	int winner;
	
	
	
	/**
	 * constructor
	 * @param idPlayer1
	 * @param idPlayer2
	 * @param Date
	 * @param location
	 * @param score
	 */
	TennisMatch(String idPlayer1, String idPlayer2, LocalDate Date, String location, String score)
	{
		this.idPlayer1 = idPlayer1;
		this.idPlayer2 = idPlayer2;
		this.Date = Date;
		this.location = location;
		this.score = score;
		
		this.winner = TennisMatchInterface.processMatchScore(score);
	}
	
	/**
	 * compares the dates of two tennis matches
	 */
	@Override
	
	public int compareTo(TennisMatch o) 
	{
		return this.Date.compareTo(o.Date);
	}
	
	/**
	 * converts the relevents data to a String
	 */
	public String toString()
	{
		String returnString = "";
		returnString += this.Date.toString() + ", " + 
				this.namePlayer1 + " - " + 
				this.namePlayer2 + ", " +
				this.location + ", " +
				this.score.toString();
		
		return returnString;
	}

	/**
	 * get player 1 id
	 */
	@Override
	public String getIdPlayer1() 
	{
		return this.idPlayer1;
	}

	/**
	 *get player 2 id
	 */
	@Override
	public String getIdPlayer2() 
	{
		return this.idPlayer2;
	}

	/**
	 * get the year the match took place
	 */
	@Override
	public int getDateYear() 
	{
		return this.Date.getYear();
	}

	/**
	 * get the month the match took place
	 */
	@Override
	public int getDateMonth() 
	{
		return this.Date.getMonthValue();
	}

	/**
	 * get the day the match took place
	 */
	@Override
	public int getDateDay() 
	{
		return this.Date.getDayOfMonth();
	}

	/**
	 * get the location of the match
	 */
	@Override
	public String getTournament() 
	{
		return this.location;
	}

	/**
	 * get the scores for the match
	 */
	@Override
	public String getMatchScore() 
	{
		return this.score.toString();
	}

	/*
	 * returns the number of the winning player
	 */
	@Override
	public int getWinner() 
	{
		return this.winner;
	}

}
