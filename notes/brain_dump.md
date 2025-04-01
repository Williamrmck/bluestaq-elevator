# Feature Ideas
Features to implement. 
Anything marked with a (?) is a stretch goal

- REST API for giving commands to the elevators
    - At each floor, up and down buttons
    - On each car, floor number buttons
    - On each car, alarm button
    - On each car, door open and door closed button
        - "Door closed" immediately dispatches from floor
        - "Door open" takes an amount of time to have the door stay open longer
- Multiple elevators in a bank
    - Dispatch strategies
        - Return to common floor
        - Wait on last called floor
        - Catch one "on the way" to a floor
    - Express elevators (?)
    - Multi-door elevators (?)
- Real Time simulation
    - Cars take time to get between floors
    - Cars must stop for a random time at each floor
    - Cars must wait longer if door open held
- JSON config file for runtime
    - How many floors?
    - How many elevators?
    - Elevator speed + acceleration
    - Simulation config
        - Real time multiplier
        - Time step size (?)
- Frontend (?)
    - This would be best in a second docker image
    - Live update of current elevator location
    - Ability to submit button presses
    - See current sim state (floors, elevator count, etc.)

# Deployment Features
Features for the environment. 
Anything marked with a (?) is a stretch goal.

- SpringBoot
- Gradle build and test
- Unit testing
- Security Scanning
    - Trufflehog
    - Clamav (?)
    - Sonarqube
    - Grype
    - Syft
- Docker
    - Docker compose to make deployment quick
    - Should be a single docker image for deployment
    - Maybe builder image to produce JARs

# Coding Requirements
- Java 17
- javadocs
    - Built with gradle
    - Pushed to GitHub pages
- README file
- One button deployment
- Documentation of assumptions and features
- GitHub links must be PUBLIC!


