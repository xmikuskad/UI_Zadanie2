import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int width, height;
		String finalState,currentState;
		Solver solver = new Solver();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Height: ");
		height = scanner.nextInt();
		System.out.println("Width: ");
		width = scanner.nextInt();		
		
		System.out.println("Final state: ");
		finalState = scanner.next();
		System.out.println("Current state: ");
		currentState = scanner.next();	
		scanner.close();
		
			
		System.out.println("H: "+height+" W: "+width);
		System.out.println("finalState: "+finalState);
		System.out.println("Current state "+currentState);

		
		String endString = solver.solvePuzzle(finalState,currentState,height,width);
		System.out.println("RESULT: "+endString);
		
	}
	

}
