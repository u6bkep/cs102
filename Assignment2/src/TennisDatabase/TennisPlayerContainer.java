package TennisDatabase;

/**
 * stores tennis players in a doubly linked list
 * @author Ben Kepner
 *
 */
public class TennisPlayerContainer implements TennisPlayerContainerInterface, Iterable<TennisPlayer>
{
	
	TennisPlayerContainerNode root;
	TennisPlayerContainerNode current;
	private int numberOfPlayers;

	/**
	 * default constructor
	 */
	public TennisPlayerContainer()
	{
		this.root = null;
		this.current = null;
		this.numberOfPlayers = 0;
	}
	
	/**
	 * insert a player into the list
	 */
	@Override
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException 
	{
		this.insertAtRoot(this.root, new TennisPlayerContainerNode(p));
		this.numberOfPlayers++;
	}
	
	// insert a player node at a given root
	private void insertAtRoot(TennisPlayerContainerNode root, TennisPlayerContainerNode p) throws TennisDatabaseException
	{
		if(root == null)
		{
			this.root = p;
			
		}
		else
		{
			TennisPlayerContainerNode newNode = p;
			TennisPlayerContainerNode current = root;
			boolean inserted = false;
			while(!inserted)
			{
				switch(current.compare(newNode))
				{
					case -1:
						if(current.left == null)
						{
							current.left = newNode;
							inserted = true;
						}
						else
							current = current.left;
						break;

					case 0:
						throw new TennisDatabaseException("no duplicate player allowed");

					case 1:
						if(current.right == null)
						{
							current.right = newNode;
							inserted = true;
						}
						else
						current = current.right;
						break;
						
					default:
						throw new TennisDatabaseException("should not have reached this point, bug in TennisPLayerContainerNode compare");
				}
			}
		}
	}

	/**
	 * get all players in an array of tennis players
	 */
	public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException
	{
		TennisPlayer[] players = new TennisPlayer[this.numberOfPlayers];
		int i = 0;
		for(TennisPlayer x: this)
		{
			players[i] = x;
			i++;
		}

		return players;
	}

	/**
	 * add a match to the list of matches associated with both the players in the match
	 */
	@Override
	public void insertMatch(TennisMatch m) throws TennisDatabaseException, TennisDatabaseRuntimeException
	{
		TennisPlayerContainerNode current = findPlayerNode(m.idPlayer1);
		if(current != null)
		{
			current.insertMatch(m);
		}
		
		current = findPlayerNode(m.idPlayer2);
		if(current != null)
		{
			current.insertMatch(m);
		}
	}
	
	/**
	 * @param id
	 * @return the node the player is associated with
	 * @throws TennisDatabaseRuntimeException
	 */
	public TennisPlayerContainerNode findPlayerNode(String id) throws TennisDatabaseRuntimeException
	{
		TennisPlayerContainerNode current = this.root;
		boolean found = false;
		while(!found)
		{
			if(current == null)
				return null;
			
			switch (current.player.compareId(id))
			{
				case -1:
					current = current.left;
					break;

				case 0:
					found = true;
					break;

				case 1:
					current = current.right;
					break;
					
				default:
					throw new TennisDatabaseRuntimeException("should never reach here...");
			}
		}
		return(current);
	}

	/**
	 * get a specific player by id
	 */
	@Override
	public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException 
	{
		TennisPlayerContainerNode player = findPlayerNode(id);
		if(player == null)
			throw new TennisDatabaseRuntimeException("error while searching for player by id:\n"
					+ "player not found");
		
		else 
			return player.player;
		
	}


	/**
	 * get all matches of a player
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId)
			throws TennisDatabaseException, TennisDatabaseRuntimeException 
	{
		TennisPlayerContainerNode player = this.findPlayerNode(playerId);
		if(player == null)
			throw new TennisDatabaseRuntimeException("player does not exsist");
		return player.getMatches();
	}


	@Override
	public int getNumPlayers() {
		return(this.numberOfPlayers);
	}

	@Override
	public TennisPlayerContainerIterator iterator() {
		return new TennisPlayerContainerIterator(this.root);
	}


	@Override
	public void deletePlayer(String playerId) throws TennisDatabaseRuntimeException 
	{
		if(this.numberOfPlayers == 0)
			throw new TennisDatabaseRuntimeException("no players");
		
		//first find the node of the player to delete
		TennisPlayerContainerNode current = this.root;
		TennisPlayerContainerNode previous = current;
		boolean previousDirection = false;
		boolean found = false;
		
		while(!found)
		{
			if(current == null)
				found = true;
			
			switch (current.player.compareId(playerId))
			{
				case -1:
					previous = current;
					current = current.left;
					previousDirection = false;
					break;

				case 0:
					found = true;
					break;

				case 1:
					previous = current;
					current = current.right;
					previousDirection = true;
					break;
					
				default:
					throw new TennisDatabaseRuntimeException("should never reach here...");
			}
		}
		
		if(current == null)
			throw new TennisDatabaseRuntimeException("player does not exsist");
		// the node has been found, delete it from the tree
		else
		{
			if(previous == current)
			{
				// target found at root node
				previous = current.right;
				current = current.left;
				
			}
			else if(previousDirection)
				previous.right = null;
			else
				previous.left = null;
			
			try
			{
				this.insertAtRoot(previous, current);
			}
			catch(Exception e)
			{
				throw new TennisDatabaseRuntimeException("error deleting player");
			}
		}
		this.numberOfPlayers--;
		
		//now that the player is gone, delete it's matches from the other players
		for(TennisPlayer x: this)
		{
			try {
				this.findPlayerNode(x.id).deleteMatchesOfPlayer(playerId);
			} catch (TennisDatabaseException e) {
				throw new TennisDatabaseRuntimeException("error while deleting matches of player while deleting player.");
			}
		}
	}



}
