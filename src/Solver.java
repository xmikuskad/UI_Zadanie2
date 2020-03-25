import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solver {
	
	int MAX_ATTEMPTS = 600000;
	int SPACE_CHARACTER = 0;
	int PRINT_COUNT_LIMIT = 1000;
	boolean PRINT_PROGRESS = false;
	
	boolean firstHeuristic = true;
	Heap heap = new Heap();
	long generatedCount=0;
	long processedCount=0;
	int height,width;
	
	public Solver(int heightInc,int widthInc,boolean firstHeuristicInc)
	{
		height = heightInc;
		width = widthInc;
		firstHeuristic = firstHeuristicInc;
	}
	
	public List<Integer> solvePuzzle(int[][] finalState,int[][] currentStateInc, int mPosInc)
	{		
		int[][] currentState = currentStateInc;
		heap.addToHashTable(getHash(currentState));

		List<Integer> list = new ArrayList<>();
		int mPos = mPosInc;
		
		//Pokial nie sme vo finalnom stave
		while(!Arrays.deepEquals(finalState, currentState))			
		{
			//x a y pozicia medzery
			int xPos = mPos%width;	
			int yPos = mPos/width;
			
			if(yPos > 0)
			{
				//Vytvorenie stavu prechodom hore
				moveTile(1, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(yPos < height -1)
			{
				//Vytvorenie stavu prechodom dole
				moveTile(2, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(xPos > 0)
			{
				//Vytvorenie stavu prechodom dolava
				moveTile(3, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			if(xPos < width-1)
			{
				//Vytvorenie stavu prechodom doprava
				moveTile(4, getArrayCopy(currentState),finalState,list,xPos,yPos);
			}
			
			//Vybratie najvyhodnejsieho prvku z haldy
			State state = heap.getMin();
			processedCount++;
			
			//Kontrola, ci nahodou halda nie je prazdna
			if(state == null)
			{
				System.out.println("Prazdny heap!");
				System.out.println("Pocet spracovanych stavov "+processedCount);
				System.out.println("Pocet vygenerovanych stavov "+generatedCount);
				return null;
			}
			
			//Zastavenie ak hladanie trva prilis dlho
			if(generatedCount > MAX_ATTEMPTS)
			{
				System.out.println("maximum pokusov dosiahnutych ... stopping");
				System.out.println("Pocet spracovanych stavov "+processedCount);
				System.out.println("Pocet vygenerovanych stavov "+generatedCount);
				return null;
			}
			
			//Nastavenie noveho stavu, pozicie a doterajsieho smeru
			currentState = state.getState();
			list = state.getDirection();
			mPos = state.getMPos();
		}
		
		System.out.println("Pocet krokov: "+list.size());
		System.out.println("Pocet spracovanych stavov "+processedCount);
		System.out.println("Pocet vygenerovanych stavov "+generatedCount);
		
		return list;
		
		
	}
	
	//Tato funkcia vytvori string z daneho stavu.
	//Pouzivam ju pre vytvorenie stringu do hash mapy aby som zabranil opakovaniu stavov.
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
	
	//Vytvori kopiu pola... Nenasiel som ziadnu java funkciu, ktora by to robila pre 2 rozmerne pole
	private int[][] getArrayCopy(int[][] arrayInc)
	{
		int[][] newArray = new int[height][width];
		
		for(int i=0; i<arrayInc.length; i++)
			  for(int j=0; j<arrayInc[i].length; j++)
			    newArray[i][j]=arrayInc[i][j];
		
		return newArray;
	}
	
	//Posunutie sa v danom smere
	public void moveTile(int direction, int[][] currentState,int[][] finalState, List<Integer> list, int xPos, int yPos)
	{
		List<Integer> newList = new ArrayList<>();
		newList.addAll(list);
		
		int tmp, mPos;
		
		
		switch (direction) {
		case 1:	//Posunutie hore
			tmp = currentState[yPos-1][xPos];
			currentState[yPos-1][xPos] = SPACE_CHARACTER;
			currentState[yPos][xPos] = tmp;
			mPos = (yPos-1)*width + xPos;
			break;
		case 2:	//Posunutie dole
			tmp = currentState[yPos+1][xPos];
			currentState[yPos+1][xPos] = SPACE_CHARACTER;
			currentState[yPos][xPos] = tmp;
			mPos = (yPos+1)*width + xPos;
			break;
		case 3:	//Posunutie dolava
			tmp = currentState[yPos][xPos-1];
			currentState[yPos][xPos-1] = SPACE_CHARACTER;
			currentState[yPos][xPos] = tmp;	
			mPos = yPos*width + xPos-1;
			break;
		case 4:	//Posunutie doprava
			tmp = currentState[yPos][xPos+1];
			currentState[yPos][xPos+1] = SPACE_CHARACTER;
			currentState[yPos][xPos] = tmp;
			mPos = yPos*width + xPos +1;
			break;

		default:
			//Nikdy nenastane
			tmp = 0;
			mPos = 0;
			break;
		}
		
		newList.add(direction);
		
		//Vybratie heuristickej funkcie a pridanie noveho stavu do haldy
		int priority;
		if(firstHeuristic)
			priority = heuristic1(finalState, currentState);
		else
			priority = heuristic2(finalState, currentState);
		
		heap.insert(new State(priority, currentState, newList, mPos),this);
		
	}
	
	//Zvacsuje pocet vytvorenych stavov.
	public void incrementCount() 
	{
		generatedCount++;
		if(PRINT_PROGRESS && generatedCount%PRINT_COUNT_LIMIT == 0)
			System.out.println(generatedCount+" vygenerovanych stavov");
	}
	
	//Funkcia prvej heuristiky
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
	
	//Funkcia druhej heuristiky
	private int heuristic2(int[][] finalState, int[][] currentState)
	{
		int[] histogram = new int[height*width];
		int count =0;
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				histogram[currentState[i][j]]=j+i*width;
			}
		}
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				int num = finalState[i][j];
				int pos = histogram[num];
				count+= Math.abs(pos/width-i) + Math.abs(pos%width -j);			
			}
		}
		
		return count;
	}
	
	
}
