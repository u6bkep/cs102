package TennisDatabase;


/**
 * stores tennis players in a doubly linked list
 * @author Ben Kepner
 *
 */
public class TennisPlayerContainer implements TennisPlayerContainerInterface 
{
	
	TennisPlayerContainerNode head;
	TennisPlayerContainerNode current;
	private int numberOfPlayers;

	/**
	 * default constructor
	 */
	public TennisPlayerContainer()
	{
		this.head = null;
		this.current = null;
		this.numberOfPlayers = 0;
	}
	
	
	/**
	 * @return all players in an array of tennis players
	 * @throws TennisDatabaseRuntimeException
	 */
	private TennisPlayer[] toArray() throws TennisDatabaseRuntimeException 
	{
		TennisPlayer[] allPlayers = new TennisPlayer[this.numberOfPlayers];
		this.current = this.head;
		
		for(int i = 0; i < this.numberOfPlayers; i++)
		{
			allPlayers[i] = this.current.item;
			this.current = this.current.next;
		}
		return allPlayers;
	}
	
	
	/**
	 * insert a player into the list
	 */
	@Override
	public void insertPlayer(TennisPlayer p) throws TennisDatabaseException 
	{
		if(this.head == null)
		{
			this.head = new TennisPlayerContainerNode(p);
			this.current = this.head;
			this.current.setNext(this.current);
		}
		else
		{
			this.current = new TennisPlayerContainerNode(p);
			this.head.previous.next = this.current;
			this.current.previous = this.head.previous;
			this.head.previous = this.current;
			this.current.next = this.head;
			this.current.previous = this.head.previous;
		}
		numberOfPlayers ++;
	}

	/**
	 * get all players in an array of tennis players
	 */
	@Override
	public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException 
	{
		return this.toArray();
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
		TennisPlayerContainerNode current = this.head;
		
		for(int i = 0; i < this.numberOfPlayers; i++)
		{
			if(current.item.id.equals(id))
			{
				return current;
			}
			current = current.next;
		}
		throw(new TennisDatabaseRuntimeException("player does not exits"));
		
	}

	/**
	 * get a specific player by id
	 */
	@Override
	public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException 
	{
		return findPlayerNode(id).item;
		
	}


	/**
	 * get all matches of a player
	 */
	@Override
	public TennisMatch[] getMatchesOfPlayer(String playerId)
			throws TennisDatabaseException, TennisDatabaseRuntimeException 
	{
			return this.findPlayerNode(playerId).getMatches();
	}

}
