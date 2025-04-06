package elevator.simulation;

import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.Duration;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SimLoop {

    static final Logger LOGGER = Logger.getLogger(SimLoop.class.getName());

    private LocalDateTime lastRunTime;

    @Autowired
    private SimState simState;

    @PostConstruct
    public void resetSimulation(){
        lastRunTime = LocalDateTime.now();
    }

    @Scheduled(fixedRate = 1000)
    public void executeTimestep(){
        // Get the current time and figure out how much time has actually elapsed since our last timestep.
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timestepDuration = Duration.between(lastRunTime, currentTime);
        long timestepLength = timestepDuration.toMillis(); // Milliseconds

        simState.executeTimestep(timestepLength);

        LOGGER.info("STEP: " + timestepLength);

        lastRunTime = currentTime; // This is when the timestep started, but we don't want to "skip" any time.
    }

}