package elevator.simulation;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Logger;

public class Elevator{

    static final Logger LOGGER = Logger.getLogger(Elevator.class.getName());

    private Queue<Integer> destinations = new PriorityQueue<Integer>();

    public void updateState(long milliseconds){
        LOGGER.info("STEP Elevator:" + milliseconds);
    }

    // Add a floor stop to the queue
    public void addFloorToQueue(int floor){

    }

    public boolean isAvailableForTasking(){
        return true; // TODO
    }

    // Check if it will accept a specific floor task.
    public boolean isAvailableForFloorTasking(int floor){
        return true; // TODO
    }

}