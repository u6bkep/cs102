


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisMatch;


public class Assignment2 {

	public static void main(String[] args)
	{
		TennisDatabase database = new TennisDatabase(); //the database
		
		// read file contents
		
		try 
		{
			database.loadFromFile(args[0]);
		} 
		catch (TennisDatabaseRuntimeException | TennisDatabaseException e)
		{
			System.out.println("An error occured while loading file:\n" + e.getMessage());
			System.exit(1);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.print("no arguments given.\nusage: Assignment2 path/to/file");
			System.exit(1);
		}
		
		Scanner scanner = new Scanner(System.in);
		
		// ui loop
		boolean cont = true;
		while(cont)
		{
			//present options to user
			
			System.out.println("CS-102 Tennis Manager-Available commands:");
			System.out.println("1 --> Print all tennis players");
			System.out.println("2 --> Print all tennis matches of a player");
			System.out.println("3 --> Print all tennis matches");
			System.out.println("4 --> Insert a player");
			System.out.println("5 --> Insert a match");
			System.out.println("6 --> reset");
			System.out.println("7 --> delete a player");
			System.out.println("8 --> import data from file");
			System.out.println("10--> save data to file");
			System.out.println("9 --> Exit");
			System.out.print("Your choice: ");
			
			boolean gotValidinput = false;
			int userResponce = 0;
			
			// keep asking user for input until a valid option is given.
			while(!gotValidinput)
			{
				try
				{
					userResponce = Integer.parseInt(scanner.nextLine().trim());
					gotValidinput = true;
				}
				catch(NumberFormatException e)
				{
					System.out.println("Pleas input a number");
					System.out.print("Your choice: ");
				}
				
			}
			
			//parse user choice
			switch(userResponce)
			{
			//Print all tennis players
			case 1:
				if(database.getAllPlayers().length == 0)
				{
					System.out.println("no players");
				}
				else 
				{
					for(TennisPlayer i:database.getAllPlayers())
						System.out.println(i.toString());
					System.out.print("\n");
				}
				break;
				
			//Print all tennis matches of a player
			case 2:
				
				if(database.getAllMatches() == null)
				{
					System.out.println("no matches");
				}
				else
				{
					System.out.print("player id: ");
					
					TennisMatch[] matches = new TennisMatch[0];
					
					//get all matches of a player
					try {
						String id = scanner.nextLine().trim();
						matches = database.getMatchesOfPlayer(id);
					} 
					catch (TennisDatabaseRuntimeException e1) 
					{
						System.out.println("An error occured while processing playerId:\n" + e1.getMessage());
					} 
					catch (TennisDatabaseException e1)
					{
						System.out.println("An error occured whileprocessing playerId\n" + e1.getMessage());
					}
					
					//print out all of the matches from the player
					try 
					{
						for(int i = 0; i < matches.length; i++)
						{
							System.out.println(matches[i].toString());
						}
					} 
					catch (TennisDatabaseRuntimeException e) 
					{
						System.out.println("An error occured while printing:\n" + e.getMessage());
					}
				}
				System.out.println();
				
				break;
			
			//Print all tennis matches
			case 3:
				
				if(database.getAllMatches() == null)
				{
					System.out.println("no matches");
				}
				
				else
				{
					for(TennisMatch i:database.getAllMatches())
						System.out.println(i.toString());
					System.out.print("\n");
				}
				break;
				
			//Insert a player
			case 4:
				System.out.print("id: ");
				String id = scanner.nextLine().trim();
				
				System.out.print("first name: ");
				String firstName = scanner.nextLine().trim();

				System.out.print("last name: ");
				String lastName = scanner.nextLine().trim();
				
				System.out.print("year of birth: ");
				String year = scanner.nextLine().trim();
				
				System.out.print("country: ");
				String country = scanner.nextLine().trim();
				
				try 
				{
					database.insertPlayer(id, firstName, lastName, Integer.parseInt(year), country);
				} 
				catch(NumberFormatException e1)
				{
					System.err.println("pleas enter a number for the year.");
				}
				catch (TennisDatabaseException e) 
				{
					System.err.println("error parsing new player:\n" + e.getMessage());
				}
				catch (TennisDatabaseRuntimeException e)
				{
					System.out.print(e.getMessage() + ": all inputs must be valid values");
				}
				
				break;
				
			//Insert a match
			case 5:
				
				System.out.print("idPlayer1: ");
				String idPlayer1 = scanner.nextLine().trim();
				
				System.out.print("idPlayer2: ");
				String idPlayer2 = scanner.nextLine().trim();
				
				System.out.print("date (yyyymmdd): ");
				LocalDate matchYear = LocalDate.parse(scanner.nextLine().trim(),
														DateTimeFormatter.ofPattern("yyyyMMdd"));
				
				System.out.print("location: ");
				String location = scanner.nextLine().trim();
				
				System.out.print("score (player1-player2,player1-player2): ");
				String score = scanner.nextLine().trim();
				
				try 
				{
					database.insertMatch(idPlayer1, idPlayer2, matchYear, location, score);
				} 
				catch (TennisDatabaseException | TennisDatabaseRuntimeException e) 
				{
					System.out.println("An error occured while processing match:\n" + e.getMessage());
				}
				break;

			// reset
			case 6:

				database.reset();
				break;

			// delete a player
			case 7:
				System.out.print("id of player to delete: ");
				String deletePlayerId = scanner.nextLine().trim();
				try
				{
					database.deletePlayer(deletePlayerId);
				}
				catch(TennisDatabaseRuntimeException e)
				{
					System.out.println(e.getMessage());
				}
				break;
				
			//import a file into the database
			case 8:
				
				System.out.print("location of file to import:");
				String fileName = scanner.nextLine().trim();
				
				try {
					database.loadFromFile(fileName);
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) 
				{
					System.out.println("An error occured while loading file:\n" + e.getMessage());
				}
				break;

			// exit
			case 9:
				cont = false;
				System.out.println("goodby");
				break;
				
				// save database to a file
			case 10:
				System.out.print("location of file to save:");
				fileName = scanner.nextLine().trim();
				try {
					database.saveToFile(fileName);
				} catch (TennisDatabaseRuntimeException | TennisDatabaseException e) 
				{
					System.out.println("An error occured while saving file:\n" + e.getMessage());
				}
				
				break;
			
			//user option is invalid, ask again.
			default:
				System.out.println("pleas chose an option from the list");
				break;
				
			}
		}
		//close out file scanner
		scanner.close();
	}
}
