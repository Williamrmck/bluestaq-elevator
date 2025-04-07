package elevator.simulation;

import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.Duration;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import elevator.optimization.OptimizationService;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SimLoop {

    static final Logger LOGGER = Logger.getLogger(SimLoop.class.getName());

    private LocalDateTime lastRunTime;

    @Autowired
    private SimState simState;

    @Autowired
    private OptimizationService optimizationService;

    @PostConstruct
    public void resetSimulation(){
        lastRunTime = LocalDateTime.now();
    }

    @Scheduled(fixedRateString = "${sim.deltaTime}")
    public void executeTimestep(){
        // Get the current time and figure out how much time has actually elapsed since our last timestep.
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timestepDuration = Duration.between(lastRunTime, currentTime);
        long timestepLength = timestepDuration.toMillis(); // Milliseconds

        simState.executeTimestep(timestepLength);

        optimizationService.optimize();

        LOGGER.info("STEP: " + timestepLength);

        lastRunTime = currentTime; // This is when the timestep started, but we don't want to "skip" any time.
    }

}