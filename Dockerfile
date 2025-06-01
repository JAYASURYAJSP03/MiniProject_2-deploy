# Build stage
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy Maven wrapper and pom.xml to install dependencies separately
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Make the mvnw script executable
RUN chmod +x mvnw

# Download dependencies only (speeds up builds if no dependency changes)
RUN ./mvnw dependency:resolve

# Copy the source code
COPY src ./src

# Build the application using Maven
RUN ./mvnw clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/MiniProject_2-0.0.1-SNAPSHOT.jar /app.jar

# Expose the application port (default for Spring Boot is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]