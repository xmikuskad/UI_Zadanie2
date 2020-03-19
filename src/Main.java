import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int width, height, mPos=-1;
		int[][] finalState,currentState;
		Solver solver;
		
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
				if(currentState[i][j]==-1 )
					mPos = j+i*width;
			}
		}
		scanner.close();

		solver = new Solver(height,width);
		String endString = solver.solvePuzzle(finalState,currentState,mPos);
		System.out.println(endString);
		
	}
	

}
