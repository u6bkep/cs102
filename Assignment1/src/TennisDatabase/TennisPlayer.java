package TennisDatabase;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * stores the information about one player
 * @author Ben Kepner
 *
 */
public class TennisPlayer implements TennisPlayerInterface 
{
	
	String id;
	String firstName;
	String lastName;
	LocalDate birthYear;
	String country;
	
	int wins;
	int losses;
	
	/**
	 * constructor
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthYear
	 * @param country
	 */
	public TennisPlayer(String id,
						String firstName,
						String lastName,
						LocalDate birthYear,
						String country)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.country = country;
		this.wins = 0;
		this.losses = 0;
	}
	
	/**
	 * converts the relevant information to a string
	 */
	public String toString()
	{
		return(this.id + ": " +
				this.firstName + " " +
				this.lastName + ", " +
				this.birthYear.getYear() + ", " +
				this.country + ", " +
				this.getWinLoss() + " (WIN/LOSS)");
	}
	
	/**
	 * verifies if a player id string follows the right format
	 * @param id
	 * @throws TennisDatabaseRuntimeException
	 */
	public static void verifyIdString(String id) throws TennisDatabaseRuntimeException
	{
		Pattern pattern = Pattern.compile("^[A-Z]{3}[0-9]{2}$");
		 if (!pattern.matcher(id).matches()) 
		 {
		        throw new TennisDatabaseRuntimeException("Invalid String");
		 }
	}
	
	/**
	 * increments the number of wins associated with this player
	 */
	public void incWinns()
	{
		this.wins ++;
	}
	
	/**
	 * increments the number of losses associated with this player
	 */
	public void incLosses()
	{
		this.losses ++;
	}
	
	/**
	 * @return the win loss ratio
	 */
	public String getWinLoss()
	{
		return(this.wins + "/" + this.losses);
	}

	/**
	 * compares two players by id
	 */
	@Override
	public int compareTo(TennisPlayer other) 
	{
		return other.id.compareTo(this.id);
	}

	/**
	 * gets the id of the player
	 */
	@Override
	public String getId() 
	{
		return id;
	}

	/**
	 * gets the first name of a player
	 */
	@Override
	public String getFirstName() 
	{
		return firstName;
	}

	/**
	 * gets the last name of the player
	 */
	@Override
	public String getLastName() 
	{
		return lastName;
	}

	/**
	 * gets the birth year of a player
	 */
	@Override
	public int getBirthYear() 
	{
		return birthYear.getYear();
	}

	/**
	 * gets the country the player is from
	 */
	@Override
	public String getCountry() 
	{
		return country;
	}


	
	
}
