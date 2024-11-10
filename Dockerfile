FROM ubuntu:latest
LABEL authors="jayas"

ENTRYPOINT ["top", "-b"]

# Use a base image with Java
FROM openjdk:17-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Install Maven and build the application
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Create a new image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build image
COPY --from=build /app/target/MiniProject_2-0.0.1-SNAPSHOT.jar /app/MiniProject_2.jar

# Expose the port your app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "MiniProject_2.jar"]
