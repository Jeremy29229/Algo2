import java.util.ArrayList;
import java.util.List;


public class DfsGraphTraversal implements Traversal
{
	public final int UNVISITED = 0;
	public final int VISITED = 1;
	List<List<Integer>> search;
	
	@Override
	public List<List<Integer>> traverse(Graph g)
	{
		search = new ArrayList<List<Integer>>();
		
		for(int v = 0; v < g.vcount(); v++)
		{
			g.setMark(v, UNVISITED);
		}
		
		for(int v = 0; v < g.vcount(); v++)
		{
			if(g.getMark(v) == UNVISITED)
			{
				search.add(new ArrayList<Integer>());
				traverse(g, v);
			}
		}
		
		for(int i = 0; i < g.vcount(); i++)
		{
			g.setMark(i, 0);
		}
		
		return search;
	}
	
	public void traverse(Graph g, int vertex)
	{
		preVisit(g, vertex);
		g.setMark(vertex, VISITED);
		
		for(int w = g.first(vertex); w < g.vcount(); w = g.next(vertex, w))
		{
			if(g.getMark(w) == UNVISITED)
			{
				traverse(g, w);
			}
		}
		
		postVisit(g, vertex);
	}
	
	public void preVisit(Graph g, int vertex){}
	
	public void postVisit(Graph g, int vertex)
	{
		search.get(search.size() - 1).add(vertex);
	}
}
