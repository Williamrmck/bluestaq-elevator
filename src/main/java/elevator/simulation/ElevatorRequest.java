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

    @Override
    public boolean equals(Object other){
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        final ElevatorRequest otherElevatorRequest = (ElevatorRequest) other;

        boolean equal = this.floorRequestedAt == otherElevatorRequest.floorRequestedAt;
        equal &=        this.requestDirection == otherElevatorRequest.requestDirection;

        return equal;
    }
}
