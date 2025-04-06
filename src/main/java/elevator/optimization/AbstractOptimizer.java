package elevator.optimization;

import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import elevator.simulation.SimState;

public abstract class AbstractOptimizer {

    static final Logger LOGGER = Logger.getLogger(AbstractOptimizer.class.getName());

    private SimState simState;

    public AbstractOptimizer(SimState simState){
        this.simState = simState;
    }

    public abstract void optimize();


}