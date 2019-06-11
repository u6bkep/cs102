package TennisDatabase;

/**
 * a node in the linked list of players.
 * keeps track of a player and its associated matches
 * @author Ben Kepner
 *
 */
class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface
{
	
	TennisPlayer player;					//the player
	SortedLinkedList<TennisMatch> matches;	//a list of all the matches this player is in
	TennisPlayerContainerNode right;		//the right child node
	TennisPlayerContainerNode left;			//the left child node
	

	/**
	 * constructor
	 * @param player
	 */
	TennisPlayerContainerNode(TennisPlayer player)
	{
		this.player = player;
		this.left = null;
		this.right = null;
		this.matches = new SortedLinkedList<TennisMatch>();
	}
	
	/*
	 * delete the matches of a given player from the list of matches
	 */
	public void deleteMatchesOfPlayer(String playerId) throws TennisDatabaseException
	{
		TennisMatch[] playerMatches = this.getMatches();
		this.matches = new SortedLinkedList<TennisMatch>();
		for(int i = 0; i < playerMatches.length; i++)
		{
			if(playerMatches[i].idPlayer1.equals(playerId) || playerMatches[i].idPlayer2.equals(playerId))
			{
				playerMatches[i] = null;
			}
		}
		for(TennisMatch x: playerMatches)
		{
			if(x != null)
				this.insertMatch(x);
		}
	}
	

	/**
	 * Insert a match into the lists associated with the players in the match
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException
	{
		try 
		{
			// insert match into list of matches for this player
				this.matches.insert(m);
			
			//update player winn/loss record
			if((this.player.id.equals(m.idPlayer1) && m.winner == 1) ||
					(this.player.id.equals(m.idPlayer2) && m.winner == 2) )
				this.player.incWinns();
			else
				this.player.incLosses();
			
		}
		catch(TennisDatabaseException e)
		{
			throw e;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * get all matches from a player as an array
	 */
	@Override
	public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException 
	{
		TennisMatch[] playerMatches = new TennisMatch[this.matches.size()];
		
		for(int i = 0; i < this.matches.size(); i++)
		{
			playerMatches[i] = this.matches.get(i);
		}
		
		return playerMatches;
	}

	int compare(TennisPlayerContainerNode other)
	{
		return this.player.compareTo(other.player);
	}


	public TennisPlayer getPlayer()
	{
		return this.player;
	}

	public void setPlayer(TennisPlayer player)
	{
		this.player = player;
	}

	public TennisPlayerContainerNode getRight()
	{
		return this.right;
	}

	public void setRight(TennisPlayerContainerNode right)
	{
		this.right = right;
	}

	public TennisPlayerContainerNode getLeft()
	{
		return this.left;
	}

	public void setLeft(TennisPlayerContainerNode left)
	{
		this.left = left;
	}
}
