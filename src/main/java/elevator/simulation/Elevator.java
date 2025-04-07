package elevator.simulation;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

public class Elevator{

    static final Logger LOGGER = Logger.getLogger(Elevator.class.getName());

    private int bottomFloor;
    private int topFloor;

    private double height = bottomFloor; // Height in floors, defaults to bottom floor
    private double velocityUp = 0; // Floors per second
    private double maxVelocity = 0.5; // Floors per second
    private double epsilon = 0.01; // Distance to snap to a floor

    private Queue<Integer> destinations = new PriorityQueue<Integer>();

    private Elevator(){
        // Disable default constructor, we need initialization data from application properties
    }

    public Elevator(int bottomFloor, int topFloor, double maxVelocity, double epsilon){
        // Check for problematic values
        if(maxVelocity < 0) throw new RuntimeException("elevator.maxVelocity must be >= 0");
        if(epsilon < 0)     throw new RuntimeException("elevator.floorEpsilon must be >= 0");
        
        this.bottomFloor = bottomFloor;
        this.topFloor = topFloor;
        this.maxVelocity = maxVelocity;
        this.epsilon = epsilon;
    }

    public void updateState(long milliseconds){
        this.height = this.height + this.velocityUp*(milliseconds/1000);

        // Check if we need to snap to a floor
        double heightDecimal = this.height - (int) this.height;
        if(Math.abs(heightDecimal) < this.epsilon ){
            this.height = (int) this.height;
        }

        this.velocityUp = calculateVelocity(milliseconds);

        LOGGER.info("Elevator velocity:" + this.velocityUp + " Position: " + this.height);
    }

    // Add a floor stop to the queue
    public void addFloorToQueue(int floor){

        if(floor > this.topFloor || floor < this.bottomFloor){
            throw new RuntimeException("Requested floor " + floor + " is not between " + this.bottomFloor + " and " + this.topFloor);
        }

        this.destinations.add(floor);
    }

    //
    public boolean isIdle(){
        return this.destinations.size() == 0;
    }

    // Check if it will accept a specific floor task.
    public boolean isPassingFloor(int floor){
        return true; // TODO
    }

    // Use the last timestep's duration to estimate how far we need to step if we're close to the target floor.
    private double calculateVelocity(double milliseconds){
        if(this.destinations.size() == 0) return 0;

        int targetFloor = this.destinations.peek();
        double targetFloorDistance = targetFloor - this.height; 
        double velocity = this.maxVelocity * Math.signum(targetFloorDistance);

        double nextHeightDeltaEstimate = velocity*(milliseconds/1000);

        if(targetFloorDistance < nextHeightDeltaEstimate){
            velocity = targetFloorDistance;
        }

        return velocity;

    }

}