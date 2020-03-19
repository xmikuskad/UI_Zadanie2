import java.util.ArrayList;
import java.util.List;

public class State {

	//private String state;
	private int[][] state;
	private int doneCount;
	private List<Integer> direction = new ArrayList<Integer>();
	private int mPos;
	
	public State(int doneCountInc, int[][] stateInc,List<Integer> directionInc, int mPosInc)
	{
		doneCount = doneCountInc;
		state = stateInc;
		direction = directionInc;
		mPos = mPosInc;
	}
	
	public int[][] getState() {	return state;	}
	public int getDoneCount() {	return doneCount;	}
	public List <Integer> getDirection() {	return direction;	}
	public int getMPos() {	return mPos;	}
	
}
