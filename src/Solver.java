import java.util.ArrayList;
import java.util.List;

public class Solver {
	Heap heap = new Heap();
	
	public String solvePuzzle(String finalState, String currentStateInc, int height, int width)
	{		
		String currentState = currentStateInc;
		heap.addToHashTable(currentState);

		List<Integer> list = new ArrayList<>();
		
		while(!finalState.equals(currentState))
		{
			int position = currentState.indexOf("m");
			
			if(position/width != 0)
			{
				//Hore
				moveTile(1, currentState,finalState,height,width,position,list);
			}
			if(position/width != height -1)
			{
				//Dole
				moveTile(2, currentState,finalState,height,width,position,list);
			}
			if(position % width != 0)
			{
				//Dolava
				moveTile(3, currentState,finalState,height,width,position,list);
			}
			if(position % width != width-1)
			{
				//Doprava
				moveTile(4, currentState,finalState,height,width,position,list);
			}
			
			State state = heap.getMin();
			if(state == null)
			{
				System.out.println("heap is empty");
				return "NOT FOUND!";
			}
			currentState = state.getState();
			list = state.getDirection();
		}
		
		System.out.println("Number of steps: "+list.size());
		printResults(list);
		
		return currentState;
		
		
	}
	
	private void printResults(List<Integer> list)
	{
		for(int i : list)
			System.out.println(getDirectionName(i));

	}
	
	private String getDirectionName(int num)
	{
		switch (num) {
		case 1: return "hore";
		case 2: return "dole";
		case 3: return "vlavo";
		case 4: return "vpravo";
		default:
			break;
		}
		return "NOT FOUND";
	}
	
	public void moveTile(int direction, String currentState,String finalState, int height, int width,int position, List<Integer> list)
	{
		List<Integer> newList = new ArrayList<>();
		newList.addAll(list);
		
		char tmp;
		switch (direction) {
		case 1:
			tmp = currentState.charAt(position-width);	
			break;
		case 2:
			tmp = currentState.charAt(position+width);	
			break;
		case 3:
			tmp = currentState.charAt(position-1);	
			break;
		case 4:
			tmp = currentState.charAt(position+1);	
			break;

		default:
			//Nikdy nenastane
			tmp = 0;	
			break;
		}
		
		
		currentState = currentState.replace('m', 'x');
		currentState = currentState.replace(tmp, 'm');
		currentState = currentState.replace('x', tmp);			
		
		newList.add(direction);
		
		heap.insert(new State(getWrongCount(finalState, currentState), currentState, newList));
		
	}
	
	
	private int getWrongCount(String original, String current)
	{
		int count =0;
		
		for(int i=0;i<original.length();i++)
		{
			if(original.charAt(i) != current.charAt(i))
				count++;
		}
		
		return count;
	}
}
