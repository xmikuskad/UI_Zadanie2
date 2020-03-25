import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static String HEURISTIC_ONE_PATH = "heuristika1.txt";
	private static String HEURISTIC_TWO_PATH = "heuristika2.txt";
	private static final String UP = "hore";
	private static final String DOWN = "dole";
	private static final String LEFT = "dolava";
	private static final String RIGHT = "doprava";
	
	public static void main(String[] args) {
		
		//Znak, ktory sa pouziva namiesto medzery
		int SPACE_CHARACTER = 0;
		
		int width, height, mPos=-1;
		int[][] finalState,currentState;
		Solver solver;
		
		//Nacitanie vstupu
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Vyska: ");
		height = scanner.nextInt();
		System.out.println("Sirka: ");
		width = scanner.nextInt();		
		
		finalState = new int[height][width];
		currentState = new int[height][width];		
		
		System.out.println("Konecny stav: ");
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				finalState[i][j]= scanner.nextInt();
			}
		}
			
		System.out.println("Aktualny stav: ");		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				currentState[i][j]= scanner.nextInt(); 
				if(currentState[i][j]==SPACE_CHARACTER )
					mPos = j+i*width;
			}
		}
		scanner.close();

		//Volanie prvej heuristiky
		System.out.println("\nPrva heuristika:");
		solver = new Solver(height,width,true);
		printResults(solver.solvePuzzle(finalState,currentState,mPos), true);
		
		//Volanie druhej heuristiky
		System.out.println("\nDruha heuristika:");
		solver = new Solver(height,width,false);
		printResults(solver.solvePuzzle(finalState,currentState,mPos), false);
		
		System.out.println("\nTestovanie:");
		Tester tester = new Tester(currentState, finalState,height,width);
		System.out.println("Test 1 heuristiky: "+tester.testHeuristic(HEURISTIC_ONE_PATH));
		System.out.println("Test 2 heuristiky: "+tester.testHeuristic(HEURISTIC_TWO_PATH));
		
	}
	
	//Prevod smeru na string
	private static String getDirectionName(int num)
	{
		switch (num) {
		case 1: return UP;
		case 2: return DOWN;
		case 3: return LEFT;
		case 4: return RIGHT;
		default:
			break;
		}
		return "Smer nenajdeny!";
	}
	
	//Tato funkcia vypise najdenu cestu
	private static void printResults(List<Integer> list, boolean firstHeuristics)
	{
		if(list == null) return;
		
		PrintWriter writer;
		try {
			if(firstHeuristics)
				writer = new PrintWriter(HEURISTIC_ONE_PATH, "UTF-8");
			else
				writer = new PrintWriter(HEURISTIC_TWO_PATH, "UTF-8");
			
			
			for(int i : list)
				writer.println(getDirectionName(i));
			
			writer.close();
			
		} catch(FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
	

}
