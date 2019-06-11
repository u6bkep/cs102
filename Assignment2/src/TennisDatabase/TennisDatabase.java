package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
	TennisPlayerContainer tpc; //database of players
	TennisMatchContainer tmc; //database of matches
	
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
	 * @param fileName to read data from
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
			//System.out.println("successfully opened file " + file);
			
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
		
			String textLine = fileScanner.next().trim();
			String [] splitLine = textLine.split("/");
			
			try
			{
				
			
			
			
				switch(splitLine[0])
				{
				case "PLAYER":
					//parse player lines
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
					//parse match lines
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
				throw new TennisDatabaseRuntimeException("error in input file:\n" + e.getMessage());
			}
		}
		
		fileScanner.close();
	}

	/*
	 * save database to a file in the same format as importing
	 */
	@Override
	public void saveToFile(String fileName) throws TennisDatabaseException 
	{
		//generate the data to save
		String fileString = "";
		for(TennisPlayer x: this.tpc)
		{
			fileString += "PLAYER/" + x.id + "/" + x.firstName +
					"/" + x.lastName + "/" + x.getBirthYear() +
					"/" + x.country + "\n";
		}
		for(TennisMatch x: this.tmc.getAllMatches())
		{
			fileString += "MATCH/" + x.idPlayer1 +
					"/" + x.idPlayer2 + "/" + Integer.toString(x.getDateYear()) +
					Integer.toString(x.getDateMonth()) + Integer.toString(x.getDateDay()) + 
					"/" + x.location + "/" + x.score + "\n";
		}
		
		//save the data to a file
		FileWriter file;
		try 
		{
			file = new FileWriter(fileName);
		} 
		catch (IOException e) 
		{
			throw new TennisDatabaseException("file io error:\n" + e.getMessage());
		}
		
		try {
			file.write(fileString);
		} catch (IOException e) {
			throw new TennisDatabaseException("file io error:\n" + e.getMessage());
		}
		finally
		{
			try {
				file.close();
			} catch (IOException e) {
				throw new TennisDatabaseException("file io error:\n" + e.getMessage());
			}
		}
	}

	/*
	 * reset the database, deleting all data
	 */
	@Override
	public void reset()
	{
		this.tpc = new TennisPlayerContainer();
		this.tmc = new TennisMatchContainer();
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
		TennisPlayer.verifyIdString(playerId);
		return this.tpc.getMatchesOfPlayer(playerId);
	}

	/**
	 * get all matches
	 * @return TennisMatch[] array of all matches
	 */
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException 
	{
		if(tmc == null)
			throw new TennisDatabaseRuntimeException("no matches");
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

	/*
	 * delete a player and all of their matches, even matches held by other players
	 */
	@Override
	public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException
	{
		TennisPlayer.verifyIdString(playerId);
		this.tpc.deletePlayer(playerId);
		this.tmc.deleteMatchesOfPlayer(playerId);
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
