public class Graph
{
	private int[][] matrix; //for tracking edges
	private int[] marks; //for coloring vertices
	
	public Graph(int v)
	{
		matrix = new int[v][v];
		marks = new int [v];
	}
	
	public int vcount()
	{
		return marks.length;
	}
	
	public int first (int v)
	{
		for(int i = 0; i < vcount(); i++)
		{
			if(matrix[v][i] != 0)
			{
				return i;
			}
		}
		
		return vcount();
	}
	
	public int next(int vertex, int lastVisitedNeighbor)
	{
		for(int i = lastVisitedNeighbor + 1; i < vcount(); i++)
		{
			if(matrix[vertex][i] != 0)
			{
				return i;
			}
		}
		return vcount();
	}
	
	public void addEdge(int vertex, int neighbor, int weight)
	{
		if(weight == 0)
		{
			throw new IllegalArgumentException("Edge weight cannot be 0"); 
		}
		
		matrix[vertex][neighbor] = weight;
	}

	public void removeEdge(int vertex, int neighbor)
	{
		matrix[vertex][neighbor] = 0;
	}

	public boolean isEdge(int vertex, int neighbor)
	{
		return matrix[vertex][neighbor] != 0;
	}
	
	public int getMark(int vertex)
	{
		return marks[vertex];
	}

	public void setMark(int vertex, int value)
	{
		marks[vertex] = value;
	}

	public int ecount()
	{
		int edgeCount = 0;
		for(int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j < matrix[i].length; j++)
			{
				if(isEdge(i, j))
				{
					edgeCount++;
				}
			}
		}
		
		return edgeCount;
	}

	public int degree(int v)
	{
		int edgesFromV = 0;
		
		int currentNeighbor = first(v);
		
		while(currentNeighbor != vcount())
		{
			edgesFromV++;
			currentNeighbor = next(v, currentNeighbor);
		}
		
		return edgesFromV;
	}
}
