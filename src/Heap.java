import java.util.Hashtable;

//Toto je klasicky implementovany min heap
public class Heap {

	private int HEAP_SIZE = 2000000;
	
	private int size = 1;
	private State[] heapStates;
	private Hashtable<String, Boolean> hashtable = new Hashtable<>();
	
	public Heap()
	{
		heapStates = new State[HEAP_SIZE];
	}
	
	//Vlozenie prvku do min heapu. Najmensi prvok je na vrchu.
	public void insert(State state,Solver solver)
	{
		//Aby som sa nevracal do stavov, kde som uz bol tak si navstivene stavy ukladam ako stringy v hash mape
		if(hashtable.containsKey(solver.getHash(state.getState()))) 
		{
			return;
		}
		
		addToHashTable(solver.getHash(state.getState()));
		
		heapStates[size] = state;
		size++;
		solver.incrementCount();
		
		int iterator = size-1;
		
		while(iterator > 1)
		{
			if(heapStates[iterator/2].getPriority() > heapStates[iterator].getPriority())
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
	
	//Pridanie stavu do hash many
	public void addToHashTable(String state)
	{
		hashtable.put(state, true);
	}
	
	//Vybratie min prvku z haldy
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
				min = heapStates[iterator].getPriority();
			}
			else
			{
				break;
			}
			
			if(checkPosition(iterator+1))
			{
				if(min > heapStates[iterator+1].getPriority())
				{
					iterator++;
					min = heapStates[iterator].getPriority();
				}
			}

			if(min < heapStates[parent].getPriority())
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
	
	//Overenie, ci nejdeme mimo alokovaneho pristoru
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

}
