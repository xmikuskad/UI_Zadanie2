import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Tester {

	private final String UP = "hore";
	private final String DOWN = "dole";
	private final String LEFT = "dolava";
	private final String RIGHT = "doprava";
	int SPACE_CHARACTER = 0;
	
	private int[][] initialState,finalState;
	private int height, width;
	
	public Tester (int[][] currentState, int[][] finalState2,int heightInc,int widthInc)
	{
		initialState = currentState;
		finalState = finalState2;
		height = heightInc;
		width = widthInc;
	}
	
	public String testHeuristic(String heuristicString)
	{
		int[][] initialStateCopy = getArrayCopy(initialState);
		BufferedReader bufferedReader;
		String command;
		try {
			bufferedReader = new BufferedReader(new FileReader(heuristicString));
			
			while((command = bufferedReader.readLine()) != null) 
			{
				MoveTile(initialStateCopy, command);
			}
			
			bufferedReader.close();
			
			if(Arrays.deepEquals(finalState, initialStateCopy))
				return "Riesenie platne";
			else
				return "Riesenie neplatne";
			
		} catch (IOException e) {
			return "Nenasiel som subor";
		}	
	}
	
	private void MoveTile(int[][] array, String command)
	{
		int pos = getSpacePosition(array);
		int xPos = pos%width;	
		int yPos = pos/width;
		//System.out.println("Command "+command + " X:"+xPos + " Y:"+yPos);
		int tmp;
		switch(command)
		{
			case UP:
				tmp = array[yPos-1][xPos];
				array[yPos-1][xPos] = SPACE_CHARACTER;
				array[yPos][xPos] = tmp;
				break;
			case DOWN:
				tmp = array[yPos+1][xPos];
				array[yPos+1][xPos] = SPACE_CHARACTER;
				array[yPos][xPos] = tmp;
				break;
			case LEFT:
				tmp = array[yPos][xPos-1];
				array[yPos][xPos-1] = SPACE_CHARACTER;
				array[yPos][xPos] = tmp;
				break;
			case RIGHT:
				tmp = array[yPos][xPos+1];
				array[yPos][xPos+1] = SPACE_CHARACTER;
				array[yPos][xPos] = tmp;
				break;			
		}
	}
	
	private int getSpacePosition(int[][] array)
	{
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				if(array[i][j] == SPACE_CHARACTER )
				{
					return i*width+j;
				}
			}
		}
		return 0;
	}
	
	private int[][] getArrayCopy(int[][] arrayInc)
	{
		int[][] newArray = new int[height][width];
		
		for(int i=0; i<height; i++)
			  for(int j=0; j<width; j++)
			    newArray[i][j]=arrayInc[i][j];
		
		return newArray;
	}

}
