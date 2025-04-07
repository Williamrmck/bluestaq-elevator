package elevator.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;


@Repository
public class SimState {

    static final Logger LOGGER = Logger.getLogger(SimState.class.getName());

    @Value("${sim.elevatorCount}")
    private int elevatorCount;

    @Value("${sim.bottomFloor}")
    private int bottomFloor;

    @Value("${sim.topFloor}")
    private int topFloor;

    @Value("${elevator.maxVelocity}")
    private double maxVelocity = 0.5; // Floors per second
    
    @Value("${elevator.floorEpsilon}")
    private double epsilon = 0.01; // Distance to snap to a floor
    
    public List<Elevator> elevators;
    public Queue<ElevatorRequest> requests;

    @PostConstruct
    public void initializeElevators(){
        this.requests = new LinkedList<>();
        this.elevators = new ArrayList<>();

        for (int index = 0; index < this.elevatorCount; index++){
            Elevator elevator = new Elevator(this.bottomFloor, this.topFloor, this.maxVelocity, this.epsilon);

            this.elevators.add(elevator);
        }
    }

    public void executeTimestep(long milliseconds){
        for(Elevator elevator : this.elevators){
            elevator.updateState(milliseconds);
        }

        LOGGER.info("STEP State:" + milliseconds);
    }

}