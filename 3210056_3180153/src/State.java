import java.util.*;

public class State implements Comparable<State>
{
    ArrayList<Person> left;
    ArrayList<Person> right;
    State father;
	int totalTime;
    boolean lamp;
    int f;
    

    //constructor - fill with arguments if necessary
	public State(ArrayList<Person> family) 
	{
		this.father = null;
		this.totalTime = 0;
        this.lamp = true; // True if lamp is in the right side, false if it is in the left side
        this.left = new ArrayList<>();
        this.right = family;
        

	}

    //constructor with the variables of a state as a parameter
    public State(ArrayList<Person> left,ArrayList<Person> right,State father,int totalTime,boolean lamp)
	{
		this.left = new ArrayList<>(left);
        this.right =  new ArrayList<>(right);
        this.father = father;
        this.totalTime = totalTime;
        this.lamp = lamp;
	}

    //constructor with a State object as parameter
    public State(State state) {
		
        this.left = state.left;
        this.right = state.right;
        this.father = state.father;
        this.totalTime = state.totalTime;
        this.lamp = state.lamp;        
    }


    //father getter
    public State getFather()
	{
		return this.father;
	}

    //father setter
    public void setFather(State f)
	{
		this.father = f;
	}

    //left side arraylist getter
    public ArrayList<Person> getLeft()
    {
        return this.left;
    }

    //left side arraylist setter
    public void setLeft(ArrayList<Person> l)
    {
         this.left = new ArrayList<Person>(l);
    }

    //right side arraylist getter
    public ArrayList<Person> getRight()
    {
        return this.right;
    }

    //right side arraylist setter
    public void setRight(ArrayList<Person> r)
    {
         this.right = new ArrayList<Person>(r);
    }

    //totalTime getter
    public int getTotalTime() 
	{
		return this.totalTime;
	}
    
    //totalTime setter
	public void setTotalTime(int time)
	{
		this.totalTime = time;
	}


    // f setter
    public void setF (int f){
        this.f = f;
    }

        // f getter
    public int getF(){
        return this.f;
    }


    // lamp getter
    public boolean getLamp()
    {
        return this.lamp;
    }

    //lamp setter
    public void setLamp(boolean l)
    {
         this.lamp = l;
    }

    
 


    //print a basic represantion of the state
    public void print(){
        
        //print every member of the left side
        for (Person family : this.left){
            System.out.print(family.pid + " ");
        }

        //print the lamp if it on the left side
        if (lamp == false){
            System.out.print("L ");
        }
        //print a basic representation of a bridge
        System.out.print("|____________________|");

        //print the lamp if it on the right side
        if (lamp){
            System.out.print(" L ");
        }

        //print the right side of the bridge;
        for (Person family : this.right){
            System.out.print(family.pid + " ");
        }

        System.out.println("");
        System.out.println("The total time is " + this.getTotalTime());

    }


    
    
    //make a move from left to right for the person that the id given belongs to.
    public State makeMoveRight(int id){
        
        for(Person familyMember: this.left){
            //check if the person exists on the left side. If true, make the move to the right
            if (familyMember.pid == id) {

                this.right.add(familyMember);
                this.left.remove(familyMember);
                break;


            }

        }
        return this;
    }




    //make a move from right to left for the person that the id given belongs to.
    public State makeMoveLeft(int id){
        
        for(Person familyMember: this.right){
            
            //check if the both people exist on the left side. If true, make the move to the right
            if (familyMember.pid == id) {

                this.left.add(familyMember);
                this.right.remove(familyMember);
                break;

            }

        }
        return this;
    }

    
    // make a move from right to left for 2 different people based on their ids.
    public State makeMoveLeft(int id1, int id2){
        //create a temporary arraylist person
        ArrayList<Person> temp= new ArrayList<>();

        // for every person that currently exists on the right side
        for(Person familyMember: this.right){
            // if the the id of a person is equal to one of the 2 given as a parameter
            if (familyMember.pid == id1 || familyMember.pid == id2 ) {
                // add the temporary list
                temp.add(familyMember);
            }
        }

        //for every person on the temporary list remove the person from the right side and add them to the left.
        for( Person member: temp){
            this.left.add(member);
            this.right.remove(member);
        }
        return this;
    }



    //create all the possible children from the current state
    public ArrayList<State> makeMove(){

        ArrayList<State> children = new ArrayList<>();

        
        State currentState = new State(this);
        
        // if the lamp is on the right
        if (this.lamp){
            //all possible moves from right to left

            //if there is only one person on the right side of the bridge
            if (this.right.size() <2){
                
                //create a new state based on the current one
                State child = new State(currentState.left, currentState.right, currentState, currentState.totalTime, false);

                //get the time that the person needs to cross the bridge
                child.totalTime += this.right.get(0).getTime();

                //make the move from the right to the left for the person
                child.makeMoveLeft(this.right.get(0).pid);

                //calculate the f = h + g for the the child
                child.setF(child.evaluate());

                 //make the move to the left and add the child to the arraylist of possible children from the current state
                children.add(child);


            }

            //all possible two person moves
            else{
                for (int i = 0; i<this.right.size(); i++){
                    for (int j = i+1; j< this.right.size(); j++){
                        //create a new state based on the current one
                        State child = new State(currentState.left, currentState.right, currentState, currentState.totalTime, false);
                        
                        //get the time of every single combination of people that could cross the bridge
                        int time1 = this.right.get(i).getTime();
                        int time2 = this.right.get(j).getTime();

                        //make all the possible moves from right to left of 2 people that could happen
                        child.makeMoveLeft(this.right.get(i).pid, this.right.get(j).pid);
                        
                        //add to the total time the longer time from the pair of people
                        child.totalTime += Math.max(time1, time2);

                        //calculate the f = h + g for the the child
                        child.setF(child.evaluate());
                        
                        // add the child to the arraylist of possible children from the current state
                        children.add(child);

                        
                        
                    }
                }
            }


        }
        
        // if the lamp is on the left
        else{
            //all possible moves from left to right
            
            
            //all possible single person moves
            for (int i = 0; i< this.left.size(); i++){

                //create a new state based on the current one
                State child = new State(currentState.left, currentState.right, currentState, currentState.totalTime, true);

                //get the time that the person needs to cross the bridge
                int time = this.left.get(i).getTime();

                //make the move to the right
                child.makeMoveRight(this.left.get(i).pid);
                
                //add to the total time of the child the time of the person
                child.totalTime += time;
                
                //calculate the f = h + g for the the child
                child.setF(child.evaluate());
                
                //add the child to the arraylist of possible children from the current state
                children.add(child);
                
                

                
            }
            
        }
        
        //return the possible children of the current state
        return children;
    }

    
      /*Our heurestic function does not take into consideration that only 2 people are allowed to be on the bridge on the same time */
 
    int estimateCost() //heurestic
    {
        //if the lamp is on the right
        if (lamp){
            int max = 0;
            //find the longest time that belongs to a person on the right side of the bridge and return it
            for (int i = 0; i < right.size(); i++){
                if (max < right.get(i).getTime()){
                    max = right.get(i).getTime();
                }
            }
            //return the longest time
            return max;
        }
        
        // if the lamp is on the left
        else{
            int max = 0;
            int min = 1000000000;
            
            //if the right is not empty
            if(!right.isEmpty()){
                
                // find the shortest time that belongs to a person on the left side of the bridge
                for (int i = 0; i < left.size(); i++){
                    if (min > left.get(i).getTime()){
                        min = left.get(i).getTime();
                    }
                }

                // find the longest time that belongs to a person on the right side of the bridge
                for (int i = 0; i < right.size(); i++){
                    if (max < right.get(i).getTime()){
                        max = right.get(i).getTime();
                    }
                }

                //return the sum of those min and max numbers.
                return min + max;
            }
            
            // return 0 because if the right side is empty it means that we are in an end state
            return 0;
        }
    }



    //check if we have reached an end state
    public boolean isFinal() {
        
        if (this.right.isEmpty() && this.lamp == false){
            return true;
        }
        else{
            return false;
        }
    }

    //calculates the f = h+g for a state
    private int evaluate(){

        //returns h + g;
        return this.estimateCost() + this.totalTime;
        
    }



	//compare two states based on the score of their f
	@Override
    public int compareTo(State s)
    {
    	return Double.compare(this.evaluate(), s.evaluate());
    }

}




