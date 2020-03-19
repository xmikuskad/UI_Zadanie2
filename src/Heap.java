import java.util.Hashtable;

public class Heap {

	private int size = 1;
	private State[] heapStates;
	private Hashtable<String, Boolean> hashtable = new Hashtable<>();
	
	public Heap()
	{
		heapStates = new State[1000000];
	}
	
	public void insert(State state)
	{
		if(hashtable.containsKey(state.getState()))
			return;
				
		addToHashTable(state.getState());
		heapStates[size] = state;
		size++;
		
		int iterator = size-1;
		
		while(iterator > 1)
		{
			if(heapStates[iterator/2].getDoneCount() > heapStates[iterator].getDoneCount())
			{
				State tmpState = heapStates[iterator/2];
				heapStates[iterator/2] = heapStates[iterator];
				heapStates[iterator] = tmpState;
				iterator = iterator/2;
			}
			else 
			{
				break;
			}
		}
	}
	
	public void addToHashTable(String state)
	{
		hashtable.put(state, true);
	}
	
	public State getMin()
	{
		if(size <=0) return null;		
		
		State returnState = heapStates[1];
		
		//Heapify
		heapStates[1] = heapStates[size-1];
		heapStates[size-1] = null;
		size--;
		
		int iterator = 1;
		int parent = 1;
		int min;
		
		while (true)
		{			
			iterator = parent*2;
			if(checkPosition(iterator))
			{
				min = heapStates[iterator].getDoneCount();
			}
			else
			{
				break;
			}
			
			if(checkPosition(iterator+1))
			{
				if(min > heapStates[iterator+1].getDoneCount())
				{
					iterator++;
					min = heapStates[iterator].getDoneCount();
				}
			}

			if(min < heapStates[parent].getDoneCount())
			{
				State tmpState = heapStates[parent];
				heapStates[parent] = heapStates[iterator];
				heapStates[iterator]=tmpState;
				parent = iterator;
			}	
			else 
			{
				break;
			}
			
			
		}
		
		return returnState;
	}
	
	private boolean checkPosition(int position)
	{
		if(position < size)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public int heapSize() {	return size; }

}
