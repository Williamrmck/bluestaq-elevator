package elevator.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;


@Repository
public class SimState {

    static final Logger LOGGER = Logger.getLogger(SimState.class.getName());

    private int elevatorCount = 4;
    
    public List<Elevator> elevators;
    public Queue<ElevatorRequest> requests;


    @PostConstruct
    public void initializeElevators(){
        this.requests = new LinkedList<>();
        this.elevators = new ArrayList<>();

        for (int index = 0; index < elevatorCount; index++){
            Elevator elevator = new Elevator();

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