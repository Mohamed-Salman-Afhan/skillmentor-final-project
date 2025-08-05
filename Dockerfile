# Stage 1: Build the application with a consistent Java version
FROM maven:3.8-openjdk-17 AS build

WORKDIR /app

# 1. Copy only the pom.xml to leverage Docker's caching
# This layer only gets rebuilt if your dependencies change.
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copy the rest of your source code
COPY src ./src

# 3. Build the application and create the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight runtime image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the correctly named JAR file from the build stage
COPY --from=build /app/target/final-project-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 9100

# Command to run the application
# We also specify the production profile here
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]