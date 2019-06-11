package TennisDatabase;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * TennisMatchContainer:
 * stores tennis matches in a custom dynamically allocated array list
 * sorted by date
 * @author Ben Kepner
 */
public class TennisMatchContainer implements TennisMatchContainerInterface
{
	
	private TennisMatch[] matches; //internal array of matches
	
	
	/**
	 * default constructor
	 */
	public TennisMatchContainer()
	{
		this.matches = null;
	}
	
	/**
	 * constructor
	 * @param m
	 */
	public TennisMatchContainer(TennisMatch m)
	{
		this.matches = new TennisMatch[] {m};
	}

	/**
	 * insert a match into the array
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException 
	{
		if(this.matches == null)
		{
			this.matches = new TennisMatch[] {m};
		}
		
		else
		{
			for(int i=0; i < this.matches.length; i++)
			{
				if(m.compareTo(this.matches[i]) >= 1)
				{
					// insert before as soon as a larger node is found
					TennisMatch[] temp = new TennisMatch[this.matches.length +1];
					
					if(i == 0)
					{
						System.arraycopy(this.matches, 0, temp, 1, temp.length - 1);
						temp[0] = m;
					}
					else 
					{
						System.arraycopy(this.matches, 0, temp, 0, i );
						temp[i] = m;
						System.arraycopy(this.matches, i, temp, i+1, this.matches.length - i);
					}
					this.matches = temp;
					
					//then return
					return;
				}
			}
			
			//if a larger node is not found, insert at end
			this.matches = Arrays.copyOf(this.matches, this.matches.length + 1);
			this.matches[this.matches.length - 1] = m;
		}
	}

	/**
	 * get all matches
	 */
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException 
	{
		return this.matches;
	}

	/**
	 * get all matches for a player
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException 
	{
		ArrayList<TennisMatch> matchesOfPlayer = new ArrayList<TennisMatch>();
		
		for(int i = 0; i < this.matches.length; i++)
		{
			if(playerId.equals(this.matches[i].getIdPlayer1()) || playerId.equals(this.matches[i].getIdPlayer2()))
			{
				matchesOfPlayer.add(this.matches[i]);
			}
		}
		
		return (TennisMatch[]) matchesOfPlayer.toArray();
	}
}
