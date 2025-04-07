package elevator.optimization;

import elevator.simulation.Elevator;
import elevator.simulation.ElevatorRequest;
import elevator.simulation.SimState;

public class FirstAvailableOptimizer extends AbstractOptimizer {

    public FirstAvailableOptimizer(SimState simState) {
        super(simState);
    }

    @Override
    public void optimize(){

        // For each elevator request, send it to an available elevator.
        for(ElevatorRequest request : this.simState.requests){
            boolean requestHandled = false;
            for (Elevator elevator : this.simState.elevators){
                if(elevator.isIdle()){
                    LOGGER.info("Sending Elevator to floor: " + request.floorRequestedAt);
                    elevator.addFloorToQueue(request.floorRequestedAt);
                    requestHandled = true;
                    this.simState.requests.remove(request);
                }

                if(requestHandled) break;
            }
        }
    }


}