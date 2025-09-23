# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app/app.jar"]