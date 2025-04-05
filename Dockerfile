# Use a builder gradle
FROM gradle:8.13-jdk17-alpine AS builder

WORKDIR /
COPY ./src /src
COPY ./build.gradle /build.gradle

RUN gradle build --no-daemon

# Swap to java 17 image
FROM eclipse-temurin:17-alpine

COPY --from=builder /build/libs/elevator.jar /app/elevator.jar

ENTRYPOINT ["java", "-jar", "/app/elevator.jar"]
