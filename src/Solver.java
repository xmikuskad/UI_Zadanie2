import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays.*;

public class Solver {
	Heap heap = new Heap();
	long count=0;
	int MAX_ATTEMPTS = 1000000;
	int height,width;
	
	public Solver(int heightInc,int widthInc)
	{
		height = heightInc;
		width = widthInc;
	}
	
	public String solvePuzzle(int[][] finalState,int[][] currentStateInc, int mPosInc)
	{		
		int[][] currentState = currentStateInc;
		heap.addToHashTable(getHash(currentState));

		List<Integer> list = new ArrayList<>();
		int mPos = mPosInc;
		
		while(!checkEqual(finalState, currentState))
		{
			//Overit!
			int xPos = mPos%width;
			int yPos = mPos/width;
			
			if(yPos > 0)
			{
				//Hore
				//System.out.println("1");
				//System.out.println(xPos + " | "+yPos);
				moveTile(1, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(yPos < height -1)
			{
				//Dole
				//System.out.println("2");
				//System.out.println(xPos + " | "+yPos);
				moveTile(2, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(xPos > 0)
			{
				//Dolava
				//System.out.println("3");
				//System.out.println(xPos + " | "+yPos);
				moveTile(3, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(xPos < width-1)
			{
				//Doprava
				//System.out.println("4");
				//System.out.println(xPos + " | "+yPos);
				moveTile(4, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			
			State state = heap.getMin();
			if(state == null)
			{
				System.out.println("heap is empty "+count);
				return "RESULT NOT FOUND!";
			}
			//Zastavenie ak hladanie trva prilis dlho
			if(count > MAX_ATTEMPTS)
			{
				return "max attempts reached ... stopping";
			}
			currentState = state.getState();
			list = state.getDirection();
			mPos = state.getMPos();
		}
		
		System.out.println("Number of steps: "+list.size());
		System.out.println("generated states "+count);
		printResults(list);
		
		return "RESULT FOUND";
		
		
	}
	
	private void printResults(List<Integer> list)
	{
		for(int i : list)
			System.out.println(getDirectionName(i));

	}
	
	public String getHash(int[][] state)
	{
		String hashString = "";
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				if(state[i][j]< 10)
					hashString+="0";
				hashString+= String.valueOf(state[i][j]);
			}
		}
		
		return hashString;
	}
	
	private int[][] getArrayCopy(int[][] arrayInc)
	{
		int[][] newArray = new int[height][width];
		
		for(int i=0; i<arrayInc.length; i++)
			  for(int j=0; j<arrayInc[i].length; j++)
			    newArray[i][j]=arrayInc[i][j];
		
		return newArray;
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
	
	public void moveTile(int direction, int[][] currentState,int[][] finalState, List<Integer> list, int xPos, int yPos)
	{
		List<Integer> newList = new ArrayList<>();
		newList.addAll(list);
		
		//System.out.println("-----4-----");
		//printArray(currentState);
		
		int tmp, mPos;
		switch (direction) {
		case 1:
			tmp = currentState[yPos-1][xPos];
			currentState[yPos-1][xPos] = -1;
			currentState[yPos][xPos] = tmp;
			mPos = (yPos-1)*width + xPos;
			break;
		case 2:
			tmp = currentState[yPos+1][xPos];
			currentState[yPos+1][xPos] = -1;
			currentState[yPos][xPos] = tmp;
			mPos = (yPos+1)*width + xPos;
			break;
		case 3:
			tmp = currentState[yPos][xPos-1];
			currentState[yPos][xPos-1] = -1;
			currentState[yPos][xPos] = tmp;	
			mPos = yPos*width + xPos-1;
			break;
		case 4:
			tmp = currentState[yPos][xPos+1];
			currentState[yPos][xPos+1] = -1;
			currentState[yPos][xPos] = tmp;
			mPos = yPos*width + xPos +1;
			break;

		default:
			//Nikdy nenastane
			tmp = 0;
			mPos = 0;
			break;
		}
		
		//System.out.println("-----5-----");
		//printArray(currentState);

		
		newList.add(direction);
		
		//Tu sa upravuje heuristika
		int priority = heuristic1(finalState, currentState);
		heap.insert(new State(priority, currentState, newList, mPos),this);
		
	}
	
	public void incrementCount() {count++;}
	
	
	private int heuristic1(int[][] finalState, int[][] currentState)
	{
		int count =0;

		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				if(currentState[i][j] != finalState[i][j])
					count++;
			}
		}

		return count;
	}
	
	private boolean checkEqual(int[][] finalState, int[][] currentState)
	{
		//System.out.println("-----1-----");
		//printArray(finalState);
		//System.out.println("-----2-----");
		//printArray(currentState);
		//System.out.println("-----3-----");
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				if(currentState[i][j] != finalState[i][j])
					return false;
			}
		}
		return true;
	}
	
	//Debug
	private void printArray(int [][]arrayEh)
	{
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				System.out.print(arrayEh[i][j]);
			}
			System.out.println("");
		}
	}
}
