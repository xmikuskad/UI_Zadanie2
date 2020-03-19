import java.util.ArrayList;
import java.util.List;

public class State {

	private String state;
	private int doneCount;
	private List<Integer> direction = new ArrayList<Integer>();
	
	public State(int doneCountInc, String stateInc,List<Integer> directionInc)
	{
		doneCount = doneCountInc;
		state = stateInc;
		direction = directionInc;
	}
	
	public String getState() {	return state;	}
	public int getDoneCount() {	return doneCount;	}
	public List <Integer> getDirection() {	return direction;	}
	
}
