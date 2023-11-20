public class Person {

    //static int indicating the number of family members on each run of the program
    static int numberOfMembers = 0;
    
    // id for every person
    int pid = numberOfMembers;
    
    //the time each person needs to cross the bridge
    int time;

    //Person constructor
    public Person(int time){
        this.time = time;
        numberOfMembers++;
    }

    //person id setter
    public void setPid(int pid){
        this.pid = pid;
    }

    //person time setter
    public void setTime(int time){
        this.time = time;
    }

    //person id getter
    public int getPid(){
        return pid;
    }

    //person time getter
    public int getTime(){
        return time;
    }
}