package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * TennisDatabase class:
 * manages storing and accessing tennis players and matches
 * 
 * @author Ben  Kepner
 */
public class TennisDatabase implements TennisDatabaseInterface
{
	TennisPlayerContainer tpc;
	TennisMatchContainer tmc;
	
	/**
	 * Default constructor:
	 */
	public TennisDatabase()
	{
		tpc = new TennisPlayerContainer();
		tmc = new TennisMatchContainer();
	}

	/**
	 * loadFromFile method:
	 * reads match and player data from a file
	 * and stores it
	 * @param fielName to read data from
	 */
	@Override
	public void loadFromFile(String fileName) throws TennisDatabaseException, 
	TennisDatabaseRuntimeException 
	{
		
		
		Scanner fileScanner;
		
		
		// open input file and create a scanner object for it
		
		try
		{
			File file = new File(fileName);
			fileScanner = new Scanner(file);
			fileScanner.useDelimiter("\n");
			System.out.println("successfully opened file " + file);
			
		}
		
		catch(FileNotFoundException exception)
		{
			System.out.println(exception.getMessage());
			return;
		}
		
		catch(Exception exception)
		{
			System.out.println(exception.toString());
			System.out.println("please specify a file name as the first argument");
			return;
		}
		
	
		//read through the file and parse out the players and matches
		
		while(fileScanner.hasNext())
		{
		
			String textLine = fileScanner.next().strip();
			String [] splitLine = textLine.split("/");
			
			try
			{
				
			
			
			
				switch(splitLine[0])
				{
				case "PLAYER":
					
					Pattern pattern = Pattern.compile("^PLAYER\\/[A-Z]{3}[0-9]{2}\\/\\w+\\/\\w+\\/[0-9]{4}\\/\\w+$");
					if (!pattern.matcher(textLine).matches()) 
					{
					       throw new TennisDatabaseRuntimeException("Invalid String");
					}
					try {
						this.insertPlayer(splitLine[1],
											splitLine[2],
											splitLine[3],
											Integer.parseInt(splitLine[4]),
											splitLine[5]);
						
					} catch (Exception e) {
						throw new TennisDatabaseException("error in parsing player " + 
						splitLine[1] + "\n" + e.getStackTrace());
					}
					break;
				case "MATCH":
					
					Pattern pattern2 = Pattern.compile("^MATCH\\/[A-Z]{3}[0-9]{2}\\/[A-Z]{3}[0-9]{2}\\/[0-9]{8}\\/.+\\/([0-9]+-[0-9]+,|[0-9]+-[0-9]+)+$");
					if (!pattern2.matcher(textLine).matches()) 
					{
					       throw new TennisDatabaseRuntimeException("Invalid String");
					}
					
					
					try {
						this.insertMatch(splitLine[1], 
										splitLine[2], 
										LocalDate.parse(splitLine[3], DateTimeFormatter.ofPattern("yyyyMMdd")), 
										splitLine[4], 
										splitLine[5]);
					} 
					catch (TennisDatabaseException e) 
					{
						throw new TennisDatabaseException("error in parsing match " + 
								splitLine[1] + "/" + splitLine[2] + " " + splitLine[3] +
								"\n" + e.getStackTrace());
					}
				
					break;
				}
			}
			catch(TennisDatabaseRuntimeException e)
			{
				System.err.println("error in input file, exiting.");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		fileScanner.close();
	}

	
	
	/**
	 * returns a specified player
	 * @param id The id of the player to return
	 */
	@Override
	public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException 
	{
		return this.tpc.getPlayer(id);
	}

	/**
	 * gets all players
	 * @return TennisPlayer[] an array of all of the players
	 */
	@Override
	public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException 
	{
		return this.tpc.getAllPlayers();
	}

	/**
	 * gets all matches for a specified player
	 * @param playerId the id of the player
	 * @return TennisMatch[] array of all matches for that player
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId)
			throws TennisDatabaseException, TennisDatabaseRuntimeException 
	{
		return this.tpc.getMatchesOfPlayer(playerId);
	}

	/**
	 * get all matches
	 * @return TennisMatch[] array of all matches
	 */
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException 
	{
		return this.tmc.getAllMatches();
	}

	/**
	 * insert a player into the database
	 */
	@Override
	public void insertPlayer(String id, String firstName, String lastName, int year, String country)
			throws TennisDatabaseException 
	{
		TennisPlayer.verifyIdString(id);
		
		tpc.insertPlayer(new TennisPlayer(id,
				firstName,
				lastName,
				LocalDate.of(year, 1, 1),
				country));
		
	}

	/**
	 * insert a match into the database.
	 * both players must exist
	 */
	@Override
	public void insertMatch(String idPlayer1, String idPlayer2, int year, int month, int day, 
			String tournament, String score) throws TennisDatabaseException 
	{
		this.insertMatch(idPlayer1, 
				idPlayer2, 
				LocalDate.of(year, month, day), 
				tournament, 
				score );
	}
	
	/**
	 * insert a match into the database.
	 * both players must exist
	 */
	public void insertMatch(String idPlayer1, String idPlayer2, LocalDate date, String tournament,
			String score) throws TennisDatabaseException
	{
		TennisMatch newMatch = new TennisMatch(idPlayer1, 
				idPlayer2, 
				date, 
				tournament, 
				score );
		newMatch.namePlayer1 = (this.tpc.getPlayer(idPlayer1).firstName.charAt(0) + "." + tpc.getPlayer(idPlayer1).lastName);
		newMatch.namePlayer2 = (this.tpc.getPlayer(idPlayer2).firstName.charAt(0) + "." + tpc.getPlayer(idPlayer2).lastName);
		tpc.getPlayer(idPlayer1);
		tpc.getPlayer(idPlayer2);
		tmc.insertMatch(newMatch);
		tpc.insertMatch(newMatch);
	}
		

}
