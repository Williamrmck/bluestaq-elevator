package elevator.restcontrollers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import elevator.simulation.ElevatorRequest;
import elevator.simulation.SimState;
@RestController
public class ElevatorRequestController {

    static final Logger LOGGER = Logger.getLogger(ElevatorRequestController.class.getName());

	@Autowired
	SimState simState;

	@PostMapping(path = "/addExternalRequest", consumes = "application/json")
	public String addExternalRequest(@RequestBody ElevatorRequest elevatorRequest)
	{
		LOGGER.info("REST request to call an elevator: " + elevatorRequest.floorRequestedAt + " Going: " + elevatorRequest.requestDirection);
		this.simState.requests.add(elevatorRequest);
		return "Accepted.";
	}

	@PostMapping(path = "/addInternalRequest/{elevatorId}", consumes = "application/json")
	public String addExternalRequest(@RequestBody Integer floor, @PathVariable int elevatorId)
	{
		if(elevatorId < this.simState.elevators.size()){
			this.simState.elevators.get(elevatorId).addFloorToQueue(floor);
			return "Accepted.";
		}else{
			return "Rejected.";
		}
		
	}

	@GetMapping("/simState")
	public String helloWorld() {
		StringBuilder response = new StringBuilder();

		return response.toString();
	}

}