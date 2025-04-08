# Bluestaq Code Challenge: The Elevator

# Quick Start

A primary philosophy of this project was to have single button build and deployment with minimal external requirements. This is done to emulate having a CI/CD pipeline with deployment onto an airgapped network.

As such, building is done within a docker container and deployment is done with a docker container orchestrated by docker compose.

## Building
Code building is performed via a docker builder image. 
With docker and the compose plugin installed, building is as easy as:
` docker compose build


## Deployment

After the build process, the elevator simulation can be run with:
` docker compose up

## Usage

The Swagger/OpenAPI REST endpoint can be accessed via the following URL:
http://localhost:8080/swagger-ui/index.html

Currently the Swagger endpoint is the primary way to interface with the service. The applications.properties file contains the initialization parameters for the simulation, however it is baked into the docker image and either the image must be re-built or the file must be overwritten with "docker cp".


# Details

## Architecture

The key driver for architectural decisions was to apply realtime simulation properties while also creating modularity and flexibility in many areas, such as the optimization function for the elevator bank and the individual elevator parameters.

From a software stack perspective, the elevator simulation is a monolithic RESTful Spring application using Spring Boot and Java 17. These choices were made for several reasons:
- Showcase the tech stack
- Increased modularity with the Bean containers
- Ease of configuration, 
- Built-in capabilities such as Jackson, Tomcat, and scheduling tasks
This architecture lends itself well to a modular backend microservice. A frontend GUI was planned, but not implemented. Additionally, a simulation driver to automatically simulate elevator usage was planned. These two features would be separate microservices to allow the backend to still be used. No plans for a data microservice (such as a database) was planned, since that seemed to have no minimal benefits beyond the stored elevator state in the backend.

From a modeling and simulation perspective, the elevator simulation is a time-based, continuous simulation. The elevators are stepped forward in time via a time-based simulation loop and the state is not strictly discrete. Time-based was chosen over an event-based simulation, partially to simplify the simulation component of the project and partially because event-based simulations are more challenging to make realtime. The physics loop is soft-realtime, and will likely lead to "bad" simulation results if the time it takes to calculate a physics step is longer than the allocated time for the scheduler. That being said, the time step function does attempt to change the calculated delta-time to match the wall clock delta time, making it a somewhat robust soft-realtime simulation.

## Building JARs

Building uses the gradle build automation tool to ease building from different environments. The standard build here uses a docker builder image, which is thrown away after the build artifacts are moved to a minimized Java image. Both the gradle and the JDK image use Alpine as the base image to reduce complexity, size, and attack surface.

Using gradle and the docker builder also allows for flexible building, from a CI/CD pipeline to an austere network with either data diodes to resolve dependencies or offline dependency repositories.

## Unit Testing

Currently, unit testing is not implemented, which goes against the core tenants of test-driven-development.

Unit testing can be done via JUnit, and should use the above mentioned build chain. Ideally, the docker builder would fail to finish the gradle build if the unit testing fails. Unit testing should be done for each function, and the REST API endpoints should be thoroughly tested via putting in both good and bad data to ensure validity and that errors are handled properly.

Full system testing should also be performed with a simulation instantiated with test parameters and a test driver exercising the REST endpoints.

## Security Scanning

Currently, security scanning is not implemented. Basic security scanning should involve static code analysis, dynamic code analysis, secret discovery, dependency checking, container scanning, and basic antivirus scanning. 

Recommended tooling:

- Trufflehog
- Clamav
- Sonarqube
- Grype
- Syft

# Assumptions

- Elevator ops are the primary focus of this project
    - Multiple elevators should be included
    - Dispatch optimization strategy should be implemented and modular
    - All elevators have the same number of floors
    - Elevators should all behave the same, no random variations on performance or differences between cars
    - One elevator per track, all cars are the same (no multi-door elevators)
    - All elevators can access all floors
    - No limitations on passengers or cargo
- Backend is the prime goal, frontend and simulation drivers/scenarios are secondary
    - Swagger/OpenAPI for human interaction and testing is fine
    - Data storage and comprehensive logging is not required
- A web interface is a must to promote a modular and open architecture of elevator technology
    - RESTful interface
- Project should be initialized configurably, with a end goal of re-configurable during runtime
- Modern DevOps/DevSecOps is required for it to be secure, maintainable, and sustainable
    - Repeatable build process
    - Dependency management
    - Code should have documentation
    - Containerization (Hardware independence and portability)
    - Code should be held to modern coding standards and use a memory-safe language
- Simulation should be near real-time, not fast-forwarded, and travel time should be modeled

# Features

## Implemented Features

Features are split into three categories, covering what elevator features are implemented, what simulation features are implemented, and what software features are implemented.

### Elevator Features
- Call an elevator
    - Centralized logic for car dispatch
- Send your specific elevator to a floor
- Elevators verify that floors exist
- Elevators idle when not tasked

### Simulation Features
- Elevators take time to travel
- Configurable parameters
    - Building floor count
    - Lowest floor (basements, ground floor, standard US style)
- Deterministic (no random variations)
- Discrete Time
    - Soft realtime
- Continuous state
- Linear models
- User-in-the-loop

### Software Features
- Modular class structure for car dispatch optimizer
- RESTFul interface
- SpringBoot Inversion of Control design
- Global simulation state
- One command build
    - Gradle dependency management and build chain
    - Ephemeral docker builder container
- One command deploy
    - Docker compose to manage networking and volumes

## Un-implemented Features

- Mounted volume for configuration, or endpoint for modifying configuration
- Multiple Optimization Types
    - Minimized travel time
    - Picking up passengers on their way to floors
    - Express elevators
    - Machine learning options (reinforcement learning)
- Modular Optimization Types
    - Different optimizers for different cars
    - "Idle" floor schemes to optimize where cars come to rest
- Security Scanning
- Unit Testing
- GUI/frontend
- Simulation driver
    - Random amounts of passengers arriving
    - Tracking where passengers are starting from and heading to
    - High traffic floors (lobbies, etc)
- Misc Elevator Features
    - Firefighter mode
    - Alarm
    - Door state tracking
    - Delay time at floors