package elevator.optimization;

import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import elevator.simulation.SimState;

@Service
public class OptimizationService {

    static final Logger LOGGER = Logger.getLogger(OptimizationService.class.getName());

    @Autowired
    private SimState simState;

    private AbstractOptimizer optimizer;

    @PostConstruct
    public void setupOptimizer(){
        // TODO check sim state for optimizer type.

        optimizer = new FirstAvailableOptimizer(simState);
    }

    public void optimize(){
        optimizer.optimize();
    }


}