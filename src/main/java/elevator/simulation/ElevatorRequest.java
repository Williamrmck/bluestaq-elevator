package elevator.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;

// These are elevator requests from outside the elevator.
public class ElevatorRequest {

    public enum RequestDirection {
        UP,
        DOWN
    }

    @JsonProperty
    public int floorRequestedAt;

    @JsonProperty
    public RequestDirection requestDirection;
}
