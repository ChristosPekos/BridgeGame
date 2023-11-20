import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Main {
    
    public static void main(String[] args){
        
        Scanner input = new Scanner(System.in);
        
        int numberOfFamily;     // number of family members
        int lamp_time;          // duration of lamp 

        ArrayList<Person> familyMembers = new ArrayList<>();        //list containing each person of the family


        System.out.println("Welcome to a simulation of family at the Bridge Game! Please type the number of family members: ");

        numberOfFamily = input.nextInt();

        // get each person's crossing time
        for(int i =0; i<numberOfFamily; i++){
            System.out.println("Whats the time of family member " + i + " ? : ");
            Person member = new Person(input.nextInt());
            familyMembers.add(member);
        }

        // get lamp duration time
        System.out. println("Last but not least, please type the duration time of the lamp: ");
        lamp_time = input.nextInt();
        
        // create a new (initial) state object
        State initial= new State(familyMembers);

        // create a new space searcher object 
        SpaceSearcher search = new SpaceSearcher();
        
        // save the moment before we run the A*
        long start = System.currentTimeMillis();
        // run A* with closed set
        State terminal = search.AStarClosedSet(initial);
        // save the moment after we run the A*
        long end = System.currentTimeMillis();

        // if we didn't find a solution
        if(terminal == null){
             System.out.println("Could not find a solution.");
        }
        else
        {
            //if the total passing time is greater than the lamp duration time 
            if ( terminal.getTotalTime() > lamp_time){
                System.out.println("No path found within the lamp's duration time ("+ lamp_time+" sec.)");
            }
            else{
                // print the path from end to start.
                State temp = terminal; // begin from the end.
                ArrayList<State> path = new ArrayList<>();
                path.add(terminal);
                while(temp.getFather() != null) // if father is null, then we are at the root.
                {
                    path.add(temp.getFather());
                    temp = temp.getFather();
                }
                // reverse the path and print.
                Collections.reverse(path);
                for(State item: path)
                {
                    item.print();
                }
                System.out.println();
                System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
                }
        }


    }
}
