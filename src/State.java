import java.util.ArrayList;
import java.util.List;

//Tato classa udrzuje v sebe udaje o jednom stave
public class State {

	private int[][] state;	//Aktualny obraz stavu
	private int priority;	//Cislo vypocitane heuristikou
	private List<Integer> direction = new ArrayList<Integer>();	//List smerov ako sme sa sem dostali
	private int mPos;	//Pozicia medzery
	
	public State(int priorityInc, int[][] stateInc,List<Integer> directionInc, int mPosInc)
	{
		priority = priorityInc;
		state = stateInc;
		direction = directionInc;
		mPos = mPosInc;
	}
	
	public int[][] getState() {	return state;	}
	public int getPriority() {	return priority;	}
	public List <Integer> getDirection() {	return direction;	}
	public int getMPos() {	return mPos;	}
	
}
