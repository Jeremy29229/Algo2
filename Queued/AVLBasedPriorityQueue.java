
public class AVLBasedPriorityQueue<T extends Comparable<T>> implements Queueable<T>
{
	private TreeNode<T> root;
	
	public AVLBasedPriorityQueue()
	{
		root = new TreeNode<T>();
	}
	
	@Override
	public boolean offer(T data)
	{
		boolean isDataPlaced = false;
		boolean isSearching = true;
		TreeNode<T> currentNode = root;
			
		while(isSearching)
		{
			if(currentNode.value == null)
			{
				currentNode.value = data;
				isSearching = true;
				isDataPlaced = true;
			}
			else if(data.compareTo(currentNode.value) < 0)
			{
				if(currentNode.left == null)
				{
					currentNode.left = new TreeNode<T>(data);
				}
				else
				{
					currentNode = currentNode.left;
				}
			}
			else if(data.compareTo(currentNode.value) > 0)
			{
				if(currentNode.right == null)
				{
					currentNode.right = new TreeNode<T>(data);
				}
				else
				{
					currentNode = currentNode.right;
				}
			}
			else
			{
				isSearching = false;	
			}
		}
		
		return isDataPlaced;
	}

	@Override
	public T peek()
	{
		TreeNode<T> currentNode = root;
		
		if(currentNode != null)
		{
			while(currentNode.right != null)
			{
				currentNode = currentNode.right;
			}
		}
		
		return currentNode.value;
	}

	@Override
	public T poll()
	{
		TreeNode<T> previousNode = null;
		TreeNode<T> currentNode = root;
		T polledValue = null;
		
		if(currentNode != null)
		{
			while(currentNode.right != null)
			{
				previousNode = currentNode;
				currentNode = currentNode.right;
			}
		
		
			polledValue = currentNode.value;
			
			if(currentNode.left == null)
			{
				if(previousNode == null)
				{
					root = null;
				}
				else
				{
					previousNode.right = null;
				}
			}
			else
			{
				if(previousNode == null)
				{
					root = root.left;
				}
				else
				{
					previousNode.right = currentNode.left;
				}
			}
		}
		
		return polledValue;
	}
}
