
public class HeapBasedPriorityQueue<T extends Comparable<T>> implements Queueable<T>
{
	private ArrayList<T> heap;
	
	public HeapBasedPriorityQueue(int initialSize)
	{
		heap = new ArrayList<T>(initialSize);
	}
	
	@Override
	public boolean offer(T data)
	{	
		heap.add(data);
		heapify();

		return true;
	}
	
	private void heapify()
	{
		for(int i = heap.size() / 2 - 1; i >= 0; i--)
		{
			siftDown(i); 
		}
	}
	
	private void siftDown(int position)
	{
		if ((position < 0) || (position >= heap.size()))
		{
			throw new IllegalArgumentException("Illegal heap position passed in: " + position + "\nHeapsize: " + heap.size());
		}
		
		while (!isLeaf(position))
		{
			int j = leftChild(position);
			if ((j < (heap.size() - 1)) && (heap.get(j).compareTo(heap.get(j + 1)) > 0))
			{
				j++;
			}
				
			if (heap.get(position).compareTo(heap.get(j)) <= 0)
			{
				return;
			}
			
			heap.swap(position, j);
			position = j;
		}
	}
	
	private boolean isLeaf(int index)
	{
		return (index >= heap.size() / 2) && (index < heap.size());
	}
	
	private Integer leftChild(int index) 
	{
		if(index > heap.size() / 2)
		{
			//throw new IllegalArgumentException("Position has no left child: " + index);
			return null;
		}
		return 2 * index + 1;
	}
	
	public Integer rightChild(int index)
	{
		if(index >= heap.size() / 2)
		{
			//throw new IllegalArgumentException("Position has no right child: " + index);
			return null;
		}

		return 2 * index + 2;
	}
	
	public int parent(int index)
	{
		if(index <= 0)
		{
			throw new IllegalArgumentException("Position has no parent: " + index);
		}

		return (index - 1) / 2;
	}

	@Override
	public T peek()
	{
		return heap.get(0);
	}

	@Override
	public T poll()
	{
		T largestValue = null;
		if(heap.get(0) != null)
		{		
			int largestValueIndex = 0;
			
			for(int i = 1; i < heap.size(); i++)
			{
				if(heap.get(largestValueIndex).compareTo(heap.get(i)) < 0)
				{
					largestValueIndex = i;
				}
			}
			
			largestValue = remove(largestValueIndex);
		}
		
		return largestValue;
	}
	
	public T remove(int index)
	{
		if (index < 0 || index >= heap.size())
		{
			return null;
		}
		
		if (index == (heap.size() - 1))
		{
			
			//heap.remove(heap.get(index));
		}
		else
		{
			heap.swap(index, heap.size() - 1);
			
			while ((index > 0) && (heap.get(index).compareTo(heap.get(parent(index))) > 0))
			{
				heap.swap(index, parent(index));
				index = parent(index);
			}
			
			if (heap.size() != 0)
			{
				siftDown(index);
			}
		}
		
		T requestedValue = heap.get(heap.size() - 1);
		heap.remove(requestedValue);
		return requestedValue;
	}
	
	public int size()
	{
		return heap.size();
	}
	
	public T get(int index)
	{
		return heap.get(index);
	}
}
