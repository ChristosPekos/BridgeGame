import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class SpaceSearcher {
	private ArrayList<State> frontier;
    private HashSet<State> closedSet;
    
    //constructor
    SpaceSearcher()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }
	

    // A* with close set  search method
	public State AStarClosedSet(State initialState)
	{
        // if there is only one state
		if(initialState.isFinal()){
            return initialState;
        }

        //add the initial state in the frontier
		this.frontier.add(initialState);

        //while there are states in the frontier
		while(this.frontier.size() > 0){
            // get the first state 
			State currentState = this.frontier.remove(0);
            // check if it is final
			if (currentState.isFinal())
            {
                return currentState;
            }
			// check if we already have encountered the state, if not put it in the closed set 
			if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
                this.frontier.addAll(currentState.makeMove());
				Collections.sort(this.frontier);        //sort frontier based on f= g+h  to get the  best in front
            }
		}
		return null;
	}
}