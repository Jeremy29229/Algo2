import java.util.Iterator;

public class ArrayList<T> implements Iterator<T>
{
	private T[] set;
	private int maxSpace;
	private int usedSpace;
	private int currentIndex;
	
	@SuppressWarnings("unchecked")
	public ArrayList()
	{
		maxSpace = 10;
		usedSpace = 0;
		set = (T[])new Object[maxSpace];
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList(int startingMaxSpace)
	{
		maxSpace = startingMaxSpace;
		usedSpace = 0;
		set = (T[])new Object[maxSpace];
	}
	
	public Iterator<T> iterator()
	{
		currentIndex = 0;
		return this;
	}

	public boolean add(T t)
	{
		if(maxSpace == usedSpace)
		{
			enlarge();
		}
		
		set[usedSpace] = t;
		usedSpace++;
		
		return true;
	}

	public T get(int index)
	{
		return set[index];
	}

	public boolean remove(T t)
	{
		boolean isElementFound = false;
		
		for(int i = 0; i < usedSpace && !isElementFound; i++)
		{
			if(set[i] == t)
			{
				isElementFound = true;
				for(int j = i+1; j < usedSpace; j++)
				{
					set[j-1] = set[j];
				}
				set[usedSpace-1] = null;
				usedSpace--;
			}
		}
		
		return isElementFound;
	}

	public int size()
	{
		return usedSpace;
	}

	@Override
	public boolean hasNext()
	{
		return currentIndex < usedSpace;
	}

	@Override
	public T next()
	{
		return set[currentIndex++];
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Iterator does not support removal.");
	}
	
	public void swap(int indexA, int indexB)
	{
		T temp = set[indexA];
		set[indexA] = set[indexB];
		set[indexB] = temp;
	}
	
	public boolean contains(T value)
	{
		boolean doesContain = false;
		
		for(T setItem : set)
		{
			if(setItem == value)
			{
				doesContain = true;
			}
		}
		
		return doesContain;
	}
	
	private void enlarge()
	{
		@SuppressWarnings("unchecked")
		T[] newSet = (T[]) new Object[maxSpace * 2];
		
		for(int i = 0; i < set.length; i++)
		{
			newSet[i] = set[i];
		}
		
		maxSpace = newSet.length;
		set = newSet;
	}
}
