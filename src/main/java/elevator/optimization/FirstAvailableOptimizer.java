package elevator.optimization;

import java.util.logging.Logger;

import elevator.simulation.SimState;

public class FirstAvailableOptimizer extends AbstractOptimizer {

    public FirstAvailableOptimizer(SimState simState) {
        super(simState);
    }

    @Override
    public void optimize(){
        LOGGER.info("First Available Optimizer Running.");
    }


}