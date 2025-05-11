# Stage 1: Build stage using Maven
FROM maven:3.9.8-amazoncorretto-21 AS build

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the application with Maven, skipping tests to speed up the build
RUN mvn clean package spring-boot:repackage -DskipTests

# Stage 2: Runtime stage using Amazon Corretto JDK 21
FROM amazoncorretto:21.0.4

# Set the working directory for the application
WORKDIR /app

# Copy the generated JAR file from the build stage into the container
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 to access the app
EXPOSE 8080

# Set the command to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
