package TennisDatabase;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * TennisMatchContainer:
 * stores tennis matches in an array list
 * sorted by date
 * @author Ben Kepner
 */
class TennisMatchContainer implements TennisMatchContainerInterface
{
	
	private ArrayList<TennisMatch> matches; //internal array of matches
	
	
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
		this.matches = new ArrayList<TennisMatch>();
		this.matches.add(m);
	}

	@Override
	public int getNumMatches() {
		
		return this.matches.size();
	}

	@Override
	public Iterator<TennisMatch> iterator() 
	{
		return this.matches.iterator();
	}

	/**
	 * insert a match into the array
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException 
	{
		if(this.matches == null)
		{
			this.matches = new ArrayList<TennisMatch>();
		}

		this.matches.add(m);
		this.matches.sort(TennisMatch.reverseCompareto);

	}

	/**
	 * get all matches
	 */
	@Override
	public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException 
	{
		TennisMatch[] matches = new TennisMatch[0];
		try
		{
			matches = this.matches.toArray(matches);
		}
		catch(NullPointerException e)
		{
			return null;
		}
		return matches;
	}

	/**
	 * get all matches for a player
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException 
	{
		ArrayList<TennisMatch> matchesOfPlayer = new ArrayList<TennisMatch>();
		
		for(int i = 0; i < this.matches.size(); i++)
		{
			if(playerId.equals(this.matches.get(i).getIdPlayer1()) || playerId.equals(this.matches.get(i).getIdPlayer2()))
			{
				matchesOfPlayer.add(this.matches.get(i));
			}
		}
		
		return (TennisMatch[]) matchesOfPlayer.toArray();
	}

	// delete all of the matches for a given player
	@Override
	public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException 
	{
		ArrayList<TennisMatch> matchesOfPlayer = new ArrayList<TennisMatch>();
		
		for(int i = 0; i < this.matches.size(); i++)
		{
			if(playerId.equals(this.matches.get(i).getIdPlayer1()) || playerId.equals(this.matches.get(i).getIdPlayer2()))
			{
				matchesOfPlayer.add(this.matches.get(i));
			}
		}
		
		this.matches.removeAll(matchesOfPlayer);
		
	}
}
