# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot jar into the container
COPY ./target/demo-0.0.1-SNAPSHOT.jar /app/app.jar
COPY application.properties /app/
COPY filesystem/ /app/filesystem/

# Expose the port the application runs on
EXPOSE 8080

RUN chmod -R 777 /app

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]