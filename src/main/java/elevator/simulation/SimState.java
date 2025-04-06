package elevator.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import elevator.optimization.AbstractOptimizer;
import elevator.optimization.FirstAvailableOptimizer;
import elevator.optimization.OptimizationService;
import elevator.simulation.Elevator;
import jakarta.annotation.PostConstruct;

@Repository
public class SimState {

    static final Logger LOGGER = Logger.getLogger(SimState.class.getName());

    private int elevatorCount = 4;
    
    public List<Elevator> elevators;

    @Autowired
    private OptimizationService optimizationService;

    @PostConstruct
    public void initializeElevators(){
        elevators = new ArrayList<>();

        for (int index = 0; index < elevatorCount; index++){
            Elevator elevator = new Elevator();

            elevators.add(elevator);
        }
    }

    public void executeTimestep(long milliseconds){
        optimizationService.optimize();

        for(Elevator elevator : elevators){
            elevator.updateState(milliseconds);
        }

        LOGGER.info("STEP State:" + milliseconds);
    }

}