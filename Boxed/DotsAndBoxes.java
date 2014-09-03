import java.util.ArrayList;
import java.util.List;

public class DotsAndBoxes
{
	Graph g;
	int rows;
	int columns;
	ArrayList<Integer> players;
	
	public DotsAndBoxes(int rows, int columns)
	{
		g = new Graph((rows + 1) * (columns + 1));
		this.rows = rows + 1;
		this.columns = columns + 1;
		players = new ArrayList<Integer>();
		
		for(int i = 0; i < g.vcount(); i++)
		{
			if(!isEdgeVertex(i))
			{
				g.addEdge(i, i - 1, 1);
				g.addEdge(i - 1, i, 1);
				
				g.addEdge(i, i + 1, 1);
				g.addEdge(i + 1, i, 1);
				
				g.addEdge(i, i - columns - 1, 1);
				g.addEdge(i - columns - 1, i, 1);
				
				g.addEdge(i, i + columns + 1, 1);
				g.addEdge(i + columns + 1, i, 1);
			}
		}
	}
	
	int drawLine(int player, int x1, int y1, int x2, int y2)
	{
		// draws a line from (x1, y1) to (x2, y2) (0,0) is in the upper-left corner, returning how many points were earned, if any
				
		if(player >= this.players.size())
		{
			for(int i = this.players.size() - 1; i <= player; i++)
			{
				players.add(0);
			}
		}
			
		if(!isDotInBounds(x1, y1))
		{
			throw new IllegalArgumentException("Dot coordinates out of bounds: " + x1 + ", " + y1);
		}
		
		if(!isDotInBounds(x2, y2))
		{
			throw new IllegalArgumentException("Dot coordinates out of bounds: " + x2 + ", " + y2);
		}
		
		if(!areLinesAdjacent(x1, y1, x2, y2))
		{
			throw new IllegalArgumentException("Cannot draw lines between non-adjacent dots.");
		}
		
		int boxesCreated = 0;
		
		int v1 = -1;
		int v1X = -1;
		int v1Y = -1;
		
		int v2 = -1;
		int v2X = -1;
		int v2Y = -1;
		if(y1 == y2)
		{
			if(x1 > x2)
			{
				v1X = x1;
				v2X = x1;
			}
			else
			{
				v1X = x2;
				v2X = x2;
			}
			v1Y = y1;
			v2Y = y1 + 1;
		}
		else
		{
			if(y1 > y2)
			{
				v1Y = y1;
				v2Y = y1;
			}
			else
			{
				v1Y = y2;
				v2Y = y2;
			}
			v1X = x1;
			v2X = x1 + 1;
		}
		
		v1 = v1X + (v1Y * (columns));
		v2 = v2X + (v2Y * (columns));
		
		g.removeEdge(v1, v2);
		g.removeEdge(v2, v1);
		
		if(!isEdgeVertex(v1) && g.degree(v1) == 0)
		{
			boxesCreated++;
		}
		
		if(!isEdgeVertex(v2) && g.degree(v2) == 0)
		{
			boxesCreated++;
		}
		
		players.set(player, players.get(player) + boxesCreated);
		
		return boxesCreated;
	}
	
	private boolean isDotInBounds(int x, int y)
	{
		return x > -1 && x < rows -1 && y > -1 && y < columns - 1;
	}
	
	private boolean areLinesAdjacent(int x1, int y1, int x2, int y2)
	{
		boolean isAdjacent = true;
		
		if(x1 - x2 == 0)
		{
			if(y1 - y2 != -1 && y1 - y2 != 1)
			{
				isAdjacent = false;
			}
		}
		else if(x1 - x2 == -1 || x1 - x2 == 1)
		{
			if(y1 - y2 != 0)
			{
				isAdjacent = false;
			}
		}
		else
		{
			isAdjacent = false;
		}
		
		return isAdjacent;
	}
	
	int score(int player)
	{
		// returns the score for a player
		
		if(player >= this.players.size())
		{
			throw new IllegalArgumentException("Player of that index has not participated in the game: " + player); 
		}
		
		return players.get(player);
	}
	
	boolean areMovesLeft()
	{
		boolean movesLeft = false;
		
		for(int i = 0; i < g.vcount() && !movesLeft; i++)
		{
			if(!isEdgeVertex(i) && g.degree(i) > 0)
			{
				movesLeft = true;
			}
		}
		
		return movesLeft;
	}
	
	int countDoubleCrosses()
	{
		// returns the number of double-crosses on the board
		int count = 0;
		
		DfsGraphTraversal d = new DfsGraphTraversal();
		List<List<Integer>> t = d.traverse(g);
		for(int i = 0; i < t.size(); i++)
		{
			for(int j = 0; j < t.get(i).size(); j++)
			{
				int v = t.get(i).get(j);
				if(!isEdgeVertex(v) && g.degree(v) == 1)
				{
					int neighbor = g.first(v);
					if(isEdgeVertex(neighbor))
					{
						count++;
						g.setMark(v, 1);
					}
					else if(g.degree(neighbor) == 1 && g.getMark(neighbor) == 0)
					{
						count++;
						g.setMark(t.get(i).get(j), 1);
						g.setMark(g.first(t.get(i).get(j)), 1);
					}
				}
			}
		}
		
		for(int k = 0; k < g.vcount(); k++)
		{
			g.setMark(k, 0);
		}
		
		return count;
	}
	
	int countCycles()
	{
		int count = 0;
		
		DfsGraphTraversal d = new DfsGraphTraversal();
		List<List<Integer>> t = d.traverse(g);
		
		for(int i = 0; i < t.size(); i++)
		{
			boolean isCycle = true;
			int v = -1;
			for(int j = 0; j < t.get(i).size() && isCycle; j++)
			{
				v = t.get(i).get(j);
				if(g.degree(v) != 2)
				{
					isCycle = false;
				}
			}
			
			if(g.first(v) != t.get(i).get(0) && g.next(v, g.first(v)) != t.get(i).get(0))
			{
				isCycle = false;
			}
			
			if(isCycle)
			{
				count++;
			}	
		}

		return count;
	}
	int countOpenChains()
	{
		//throw new IllegalArgumentException("Not yet implememnted"); 
		return 0;
		// returns the number of open chains on the board
	}
	
	public boolean isEdgeVertex(int v)
	{
		return !(v > rows && v < (rows - 1) * columns && v % rows != 0 && (v + 1) % rows != 0);
	}

}
