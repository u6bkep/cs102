package TennisDatabase;

/**
 * a node in the linked list of players.
 * keeps track of a player and its associated matches
 * @author Ben Kepner
 *
 */
public class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface
{
	
	TennisPlayer item;
	SortedLinkedList<TennisMatch> matches;
	TennisPlayerContainerNode next;
	TennisPlayerContainerNode previous;
	

	/**
	 * constructor
	 * @param item
	 */
	TennisPlayerContainerNode(TennisPlayer item)
	{
		this.item = item;
		this.previous = this;
		this.next = this;
		this.matches = new SortedLinkedList<TennisMatch>();
	}
	
	/**
	 * set the next node in the list
	 */
	@Override
	public void setNext(TennisPlayerContainerNode o)
	{
		this.next = o;
	}

	/**
	 * gets the player associated with this node
	 */
	@Override
	public TennisPlayer getPlayer() 
	{
		return this.item;
	}

	/**
	 * gets the previous node in the list
	 */
	@Override
	public TennisPlayerContainerNode getPrev() 
	{
		return this.previous;
	}

	/**
	 * gets the next node in the list
	 */
	@Override
	public TennisPlayerContainerNode getNext() 
	{
		return this.next;
	}

	/**
	 * sets the previous node in the list
	 */
	@Override
	public void setPrev(TennisPlayerContainerNode p) 
	{
		this.previous = p;
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
			if((this.item.id.equals(m.idPlayer1) && m.winner == 1) || 
					(this.item.id.equals(m.idPlayer2) && m.winner == 2) )
				this.item.incWinns();
			else
				this.item.incLosses();
			
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
	
	
}
