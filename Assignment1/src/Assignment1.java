


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisMatch;


public class Assignment1 {

	public static void main(String[] args)
	{
		TennisDatabase database = new TennisDatabase();
		
		// read file contents
		
		try 
		{
			database.loadFromFile(args[0]);
		} 
		catch (TennisDatabaseRuntimeException | TennisDatabaseException e)
		{
			e.printStackTrace();
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
			System.out.println("9 --> Exit");
			System.out.print("Your choice: ");
			
			boolean gotValidinput = false;
			int userResponce = 0;
			
			// keep asking user for input until a valid option is given.
			while(!gotValidinput)
			{
				try
				{
					userResponce = Integer.parseInt(scanner.nextLine().strip());
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
				if(database.getAllMatches().length == 0)
				{
					System.out.println("no matches");
				}
				else
				{
					System.out.print("player id: ");
					
					TennisMatch[] matches = new TennisMatch[0];
					
					//get all matches of a player
					try {
						String id = scanner.nextLine().strip();
						TennisPlayer.verifyIdString(id);
						matches = database.getMatchesOfPlayer(id);
					} 
					catch (TennisDatabaseRuntimeException e1) 
					{
						System.err.println("Ann error occured while processing playerId");
						e1.printStackTrace();
					} 
					catch (TennisDatabaseException e1)
					{
						e1.printStackTrace();
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
						e.printStackTrace();
					}
				}
				System.out.println();
				
				break;
			
			//Print all tennis matches
			case 3:
				
				if(database.getAllMatches().length == 0)
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
				String id = scanner.nextLine().strip();
				
				System.out.print("first name: ");
				String firstName = scanner.nextLine().strip();

				System.out.print("last name: ");
				String lastName = scanner.nextLine().strip();
				
				System.out.print("year of birth: ");
				String year = scanner.nextLine().strip();
				
				System.out.print("country: ");
				String country = scanner.nextLine().strip();
				
				try 
				{
					database.insertPlayer(id, firstName, lastName, Integer.parseInt(year), country);
				} 
				catch(NumberFormatException e1)
				{
					System.err.println("pleas enter a number for the year.");
					e1.printStackTrace();
				}
				catch (TennisDatabaseException e) 
				{
					System.err.println("error parsing new player.");
					e.printStackTrace();
				}
				
				break;
				
			//Insert a match
			case 5:
				
				System.out.print("idPlayer1: ");
				String idPlayer1 = scanner.nextLine().strip();
				
				System.out.print("idPlayer2: ");
				String idPlayer2 = scanner.nextLine().strip();
				
				System.out.print("date (yyyymmdd): ");
				LocalDate matchYear = LocalDate.parse(scanner.nextLine().strip(), 
														DateTimeFormatter.ofPattern("yyyyMMdd"));
				
				System.out.print("location: ");
				String location = scanner.nextLine().strip();
				
				System.out.print("score (player1-player2,player1-player2): ");
				String score = scanner.nextLine().strip();
				
				try 
				{
					database.insertMatch(idPlayer1, idPlayer2, matchYear, location, score);
				} 
				catch (TennisDatabaseException | TennisDatabaseRuntimeException e) 
				{
					e.printStackTrace();
				}
				break;
					
			// exit
			case 9:
				cont = false;
				System.out.println("goodby");
				break;
			
			//user option is invalid, ask again.
			default:
				System.out.println("pleas chose an option from the list");
				break;
				
			}
		}
		//close out scanner
		scanner.close();
	}
}
